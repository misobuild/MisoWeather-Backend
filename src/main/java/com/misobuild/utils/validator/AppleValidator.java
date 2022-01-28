package com.misobuild.utils.validator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.misobuild.constants.HttpStatusEnum;
import com.misobuild.exception.ApiCustomException;
import com.misobuild.utils.caller.oauth.AppleAuthCaller;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AllArgsConstructor;
import org.json.JSONObject;

import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;

@AllArgsConstructor
public class AppleValidator implements Validator {
    String user;
    String identityToken;
    private final String ISS = "https://appleid.apple.com";
    private final String AUD = "com.misobuild.MisoWeather";

    public Boolean valid() {
        try {
            SignedJWT signedJWT = SignedJWT.parse(identityToken);
            JWTClaimsSet payload = signedJWT.getJWTClaimsSet();

            // TODO 현재는 이렇게 되어있지만 반드시 date 확인하게 해야 한다.
//            Date currentTime = new Date(System.currentTimeMillis());
//            if (!currentTime.before(payload.getExpirationTime())) {
//                return false;
//            }

            if (!ISS.equals(payload.getIssuer()) || !AUD.equals(payload.getAudience().get(0))) {
                return false;
            }

            if (verifyPublicKey(signedJWT)) {
                return true;
            }
        } catch (ParseException | JsonProcessingException e) {
            throw new ApiCustomException(HttpStatusEnum.BAD_REQUEST);
        }
        return false;
    }

    /**
     * Apple Server에서 공개 키를 받아서 서명 확인
     *
     * @param signedJWT
     * @return
     */
    private boolean verifyPublicKey(SignedJWT signedJWT) throws JsonProcessingException {
        AppleAuthCaller appleAuthCaller = new AppleAuthCaller();
        JSONObject jsonObject = appleAuthCaller.call();
        ObjectMapper objectMapper = new ObjectMapper();
        AppleRSAKeys keys = objectMapper.readValue(jsonObject.toString(), AppleRSAKeys.class);
        try{
            for (AppleRSAKeys.AppleRSAKey key : keys.getKeys()) {
                RSAKey rsaKey = (RSAKey) JWK.parse(objectMapper.writeValueAsString(key));
                RSAPublicKey publicKey = rsaKey.toRSAPublicKey();
                JWSVerifier verifier = new RSASSAVerifier(publicKey);
                if (signedJWT.verify(verifier)) {
                    return true;
                }
            }
        }
        catch (Exception e){
            throw new ApiCustomException(HttpStatusEnum.BAD_REQUEST);
        }
        return false;
    }
}
