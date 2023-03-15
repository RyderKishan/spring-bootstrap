package in.co.balkishan.springbootstrap.security;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import in.co.balkishan.springbootstrap.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
  @Value("${app.jwtSecret}")
  private String jwtSecret;

  @Value("${app.jwtExpirationMins}")
  private int jwtExpirationMins;

  private Key key;

  @PostConstruct
  protected void init() {
    key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
  }

  public String createToken(User user) {

    Claims claims = Jwts.claims().setSubject(user.getUsername());
    // claims.put("id", String.valueOf(user.getId()));
    // claims.put("status", String.valueOf(user.getStatus().toString()));

    Date now = new Date();
    Date validity = new Date(now.getTime() + 1000 * 60 * jwtExpirationMins);

    return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(validity).signWith(key).compact();
  }

  public String getUsername(String token) {
    try {
      return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    } catch (Exception e) {
      return null;
    }
  }

  public String getValue(String token, String valueKey) {
    try {
      Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
      return (String) claims.get(valueKey);
    } catch (ExpiredJwtException e) {
      return null;
    } catch (Exception e) {
      return null;
    }
  }

  public String getToken(String bearerToken) {
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      String token = bearerToken.substring(7, bearerToken.length());
      if (token == null || token.isEmpty() || token.isBlank() || token.equals("null") || token.equals("undefined"))
        return null;
      return token;
    }
    return null;
  }

  public String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
    return getToken(bearerToken);
  }

  public boolean validateToken(String token) {
    if (token.isEmpty() || token.isBlank())
      return false;
    Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    if (claims.getBody().getExpiration().before(new Date())) {
      return false;
    }
    return true;
  }

  public Authentication getAuthentication(String token) {
    List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    authorities.add(new SimpleGrantedAuthority("API"));
    Integer id = Integer.parseInt(getValue(token, "id"));
    String userName = getUsername(token);
    User user = new User();
    user.setId(id);
    user.setEmail(userName);
    return new UsernamePasswordAuthenticationToken(user, "", authorities);
  }

}
