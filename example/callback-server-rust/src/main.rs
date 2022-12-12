use actix_web::{post, web, App, HttpResponse, HttpServer, Responder};
use jsonwebtoken::errors::ErrorKind;
use serde::{Serialize, Deserialize};
use jsonwebtoken::{encode, decode, Header, Algorithm, Validation, EncodingKey, DecodingKey};
use base64;
use std::time::{Duration, SystemTime, UNIX_EPOCH};

const  StatusOK: i32 = 0;
const  StatusInvalidRequest: i32 = 10;
const  StatusInvalidToken: i32 = 20;
const  StatusInternalError: i32 = 30;

const  TypeKeyGen: i32 = 1;
const  TypeKeySign: i32 = 2;
const  TypeKeyReshare: i32 = 3;

const token_expired_minutes: u64 = 2;

#[derive(Debug, Serialize, Deserialize)]
struct PackageDataClaim {
    package_data: String,
    iss: String,
    exp: usize,
}

#[derive(Debug, Deserialize)]
struct CallBackRequest {
    request_type: i32,
    request_id: String,
    meta: String,
}

#[derive(Debug, Deserialize, Serialize)]
struct CallBackResponse {
    status: i32,
    request_id: String,
    action: String,  //[APPROVE, REJECT, WAIT]
    error: String,
}

#[derive(Debug, Deserialize)]
struct ReqInfo {
    TSS_JWT_MSG: String,
}

fn generate_response_token(res: CallBackResponse) -> String {
    let payload_data = serde_json::to_string(&res).unwrap();
    let payload = base64::encode(payload_data);
    let exp = SystemTime::now().duration_since(UNIX_EPOCH).unwrap() + Duration::from_secs(token_expired_minutes * 60);

    let my_claims = PackageDataClaim{
        package_data: payload,
        iss: "TEST-001".to_string(),
        exp: exp.as_secs() as usize,
    };

    let callback_handler_prikey = include_bytes!("callback-server-pri.pem");
    let key = EncodingKey::from_rsa_pem(callback_handler_prikey).unwrap();
    let header = &Header::new(Algorithm::RS256);

    return encode(header, &my_claims, &key).unwrap();
}

fn process_keygen_request(request_id: String, meta: String) -> String {
    // let keyGenMeta = JSON.parse(meta);

    // risk control logical

    return generate_response_token(CallBackResponse{
        status: StatusOK,
        request_id: request_id,
        action: "APPROVE".to_string(),
        error: "".to_string()
    })
}

fn process_keysign_request(request_id: String, meta: String) -> String {
    // const keySignMeta = JSON.parse(meta);

    // risk control logical

    return generate_response_token(CallBackResponse{
        status: StatusOK,
        request_id: request_id,
        action: "APPROVE".to_string(),
        error: "".to_string()
    })
}

fn process_keyreshare_request(request_id: String, meta: String) -> String {
    // const keyReshareMeta = JSON.parse(meta);

    // risk control logical

    return generate_response_token(CallBackResponse{
        status: StatusOK,
        request_id: request_id,
        action: "APPROVE".to_string(),
        error: "".to_string()
    })
}

#[post("/v1/check")]
async fn risk_control(form: web::Form<ReqInfo>) -> impl Responder {
    let callback_pubkey = include_bytes!("cobo-tss-node-risk-control-pub.key");
    let key = DecodingKey::from_rsa_pem(callback_pubkey).unwrap();

    let mut validation = Validation::new(Algorithm::RS256);

    let token_data = match decode::<PackageDataClaim>(&form.TSS_JWT_MSG, &key, &validation) {
        Ok(c) => c,
        Err(err) => match *err.kind() {
            ErrorKind::InvalidToken => {
                let rst = generate_response_token(CallBackResponse{
                    status: StatusInvalidToken,
                    request_id: "".to_string(),
                    action: "".to_string(),
                    error: "Token is invalid".to_string()
                });
                return HttpResponse::Ok().body(rst);
            },
            ErrorKind::ExpiredSignature => {
                let rst = generate_response_token(CallBackResponse{
                    status: StatusInvalidToken,
                    request_id: "".to_string(),
                    action: "".to_string(),
                    error: "Token is expired".to_string()
                });
                return HttpResponse::Ok().body(rst);
            },
            ErrorKind::InvalidSignature => {
                let rst = generate_response_token(CallBackResponse{
                    status: StatusInvalidToken,
                    request_id: "".to_string(),
                    action: "".to_string(),
                    error: "Token Signature is invalid".to_string()
                });
                return HttpResponse::Ok().body(rst);
            },
            _ => {
                let rst = generate_response_token(CallBackResponse{
                    status: StatusInternalError,
                    request_id: "".to_string(),
                    action: "".to_string(),
                    error: "Some other errors".to_string()
                });
                return HttpResponse::Ok().body(rst)
            },
        },
    };

    let raw_data = base64::decode(token_data.claims.package_data).unwrap();
    let req: CallBackRequest = serde_json::from_slice(&raw_data).unwrap();
    match req.request_type {
        TypeKeyGen => {
            let rst = process_keygen_request(req.request_id, req.meta);
            return HttpResponse::Ok().body(rst)
        },
        TypeKeySign => {
            let rst = process_keysign_request(req.request_id, req.meta);
            return HttpResponse::Ok().body(rst)
        },
        TypeKeyReshare => {
            let rst = process_keyreshare_request(req.request_id, req.meta);
            return HttpResponse::Ok().body(rst)
        },
        _ => {
            let rst = generate_response_token(CallBackResponse{
                status: StatusInvalidRequest,
                request_id: "".to_string(),
                action: "".to_string(),
                error: "Unsupported request type".to_string()
            });
            return HttpResponse::Ok().body(rst)
        }
    }
}

#[actix_web::main] // or #[tokio::main]
async fn main() -> std::io::Result<()> {
    HttpServer::new(|| {
        App::new().service(risk_control)
    })
        .bind(("127.0.0.1", 11020))?
        .run()
        .await
}
