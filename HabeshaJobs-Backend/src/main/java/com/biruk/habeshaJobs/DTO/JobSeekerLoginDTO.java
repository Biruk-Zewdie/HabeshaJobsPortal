package com.biruk.habeshaJobs.DTO;

public class JobSeekerLoginDTO {

    private String email;
    private String password;

    public JobSeekerLoginDTO() {
    }

    public JobSeekerLoginDTO(String email, String password) {
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
        return "JobSeekerLoginDTO{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
