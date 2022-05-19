package com.example.article.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TokenFilter extends OncePerRequestFilter {
    /*
    when a request is coming this filter will be running and in this filter we will do the controls
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        // 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJ1c2VybmFtZSI6InVzZXIxIiwiaWQiOiIxIn0.
        // 1LGAAdJvYAuHMFtDK1p9AW6s5Fgf93F29EGZK-DyWHmb56oQ55esE25701ON-1nANiR6LSSbnog6CjUE86POuw'
        String authorization = httpServletRequest.getHeader("Authorization");
        if (authorization != null) {
            String token = authorization.substring(7);
            // wrap this part with a try catch because we don't want to throw the exception at this step
            // we want this filter chain to be going on
            try {
                JwtParser parser = Jwts.parser().setSigningKey("my-app-secret");
                // setSigningKey : to verify this token is belongs to our application
                parser.parse(token); //token verifying
                // if there is something wrong with the token this is going to be throwing exception

                Claims claims = parser.parseClaimsJws(token).getBody();
                String username = (String) claims.get("username");
                Long userId = Long.valueOf((String) claims.get("id"));

                AppUser appUser = new AppUser(username, userId);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(appUser,
                        null, appUser.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);


    }
}
