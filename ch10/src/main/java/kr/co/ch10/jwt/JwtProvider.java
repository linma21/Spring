package kr.co.ch10.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import kr.co.ch10.entity.User;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Slf4j
@Getter
@Component
public class JwtProvider {

    private String issuer;
    private SecretKey secretKey;

    public JwtProvider(@Value("${jwt.issuer}") String issuer,
                       @Value("${jwt.secret}") String secretKey){
        this.issuer = issuer;
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes());
        log.info("Jwt Provider...?");
    }
    public String createToken(User user, int days){
        log.info("Jwt Provider...1 create Token");
        // 발급일, 만료일 생성
        Date issueDate = new Date();
        Date expireDate = new Date(issueDate.getTime() + Duration.ofDays(days).toMillis());

        // 클레임 생성
        Claims claims = Jwts.claims();
        claims.put("username", user.getUid());
        claims.put("role", user.getRole());
        log.info("Jwt Provider...2");
        // 토큰 생성
        String token = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(issuer)
                .setIssuedAt(issueDate)
                .setExpiration(expireDate)
                .addClaims(claims)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        return token;
    }
    public Claims getClaims(String token){
        log.info("Jwt Provider...4");
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public Authentication getAuthentication(String token){
        log.info("Jwt Provider...5");
        // 클레임에서 사용자, 권한 가져오기
        Claims claims = getClaims(token);
        String uid = (String) claims.get("username");
        String role = (String) claims.get("role");

        // 권한 목록 생성
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        log.info("Jwt Provider...6");
        // 사용자 인증 객체 생성
        User principal = User.builder()
                            .uid(uid)
                            .role(role)
                            .build();

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }
    public boolean validateToken(String token){

        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            log.info("Jwt Provider...7");
            return true;
        }catch (SecurityException | MalformedJwtException e){
            // 잘못된 JWT 서명일 경우
            log.info("MalformedJwtException..." + e.getMessage());
            throw new JwtMyException(JwtMyException.JWT_ERROR.MALFORM);
        }catch (ExpiredJwtException e){
            // 만료된 JWT 경우
            log.info("ExpiredJwtException..." + e.getMessage());
            throw new JwtMyException(JwtMyException.JWT_ERROR.EXPIRED);
        }catch (UnsupportedJwtException e){
            // 지원되지 않는 JWT 경우
            log.info("UnsupportedJwtException..." + e.getMessage());
            throw new JwtMyException(JwtMyException.JWT_ERROR.BADTYPE);
        }catch (IllegalArgumentException e){
            // 잘못된 JWT 경우
            log.info("IllegalArgumentException..." + e.getMessage());
            throw new JwtMyException(JwtMyException.JWT_ERROR.BADSIGN);
        }
    }
}
