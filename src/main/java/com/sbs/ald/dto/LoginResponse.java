package com.sbs.ald.dto;
import java.util.Set;

public class LoginResponse {
    private String token;
    private String username;
    private Set<Role> roles;

    public LoginResponse(String token, String username, Set<Role> roles) {
        this.token = token;
        this.username = username;
        this.roles = roles;
    }

    // Getteri i setteri
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
