package com.fintech.demo.prestamos.seguridad;

import com.fintech.demo.prestamos.usuario.modelo.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWT {
    private static final String KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    private static final long EXPIRATION_TIME = 864_000_000;

    public String generarToken(Usuario userDetails) {
        Map<String, Object> claims = new HashMap<>();

        return this.crearToken(claims, userDetails.getCorreo());
    }

    public String crearToken(Map<String, Object> claims, String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT.EXPIRATION_TIME))
                .signWith(this.obtenerSesionKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key obtenerSesionKey(){
        byte[] keyBytes = Decoders.BASE64.decode(KEY);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Boolean validarToken(String token, UserDetails userDetails){
        String username = this.obtenerClaim(token, Claims::getSubject);
        return  username.equals(userDetails.getUsername()) && !this.tokenExpirado(token);
    }

    public String extraerSubject(String token   ){
        return "";
    }

    public Date tokenExpiro(String token){
        return this.obtenerClaim(token, Claims::getExpiration);
    }

    public <T> T obtenerClaim(String token, Function<Claims, T> claimsResolver){
        Claims claims = this.obtenerTodoClaims(token);
        return  claimsResolver.apply(claims);
    }
    public Claims obtenerTodoClaims(String token){
        return Jwts.parserBuilder().setSigningKey(this.obtenerSesionKey()).build().parseClaimsJws(token).getBody();
    }

    public Boolean tokenExpirado(String token){
        return this.tokenExpiro(token).before(new Date());
    }
}
