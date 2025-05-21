package com.sbs.ald.interceptor;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.sbs.ald.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    private final JwtUtil jwtUtil;

    public JwtHandshakeInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean beforeHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Map<String, Object> attributes) throws Exception {

        String token = null;

        if (request instanceof ServletServerHttpRequest servletRequest) {
            HttpServletRequest req = servletRequest.getServletRequest();
            // Primer: token dolazi kao query parametar ?token=xxx
            token = req.getParameter("token");

            if (token == null || token.isBlank()) {
//            	System.out.println("ovo za null... token: " + token);
                // Ili iz Authorization header-a
                String authHeader = req.getHeader("Authorization");
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    token = authHeader.substring(7);
                }
            }

            if (token != null) {
//            	System.out.println("Before handshake... token: " + token);

                try {
                    String username = jwtUtil.extractUsername(token);
                    List<GrantedAuthority> authorities = List.of(); // možeš dodati ako čitaš role

                    UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, null, authorities);

                    // Postavi autentikaciju u kontekst
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    attributes.put("username", username);
                    return true;
                } catch (Exception e) {
                    response.setStatusCode(HttpStatus.FORBIDDEN);
                    return false;
                }
            }
        }

        response.setStatusCode(HttpStatus.FORBIDDEN);
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request,
                               ServerHttpResponse response,
                               WebSocketHandler wsHandler,
                               Exception exception) {
        // ne moraš ništa ovde
    }
}

