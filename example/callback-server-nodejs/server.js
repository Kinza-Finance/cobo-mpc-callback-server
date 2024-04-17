const express = require('express');
const bodyParser = require('body-parser');
const { expressjwt: exjwt } = require('express-jwt');
const jwt = require('jsonwebtoken')
const fs = require("fs");
var app = express();

const StatusOK = 0
const StatusInvalidRequest = 10
const StatusInvalidToken = 20
const StatusInternalError = 30

const TypePing = 0
const TypeKeyGen = 1
const TypeKeySign = 2
const TypeKeyReshare = 3

const service_name = "TEST-001";
const token_expired_minutes = 2;
const callback_handler_prikey = fs.readFileSync('./callback-server-pri.pem')
const callback_pubkey = fs.readFileSync('./cobo-tss-node-risk-control-pub.key')

// the callBackResponse should be like this:
// {
//     status: int,
//     request_id: str,
//     action: str, //[APPROVE, REJECT]
//     error: str,
// }
let send_response = (res, callBackResponse) => {
    const rspStr = JSON.stringify(callBackResponse);
    const payload= Buffer.from(rspStr).toString("base64")

    var signOpt = {
        issuer: service_name,
        algorithm: 'RS256',
        expiresIn: token_expired_minutes * 60,
    };
    const token = jwt.sign({package_data: payload}, Buffer.from(callback_handler_prikey, "base64"), signOpt);

    res.status(200).json(token)
}

// Create application/x-www-form-urlencoded parser
app.use(bodyParser.urlencoded({ extended: false }))
app.use(exjwt({
    secret: Buffer.from(callback_pubkey, "base64"),
    algorithms: ['RS256'],
    requestProperty: 'auth',
    getToken: function fromForm (req) {
        if (req.body['TSS_JWT_MSG'] !== undefined) {
            return req.body['TSS_JWT_MSG'];
        }
        return null;
    },
}))

let errorHandler = (err, req, res, next) => {
    let errorInfo = {
        status: StatusInvalidRequest,
        error: "",
    }

    if (typeof(err) === 'string') {
        errorInfo.error = err;
        return send_response(res, errorInfo)
    }

    if (err.name === 'UnauthorizedError') {
        errorInfo.status = StatusInvalidToken;
        errorInfo.error = "Invalid Token";
        return send_response(res, errorInfo)
    }

    // default to internal server error
    errorInfo.status = StatusInternalError;
    errorInfo.error = "Internal Server Error";
    return send_response(res, errorInfo)
}
app.use(errorHandler)

let process_ping_request = (request_id, res) => {
    send_response(res, {
        status: StatusOK,
        request_id: request_id,
    })
}

let process_keygen_request = (request_id, request_detail, extra_info, res) => {
    const kgDetail = JSON.parse(request_detail);
    const rawExtraInfo = JSON.parse(extra_info);

    // risk control logical

    send_response(res, {
        status: StatusOK,
        request_id: request_id,
        action: 'APPROVE'
    })
}

let process_keysign_request = (request_id, request_detail, extra_info, res) => {
    const ksDetail = JSON.parse(request_detail);
    const rawExtraInfo = JSON.parse(extra_info);

    // risk control logical

    send_response(res, {
        status: StatusOK,
        request_id: request_id,
        action: 'APPROVE'

    })
}

let process_keyreshare_request = (request_id, request_detail, extra_info, res) => {
    const krDetail = JSON.parse(request_detail);
    const rawExtraInfo = JSON.parse(extra_info);

    // risk control logical

    send_response(res, {
        status: StatusOK,
        request_id: request_id,
        action: 'APPROVE'

    })
}

app.post('/v1/check', function (req, res) {
    // the token payload will store in req.auth
    // the payload is a struct like this:
    // {
    //     package_data: 'eyJyZXF1ZXN0X2lkIjoidGVzdC1yZXEtaWQiLCJyZXF1ZXN0X3R5cGUiOjEsIm1ldGEiOiJ7XCJyZXF1ZXN0X2lkXCI6XCJyZXFfMDAxXCIsXCJ0aHJlc2hvbGRcIjoyLFwibm9kZV9pZFwiOlwibm9kZTNcIn0ifQ==',
    //     exp: 2029185219,
    //     iss: 'TEST-0001'
    // }
    // we should convert the package_data to the real payload:
    // {
    //     request_id:'xxxx',
    //     request_type:1,
    //     request_detail: 'xxxx',
    //     extra_info:'xxx',
    // }

    var package_data = Buffer.from(req.auth['package_data'], "base64").toString();
    const callBackRequest = JSON.parse(package_data);

    switch (callBackRequest['request_type']) {
        case TypePing:
            process_ping_request(callBackRequest['request_id'], res);
            break;
        case TypeKeyGen:
            process_keygen_request(callBackRequest['request_id'], callBackRequest['request_detail'], callBackRequest['extra_info'], res)
            break;
        case TypeKeySign:
            process_keysign_request(callBackRequest['request_id'], callBackRequest['request_detail'], callBackRequest['extra_info'], res)
            break;
        case TypeKeyReshare:
            process_keyreshare_request(callBackRequest['request_id'], callBackRequest['request_detail'], callBackRequest['extra_info'], res)
            break;
        default:
            break;
    }
})

var server = app.listen(11020, function () {
    var port = server.address().port
    console.log('Serving on port %s', port)
})
