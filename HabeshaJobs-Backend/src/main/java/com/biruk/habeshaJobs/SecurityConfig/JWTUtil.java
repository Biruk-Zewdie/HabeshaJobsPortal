package com.biruk.habeshaJobs.SecurityConfig;

import com.biruk.habeshaJobs.Model.User.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

//The purpose of this class is to generate, validate and extract data (subject and claims) stored in the JWT token.
//The JWT token is a way to store users information and confirm their identity every time they make a request to the server.
// it is stateless (meaning it does not store any data regarding to the specific request in the server)
@Component
public class JWTUtil {

    // 24 hours token expiration time in milliseconds
    private static final long tokenExpirationTime = 24 * 60 * 60 * 1000;

    // the JWT secret key is used to sign and later verify the JWT token,
    //TODO: make sure to change change the constant secreate key to a rotating key with in a certain time period
    @Value("${app.jwt.secret}")
    private String SECRET_KEY;

    //The logger is used to log the errors and information in the class.
    private static final Logger logger = LoggerFactory.getLogger(JWTUtil.class);

    //This method is used to generate a JWT token for the user.
    //when setting a subject, the userId should be converted from any type (in this case UUID) to String
    public String generateToken(User user){
        return Jwts.builder()
                .subject(user.getUserId().toString())
                .claim("email", user.getEmail())
                .claim("role", user.getRole())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + tokenExpirationTime))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .compact();
    }

    //This method is a helper method to extract the claims from the JWT token and used by each claim extraction methods.
    //Most of the methods in this class are new and enhanced methods of the JJWT library after old methods were deprecated on version 0.12.0
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    //This method is used to extract the subject (userId) from the JWT token.
    public UUID extractUserId (String token) {
        return UUID.fromString(extractAllClaims(token).getSubject());
    }

    //This method is used to extract the email from the JWT token.
    public String extractEmail(String token) {
        return extractAllClaims(token).get("email", String.class);
    }

    //This method is used to extract the role from the JWT token.
    public User.Role extractRole(String token) {
        // the role is stored as a string in the JWT token, so we need to convert it to the User.Role enum
        String roleString = extractAllClaims(token).get("role", String.class);
        return User.Role.valueOf(roleString);
    }


    public boolean validateToken(String token){
        try {
            Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                    .build()
                    .parseSignedClaims(token);
            return true;
        }catch (ExpiredJwtException e){
            logger.error("JWT expired", e.getMessage());
        }catch (IllegalArgumentException e){
            logger.error("The token is null, empty or blank", e.getMessage());
        }catch(MalformedJwtException e){
            logger.error("Jwt is invalid", e.getMessage());
        }catch (UnsupportedJwtException e){
            logger.error("Jwt is not supported", e.getMessage());
        }catch (JwtException e){
            logger.error("Signature validation failed", e.getMessage());
        }

        return false;
    }
}
