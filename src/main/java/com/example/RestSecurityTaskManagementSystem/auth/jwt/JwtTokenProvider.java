package com.example.RestSecurityTaskManagementSystem.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MalformedKeyException;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.security.UnsupportedKeyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.*;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.access-token-expiration-ms}")
    private long accessTokenExpiration;
    @Value("${jwt.refresh-token-expiration-ms}")
    private long refreshTokenExpiration;

    private Key getKey(){
        byte[] bytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(bytes);
    }

    public String generateAccessToken(UserDetails userDetails){
        Map<String ,Object> claims = new HashMap<>();
        claims.put("roles",userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
        return Jwts
                .builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration((new Date(System.currentTimeMillis()+accessTokenExpiration)))
                .signWith(getKey())
                .compact();
    }

    public String generateRefreshToken(UserDetails userDetails){
        return Jwts
                .builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+refreshTokenExpiration))
                .signWith(getKey())
                .compact();
    }

    public String getUsername(String token){
        return extractClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token){
        return extractClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails){
        try {
            String username = userDetails.getUsername();
            return !(isTokenExpired(token))&&username.equals(getUsername(token));
        }
        catch (ExpiredJwtException e){
            throw new JwtException("The token has expired");
        }
        catch (UnsupportedKeyException e){
            throw new JwtException("Unsupported token format");
        }
        catch (MalformedKeyException e){
            throw new JwtException("Invalid token");
        }
        catch (SignatureException e){
            throw new JwtException("Invalid token signature");
        }
        catch (IllegalArgumentException e){
            throw new JwtException("The token is empty or null.");
        }
    }

    public Claims extractClaims(String token){
        return Jwts.parser().verifyWith((SecretKey) getKey()).build().parseSignedClaims(token).getPayload();
    }


}
