package com.helper.dto.signup;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class SignupRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Codeforces ID is required")
    private String cf_id;

    @NotBlank(message = "Password is required")
    private String password;

    public SignupRequest() {
    }

    public SignupRequest(String name, String email, String cf_id, String password) {
        this.name = name;
        this.email = email;
        this.cf_id = cf_id;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCf_id() {
        return cf_id;
    }

    public void setCf_id(String cf_id) {
        this.cf_id = cf_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
}
