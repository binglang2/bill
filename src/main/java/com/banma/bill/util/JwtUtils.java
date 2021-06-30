package com.banma.bill.util;

import io.fusionauth.jwt.Signer;
import io.fusionauth.jwt.Verifier;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACSigner;
import io.fusionauth.jwt.hmac.HMACVerifier;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @author binglang
 * @since 2019/8/15
 */
public class JwtUtils {

    private static final String SECRET = "zxfwk2ddkfdsgxxffccbsndkdfqsjdif2";

    public static String getJwtToken(String subject) {
        // Build an HMAC signer using a SHA-256 hash
        Signer signer = HMACSigner.newSHA256Signer(SECRET);

        // Build a new JWT with an issuer(iss), issued at(iat), subject(sub) and expiration(exp)
        JWT jwt = new JWT().setIssuer("banma")
            .setIssuedAt(ZonedDateTime.now(ZoneId.systemDefault()))
            .setSubject(subject)
            .setExpiration(ZonedDateTime.now(ZoneId.systemDefault()).plusDays(30));

        // Sign and encode the JWT to a JSON string representation
        return JWT.getEncoder().encode(jwt, signer);
    }


    public static JWT parseJwt(String encodedJwt) {
        // Build an HMC verifier using the same secret that was used to sign the JWT
        Verifier verifier = HMACVerifier.newVerifier(SECRET);

        // Verify and decode the encoded string JWT to a rich object
        return JWT.getDecoder().decode(encodedJwt, verifier);
    }

    public static void main(String[] args) throws Exception {
        String jwtToken = getJwtToken("12345678");
        System.out.println(jwtToken);

        JWT jwt = parseJwt(jwtToken);
        System.out.println(jwt.isExpired());
        System.out.println(jwt.issuer);
        System.out.println(jwt.subject);
    }
}
