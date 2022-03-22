package com.team1.pinterest.Security;

import com.team1.pinterest.Entitiy.User;
import com.team1.pinterest.Exception.CustomException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static com.team1.pinterest.Exception.ErrorCode.*;

@Service
@Slf4j
public class TokenProvider {

    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String create(User user){
        Date expiryDate = Date.from(
                Instant.now()
                        .plus(1, ChronoUnit.DAYS));

        return Jwts.builder()
                //header
                .signWith(key)
                //payload
                .setSubject(user.getId().toString()) //sub
                .setIssuer("demo app") //iss
                .setIssuedAt(new Date()) //iat
                .setExpiration(expiryDate) // exp
                .compact();
    }

    public String validateAndGetUserId(String token){
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e){
            log.info("잘못된 JWT 서명");
            throw new CustomException(SIGNATURE_TOKEN);
        } catch (ExpiredJwtException e){
            log.info("만료된 JWT 토큰");
            throw new CustomException(EXPIRED_TOKEN);

        } catch (UnsupportedJwtException e){
            log.info("지원되지 않는 JWT 토큰");
            throw new CustomException(UNAUTHORIZED_TOKEN);

        } catch (IllegalArgumentException e){
            log.info("JWT토큰이 잘못됨.");
            throw new CustomException(TOKEN_ARGUMENTS);

        }
    }
}
