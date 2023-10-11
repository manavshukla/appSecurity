package com.security.filter;

import com.security.model.JwtTokenProvider;
import com.security.service.TokenStatusServiceImpl;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class JwtAuthenticationFilter implements Filter {

    private final JwtTokenProvider jwtTokenProvider;

    @Value("${app.jwt.secret}")
    private String key;
    private final TokenStatusServiceImpl tokenStatusService;

    private static final String ROOT_PATH = "/app";
    private static final Set<String> ALLOWED_PATHS = Set.of(ROOT_PATH, ROOT_PATH + "/", ROOT_PATH + "/roleList", ROOT_PATH + "/login",
            ROOT_PATH + "/signUp", ROOT_PATH + "/logout");

    @Value("${app.jwt.secret}")
    private String jwtSecret;


    @Autowired
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, TokenStatusServiceImpl tokenStatusService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.tokenStatusService = tokenStatusService;
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {

            HttpServletRequest req = (HttpServletRequest) servletRequest;
            HttpServletResponse res = (HttpServletResponse) servletResponse;
            String url = req.getRequestURI();

            if (!ALLOWED_PATHS.contains(url)) {
                String token = jwtTokenProvider.resolveToken(req);
                if (!isTokenExpired(token) && isTokenStatusZero()) {
                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    HttpServletResponse response = (HttpServletResponse) servletResponse;
                    response.sendRedirect("/app/login");
                    return;
                }
            }
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            ResponseEntity.badRequest().body("Try Again");
        }
    }

    private boolean isTokenStatusZero() {
        int status = tokenStatusService.getTokenStatus();
        return status == 0;
    }

    private boolean isTokenExpired(String token) {

        if (getTokenExpirationDate(token)) {

            return true;
        }
        return false;
    }

    private boolean getTokenExpirationDate(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("TOKEN IS EXPIRED");
            e.printStackTrace();
        }
        return false;
    }
}
