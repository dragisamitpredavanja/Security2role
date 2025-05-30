package com.sbs.ald.dto;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class LoginDto {
    
    private String username;

    private String password;

    private String name;

    private String address;
    
    private String email;
    
    private Set<Role> roles = new HashSet<>();
    
   

    /**
     * Default constructor
     */
   

    /**
     * Partial constructor
     * @param username
     * @param password
     */
    public LoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public LoginDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
     * Full constructor
     * @param username
     * @param password
     */
    public LoginDto( String username, String password, String name, String address, String email, Set<Role> roles) {
	    
	    this.username = username;
	    this.password = password;
	    this.name = name;
	    this.address = address;
	    this.email = email;
	    this.roles = roles;
	}


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	

	

	}