package com.biruk.habeshaJobs.DTO;

import com.biruk.habeshaJobs.Model.User.User;

public class UserRegistrationDTO {

    private String email;
    private String password;
    private User.Role role;

    public UserRegistrationDTO() {
    }

    public UserRegistrationDTO(String email, String password, User.Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User.Role getRole() {
        return role;
    }

    public void setRole(User.Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserRegistrationDTO{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
