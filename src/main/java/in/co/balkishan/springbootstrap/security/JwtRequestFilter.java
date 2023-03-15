package in.co.balkishan.springbootstrap.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.balkishan.springbootstrap.model.User;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

  private JwtTokenProvider jwtTokenProvider;

  public JwtRequestFilter(JwtTokenProvider jwtTokenProvider) {
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    final String token = jwtTokenProvider.resolveToken(request);
    if (token == null || token.isEmpty() || token.isBlank()) {
      filterChain.doFilter(request, response);
      return;
    }

    final String username = jwtTokenProvider.getUsername(token);
    if (username == null || username.isEmpty() || username.isBlank()) {
      filterChain.doFilter(request, response);
      return;
    }

    User user = new User();
    user.setEmail(username);
    final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
        user, null, user.getAuthorities());
    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    filterChain.doFilter(request, response);

  }

}
