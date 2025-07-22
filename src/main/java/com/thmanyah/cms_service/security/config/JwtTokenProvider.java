package com.thmanyah.cms_service.security.config;


import com.thmanyah.cms_service.security.entity.Role;
import com.thmanyah.cms_service.security.entity.User;
import com.thmanyah.cms_service.shared.exception.ValidationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

     @Value("${app.jwt-secret}")
     private String jwtSecretKey;
     @Value("${app.jwt-expiration-milliseconds}")
     private int jwtExpirationMilliseconds;

     public String generateToken(Authentication authentication) {
          CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
          User user = userDetails.getUser();

          Date currentDate = new Date();
          Date expirationDate = new Date(currentDate.getTime() + jwtExpirationMilliseconds);

          return Jwts.builder()
                  .setSubject(user.getUserName()) // or user.getEmail()
                  .claim("userId", user.getId())
                  .claim("name", user.getUserName())
                  .claim("role", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                  .setIssuedAt(currentDate)
                  .setExpiration(expirationDate)
                  .signWith(SignatureAlgorithm.HS512, jwtSecretKey)
                  .compact();
     }



     public String getUserNameFromToken(String token){
          Claims claims = Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token).getBody();

          return claims.getSubject();
     }


     public boolean isExist(String token){
       try {
            Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token).getBody();
            return true;
       } catch (Exception ex){
            throw new ValidationException("Jwt Secret is invalid");
       }
     }











}
