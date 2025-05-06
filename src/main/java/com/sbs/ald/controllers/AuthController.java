package com.sbs.ald.controllers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import com.sbs.ald.dto.LoginRequest;
import com.sbs.ald.dto.RefreshRequest;
import com.sbs.ald.dto.TokenResponse;
import com.sbs.ald.service.UserService;
import com.sbs.ald.util.JwtUtil;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

   

            // Vraća se JWT token
          
    @CrossOrigin
	@PostMapping("/login")
	public String login(@RequestBody LoginRequest loginDto) {
		try {
			
			String s = userService.signin(loginDto.getUsername(), loginDto.getPassword())
					.orElseThrow(() -> new HttpServerErrorException(HttpStatus.FORBIDDEN, "Login Failed"));			
			return s;
		} catch (Exception e) {
		
			throw new HttpServerErrorException(HttpStatus.FORBIDDEN, "Login Failed");
		}
	}
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshRequest request) {
        try {
            String username = jwtUtil.extractUsername(request.getRefreshToken());
            String newAccessToken = jwtUtil.generateRefreshToken(username);
            return ResponseEntity.ok(new TokenResponse(newAccessToken, request.getRefreshToken()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }
    }
}
