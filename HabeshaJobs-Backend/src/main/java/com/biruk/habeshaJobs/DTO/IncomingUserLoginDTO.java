package com.biruk.habeshaJobs.DTO;

/*
The purpose of IncomingUserLoginDTO is to transfer user auth data (email and password) from the client to the server
in order to authenticate (compare the data with the database) the user.
 */
public class IncomingUserLoginDTO {

    private String email;
    private String password;

    public IncomingUserLoginDTO() {
    }

    public IncomingUserLoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
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

    @Override
    public String toString() {
        return "IncomingUserLoginDTO{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
