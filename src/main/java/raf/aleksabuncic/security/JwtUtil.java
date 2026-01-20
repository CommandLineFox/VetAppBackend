package raf.aleksabuncic.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import raf.aleksabuncic.domain.Veterinarian;
import raf.aleksabuncic.exception.ResourceNotFoundException;
import raf.aleksabuncic.repository.VeterinarianRepository;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    private final String SECRET_KEY;
    private final long EXPIRATION_TIME;

    @Autowired
    private VeterinarianRepository veterinarianRepository;

    @Autowired
    public JwtUtil(@Value("${jwt.secret}") String secret, @Value("${jwt.expiration}") long expiration) {
        this.SECRET_KEY = secret;
        this.EXPIRATION_TIME = expiration;
    }

    public Claims extractAllClaims(String token) {
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractLicenseNumber(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Integer licenseNumber) {
        Veterinarian veterinarian = veterinarianRepository.findByLicenseNumber(licenseNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Veterinarian not found"));

        Map<String, Object> claims = new HashMap<>();
        claims.put("permissions",
                PermissionUtils.toStringPermissions(veterinarian.getPermissions()));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(licenseNumber.toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean validateToken(String token, UserDetails user) {
        return (user.getUsername().equals(extractLicenseNumber(token)) && !isTokenExpired(token));
    }
}