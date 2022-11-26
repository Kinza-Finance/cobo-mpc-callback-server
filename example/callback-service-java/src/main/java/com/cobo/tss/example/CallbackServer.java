package com.cobo.tss.example;

import com.hellokaton.blade.Blade;
import com.hellokaton.blade.annotation.Path;
import com.hellokaton.blade.annotation.request.Form;
import com.hellokaton.blade.annotation.route.POST;
import com.hellokaton.blade.mvc.ui.ResponseType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.SerializationUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

class CallBackRequest implements Serializable{
    public int request_type;

    public String request_id;

    public String meta;

    public CallBackRequest(int requestType, String requestID, String meta) {
        this.request_type = requestType;
        this.request_id = requestID;
        this.meta = meta;
    }

    public int getRequest_type() {
        return this.request_type;
    }

    public void setRequest_type(int requestType) {
        this.request_type = requestType;
    }

    public String getRequest_id() {
        return this.request_id;
    }

    public void setRequest_id(String requestID) {
        this.request_id = requestID;
    }

    public String getMeta() {
        return this.meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    private void readObject(ObjectInputStream in) throws IOException,ClassNotFoundException  {
        request_type = in.readInt();
        request_id = (String) in.readObject();
        meta = (String) in.readObject();
    }
}

class CallBackResponse implements Serializable {
    public static final String ActionApprove             = "APPROVE";
    public static final String ActionReject             = "REJECT";
    public static final String ActionWait             = "WAIT";

    public int status;
    public String request_id;
    public String action; //[APPROVE, REJECT, WAIT]
    public String error;

    public CallBackResponse(int status, String requestID, String action, String errorInfo) {
        this.status = status;
        this.request_id = requestID;
        this.action = action;
        this.error = errorInfo;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRequest_id() {
        return this.request_id;
    }

    public void setRequest_id(String requestID) {
        this.request_id = requestID;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getError() {
        return this.error;
    }

    public void setError(String errorInfo) {
        this.error = errorInfo;
    }

    @Override
    public String toString() {
        return String.format("{\"status\":%d,\"request_id\":\"%s\",\"action\":\"%s\",\"error\":\"%s\"}",
                this.status, this.request_id, this.action, this.error);
    }

    private void writeObject(ObjectOutputStream out) throws IOException{
        out.writeInt(status);
        out.writeBytes(request_id);
        out.writeBytes(action);
        out.writeBytes(error);
    }
}

@Path
public class CallbackServer {
    private static final int StatusOK             = 0;
    private static final int StatusInvalidRequest = 10;
    private static final int StatusInvalidToken   = 20;
    private static final int StatusInternalError  = 30;

    private static final int TypeKeyGen     = 1;
    private static final int TypeKeySign    = 2;
    private static final int TypeKeyReshare = 3;

    private static final String JWT_PAYLOAD = "package_data";

    // 过期时间，单位是毫秒
    private static final int EXPIRE_TIME  = 2 * 60 * 1000;

    private static final String ISSUER = "TEST-001";

    private static final String PRIVATE_KEY_PATH = "customer-risk-control-server-pri.key";

    private static final String PUBLIC_KEY_PATH = "cobo-tss-node-risk-control-pub.key";

    /**
     * 获取PrivateKey对象
     *
     * @return
     */
    private static PrivateKey getRSAPrivateKey() {
        try {
            String priFilePath = CallbackServer.class.getClassLoader().getResource(PRIVATE_KEY_PATH).getFile();
            byte[] keyBytes = Files.readAllBytes(Paths.get(priFilePath));
            String privateKeyBase64 = new String(keyBytes);

            String privateKeyPEM = privateKeyBase64.replaceAll("\\-*BEGIN.*KEY\\-*", "")
                    .replaceAll("\\-*END.*KEY\\-*", "")
                    .replaceAll("\r", "")
                    .replaceAll("\n", "");

            // extract the private key
            PKCS8EncodedKeySpec priKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyPEM));
            KeyFactory factory = KeyFactory.getInstance("RSA");

            return factory.generatePrivate(priKeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取PublicKey对象
     *
     * @return
     */
    private static PublicKey getRSAPublicKey() {
        try {
            String pubFilePath = CallbackServer.class.getClassLoader().getResource(PUBLIC_KEY_PATH).getFile();
            byte[] keyBytes = Files.readAllBytes(Paths.get(pubFilePath));
            String publicKeyBase64 = new String(keyBytes);

            String publicKeyPEM = publicKeyBase64.replaceAll("\\-*BEGIN.*KEY\\-*", "")
                    .replaceAll("\\-*END.*KEY\\-*", "")
                    .replaceAll("\r", "")
                    .replaceAll("\n", "");

            // extract the private key
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyPEM));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            return keyFactory.generatePublic(pubKeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private String createResponseToken(CallBackResponse resp) {
        byte[] data = SerializationUtils.serialize(resp.toString());
//        byte[] data = resp.toString().getBytes();
        byte[] payload =  Base64.getEncoder().encode(data);

        SignatureAlgorithm signAlg = SignatureAlgorithm.RS256;
        long exp = System.currentTimeMillis() + EXPIRE_TIME;
        PrivateKey key = getRSAPrivateKey();

        JwtBuilder builder = Jwts.builder()
                .setIssuer(ISSUER)
                .setExpiration(new Date(exp))
                .claim(JWT_PAYLOAD,payload.toString())
                .signWith(key, signAlg);

        return builder.compact();
    }

    private String process_keygen_request(String request_id, String meta) {
        // risk control logical

        return createResponseToken(new CallBackResponse(StatusOK, request_id, CallBackResponse.ActionApprove, ""));
    }

    private String process_keysign_request(String request_id, String meta) {
        // risk control logical

        return createResponseToken(new CallBackResponse(StatusOK, request_id, CallBackResponse.ActionApprove, ""));
    }

    private String process_keyreshare_request(String request_id, String meta) {
        // risk control logical

        return createResponseToken(new CallBackResponse(StatusOK, request_id, CallBackResponse.ActionApprove, ""));
    }

    private String create_error_token(int status, String errInfo) {
        return createResponseToken(new CallBackResponse(status, "", "", errInfo));
    }

    private String parseJWT(String token) throws Exception{
        PublicKey key = getRSAPublicKey();
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        return claims.get(JWT_PAYLOAD, String.class);
    }

    @POST(value = "/v1/check", responseType = ResponseType.TEXT)
    public String riskControl(@Form String TSS_JWT_MSG) {
        String package_data;
        try {
            package_data = parseJWT(TSS_JWT_MSG);
        } catch (Exception e) {
            return create_error_token(StatusInvalidToken, e.toString());
        }

        try{
            byte[] payload = Base64.getDecoder().decode(package_data.getBytes());
            CallBackRequest req = SerializationUtils.deserialize(payload);

            switch (req.request_type) {
                case TypeKeyGen:
                    return process_keygen_request(req.request_id, req.meta);
                case TypeKeySign:
                    return process_keysign_request(req.request_id, req.meta);
                case TypeKeyReshare:
                    return process_keyreshare_request(req.request_id, req.meta);
                default:
                    return create_error_token(StatusInvalidRequest, "Unsupported request type");
            }
        } catch (Exception e) {
            return create_error_token(StatusInternalError, e.toString());
        }
    }

    public static void main(String[] args) {
        Blade.create().listen(11020).start(CallbackServer.class, args);
    }
}
