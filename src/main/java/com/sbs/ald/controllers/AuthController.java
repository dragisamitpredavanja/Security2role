package com.sbs.ald.controllers;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import com.sbs.ald.dto.LoginDto;
import com.sbs.ald.dto.LoginResponse;
import com.sbs.ald.dto.User;
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
	public String login(@RequestBody LoginDto loginDto) {
		try {
			
			String s = userService.signin(loginDto.getUsername(), loginDto.getPassword())
					.orElseThrow(() -> new HttpServerErrorException(HttpStatus.FORBIDDEN, "Login Failed"));			
			return s;
		} catch (Exception e) {
		
			throw new HttpServerErrorException(HttpStatus.FORBIDDEN, "Login Failed");
		}
	}
}
