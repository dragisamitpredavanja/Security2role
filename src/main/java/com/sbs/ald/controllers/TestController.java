package com.sbs.ald.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {
	 @GetMapping("/test")
	    @PreAuthorize("hasRole('ADMIN')")
	    @CrossOrigin
	    public String test() {
	        return "radi test";
	    }

}
