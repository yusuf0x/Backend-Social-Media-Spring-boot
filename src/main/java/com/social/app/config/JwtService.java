package com.social.app.config;

import com.social.app.exceptions.JwtExpirationException;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.security.SignatureException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Service
public class JwtService {
    private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    public String validateAndExtractUsername(String token){
        try {
            return extractClaims(token, Claims::getSubject);
        }catch (ExpiredJwtException ex){
            throw new JwtExpirationException("JWT token has expired");
        }
    }
    public <T> T extractClaims(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token){
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

    }
//    public boolean validateJwtToken(String authToken) {
//        try {
//            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(authToken);
//            return true;
//        } catch (MalformedJwtException e) {
////            logger.error("Invalid JWT token: {}", e.getMessage());
//        } catch (ExpiredJwtException e) {
//            throw new JwtExpirationException("JWT token is expired");
////            logger.error("JWT token is expired: {}", e.getMessage());
//        } catch (UnsupportedJwtException e) {
////            logger.error("JWT token is unsupported: {}", e.getMessage());
//        } catch (IllegalArgumentException e) {
////            logger.error("JWT claims string is empty: {}", e.getMessage());
//        }catch (Exception e){
//
//        }
//        return false;
//    }
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public String generateToken(
            Map<String,Object> extraClaims,
            UserDetails userDetails
    ){
        return Jwts.builder().setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+2000*60*24))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }
    public String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 2000 * 60 * 60 * 10))
                .signWith(getSignKey() ,SignatureAlgorithm.HS256).compact();
    }

    public boolean isTokenValid (String token,UserDetails userDetails){
        final String username = validateAndExtractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token,Claims::getExpiration);
    }
}
