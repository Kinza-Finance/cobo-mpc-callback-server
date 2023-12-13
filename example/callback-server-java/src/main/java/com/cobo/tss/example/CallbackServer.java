package com.cobo.tss.example;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hellokaton.blade.Blade;
import com.hellokaton.blade.annotation.Path;
import com.hellokaton.blade.annotation.request.Body;
import com.hellokaton.blade.annotation.request.Form;
import com.hellokaton.blade.annotation.route.GET;
import com.hellokaton.blade.annotation.route.POST;
import com.hellokaton.blade.mvc.ui.ResponseType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.web3j.utils.Numeric;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    private static final String PRIVATE_KEY_PATH = "callback-server-pri.pem";

    private static final String PUBLIC_KEY_PATH = "cobo-tss-node-risk-control-pub.key";

    public Map<String, Boolean> RcvWhiteListMap = new ConcurrentHashMap<>();

    public Map<String, CallBackResponse> rspMemStorage = new ConcurrentHashMap<>();

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

    private String MarshalResponse(CallBackResponse resp) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            byte[] data =  objectMapper.writeValueAsBytes(resp);
            String payload = Base64.getEncoder().encodeToString(data);

            return payload;
        } catch(Exception e) {
            return e.toString();
        }
    }

    private String createResponseToken(CallBackResponse resp) {
        String payload = MarshalResponse(resp);

        SignatureAlgorithm signAlg = SignatureAlgorithm.RS256;
        long exp = System.currentTimeMillis() + EXPIRE_TIME;
        PrivateKey key = getRSAPrivateKey();

        JwtBuilder builder = Jwts.builder()
                .setIssuer(ISSUER)
                .setExpiration(new Date(exp))
                .claim(JWT_PAYLOAD, payload)
                .signWith(key, signAlg);

        return builder.compact();
    }

    private String process_keygen_request(String request_id, String request_detail, String extra_info) {
        try {
            ObjectMapper objectMapper1 = new ObjectMapper();
            KeyGenDetail kgDetail = objectMapper1.readValue(request_detail, KeyGenDetail.class);

            ObjectMapper objectMapper2 = new ObjectMapper();
            objectMapper2.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            KeyGenExtraInfo rawExtraInfo = objectMapper2.readValue(extra_info, KeyGenExtraInfo.class);

            // risk control logical

            // mock long time risk control, and save CallBackResponse to mem storage
            CallBackResponse rsp = new CallBackResponse(StatusOK, request_id, CallBackResponse.ActionApprove, "");
            rspMemStorage.put(request_id, rsp);

            return createResponseToken(rsp);
        } catch(Exception e) {
            e.printStackTrace();
            return create_error_token(StatusInternalError, e.toString());
        }
    }

    private String process_keysign_request(String request_id, String request_detail, String extra_info) {
        try {
            ObjectMapper objectMapper1 = new ObjectMapper();
            KeySignDetail ksDetail = objectMapper1.readValue(request_detail, KeySignDetail.class);

            ObjectMapper objectMapper2 = new ObjectMapper();
            objectMapper2.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            KeySignExtraInfo rawExtraInfo = objectMapper2.readValue(extra_info, KeySignExtraInfo.class);

            // risk control logical
            if (RcvWhiteListMap.get(rawExtraInfo.to_address) == null ) {
                return createResponseToken(new CallBackResponse(StatusOK, request_id,
                        CallBackResponse.ActionReject, "The target receiver address is not in whitelist"));
            }

            // mock long time risk control, and save CallBackResponse to mem storage
            CallBackResponse rsp = new CallBackResponse(StatusOK, request_id, CallBackResponse.ActionApprove, "");
            rspMemStorage.put(request_id, rsp);

            return createResponseToken(rsp);
        } catch(Exception e) {
            e.printStackTrace();
            return create_error_token(StatusInternalError, e.toString());
        }
    }

    private String process_keyreshare_request(String request_id, String request_detail, String extra_info) {
        try {
            ObjectMapper objectMapper1 = new ObjectMapper();
            KeyReshareDetail krDetail = objectMapper1.readValue(request_detail, KeyReshareDetail.class);

            ObjectMapper objectMapper2 = new ObjectMapper();
            objectMapper2.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            KeyReshareExtraInfo rawExtraInfo = objectMapper2.readValue(extra_info, KeyReshareExtraInfo.class);

            // risk control logical

            // mock long time risk control, and save CallBackResponse to mem storage
            CallBackResponse rsp = new CallBackResponse(StatusOK, request_id, CallBackResponse.ActionApprove, "");
            rspMemStorage.put(request_id, rsp);

            return createResponseToken(rsp);
        } catch(Exception e) {
            e.printStackTrace();
            return create_error_token(StatusInternalError, e.toString());
        }
    }

    private String create_error_token(int status, String errInfo) {
        return createResponseToken(new CallBackResponse(status, "", "", errInfo));
    }

    private String parseJWT(String token) throws Exception{
        PublicKey key = getRSAPublicKey();
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

        return claims.get(JWT_PAYLOAD, String.class);
    }

    public static boolean isBTCValidAddress(String input) {
        input = input.trim();
        int Addrlen = input.length();
        if (Addrlen < 25) {
            return false;
        }

        if (input.startsWith("1")) {
            if (Addrlen >= 26 && Addrlen <= 34) {
                return true;
            }
        }

        if(input.startsWith("3") && Addrlen == 34) {
            return true;
        }

        if (input.startsWith("bc1") && Addrlen > 34) {
            return true;
        }

        return false;
    }

    public static boolean isETHValidAddress(String input) {
        if (input.trim().isEmpty() || !input.startsWith("0x"))
            return false;
        return isValidAddress(input);
    }

    public static boolean isValidAddress(String input) {
        String cleanInput = Numeric.cleanHexPrefix(input);

        try {
            Numeric.toBigIntNoPrefix(cleanInput);
        } catch (NumberFormatException e) {
            return false;
        }

        return cleanInput.length() == 40;
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
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            CallBackRequest req = objectMapper.readValue(payload, CallBackRequest.class);

            // first check if the request has already been deal with
            CallBackResponse rst = rspMemStorage.get(req.request_id);
            if ( rst != null){
                return createResponseToken(rst);
            }

            switch (req.request_type) {
                case TypeKeyGen:
                    return process_keygen_request(req.request_id, req.request_detail, req.extra_info);
                case TypeKeySign:
                    return process_keysign_request(req.request_id, req.request_detail, req.extra_info);
                case TypeKeyReshare:
                    return process_keyreshare_request(req.request_id, req.request_detail, req.extra_info);
                default:
                    return create_error_token(StatusInvalidRequest, "Unsupported request type");
            }
        } catch (Exception e) {
            return create_error_token(StatusInternalError, e.toString());
        }
    }

    @POST(value = "/add_rcv_address", responseType = ResponseType.JSON)
    public AddressWhitelistResponse addReceiverAddress(@Body String req) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            AddressWhitelistRequest wlReq = objectMapper.readValue(req, AddressWhitelistRequest.class);

            if(!isETHValidAddress(wlReq.address) && !isBTCValidAddress(wlReq.address)) {
                return new AddressWhitelistResponse(400, "Receiver address is not valid btc or eth address");
            }

            RcvWhiteListMap.put(wlReq.address, true);
            return new AddressWhitelistResponse(200, "");
        } catch(Exception e) {
            return new AddressWhitelistResponse(400, e.toString());
        }
    }

    @POST(value = "/rm_rcv_address", responseType = ResponseType.JSON)
    public AddressWhitelistResponse removeReceiverAddress(@Body String req) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            AddressWhitelistRequest wlReq = objectMapper.readValue(req, AddressWhitelistRequest.class);

            RcvWhiteListMap.remove(wlReq.address);
            return new AddressWhitelistResponse(200, "");
        } catch(Exception e) {
            return new AddressWhitelistResponse(400, e.toString());
        }
    }

    @GET(value = "/list_rcv_address", responseType = ResponseType.JSON)
    public ListAddressWhitelistResponse listReceiverAddress() {
        String[] rst = new String[RcvWhiteListMap.size()];
        int idx = 0;
        for(String key : RcvWhiteListMap.keySet()) {
            rst[idx] = key;
            idx += 1;
        }
        
        return new ListAddressWhitelistResponse(rst);
    }

    public static void main(String[] args) {
        Blade.create().listen(11020).start(CallbackServer.class, args);
    }
}
