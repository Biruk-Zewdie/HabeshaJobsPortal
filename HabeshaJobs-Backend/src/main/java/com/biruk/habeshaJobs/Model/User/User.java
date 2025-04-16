package com.biruk.habeshaJobs.Model.User;

import com.biruk.habeshaJobs.Model.Employer;
import com.biruk.habeshaJobs.Model.JobSeeker.JobSeeker;
import jakarta.persistence.*;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.UUID;


/*
- the user entity serves as the central authentication and authorization model.
- fields listed below should not be included in the jobSeeker and employer entities in order to avoid redundancy.
*/
@Component
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    @Column(nullable = false , unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne (mappedBy = "user", cascade = CascadeType.ALL)
    private JobSeeker jobSeeker;

    @OneToOne (mappedBy = "user", cascade = CascadeType.ALL)
    private Employer employer;

    public enum Role{
        JobSeeker,
        Employer,
        Admin
    }

    public User () {

    }

    public User (UUID userId, String email, String password, Role role, JobSeeker jobSeeker, Employer employer){
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.role = role;
        this.jobSeeker = jobSeeker;
        this.employer = employer;
    }

    public UUID getUserId () {
        return userId;
    }

    public void setUserId (UUID userId){
        this.userId = userId;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public JobSeeker getJobSeeker() {
        return jobSeeker;
    }

    public void setJobSeeker(JobSeeker jobSeeker) {
        this.jobSeeker = jobSeeker;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", jobSeeker=" + jobSeeker +
                ", employer=" + employer +
                '}';
    }
}
