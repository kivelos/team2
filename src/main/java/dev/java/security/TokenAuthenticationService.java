package dev.java.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

public final class TokenAuthenticationService {
    private TokenAuthenticationService() {
    }

    private static final long EXPIRATION_TIME = 3600000; // 1 hour

    private static final String SECRET = "ThisIsASecret";

    private static final String TOKEN_PREFIX = "Bearer";

    private static final String HEADER_STRING = "Authorization";

    public static void addAuthentication(HttpServletResponse res, String username,
                                         Collection<? extends GrantedAuthority> roles) {
        System.out.println("addAuthentication " + username);
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", roles);

        String jwt = Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + jwt);
    }

    public static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            System.out.println("getAuthentication");
            // parse the token.
            Claims body = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();
            String user = body.getSubject();
            System.out.println(body.get("role"));
            ArrayList arrayList = (ArrayList) body.get("role");
            LinkedHashMap hashMap = (LinkedHashMap) arrayList.get(0);
            List<GrantedAuthority> authorityList =
                    AuthorityUtils.commaSeparatedStringToAuthorityList((String) hashMap.get("authority"));

            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, authorityList);
            }
            return null;
        }
        return null;
    }

}
