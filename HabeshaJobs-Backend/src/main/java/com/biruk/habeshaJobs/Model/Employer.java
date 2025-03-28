package com.biruk.habeshaJobs.Model;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Entity
@Table(name = "Employer")
public class Employer {

    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID employerId;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String email;
    private String password;
    private String city;
    private String state;
    private String country;
    private String industrySector;
    private String description;
    private LocalDateTime registrationDate;
    private String logoUrl;
    private CompanySize companySize;

    @OneToMany(mappedBy = "employer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Job> jobsPosted = new ArrayList<>();


    public Employer() {
    }

    public Employer(UUID employerId, String companyName, String email, String password, String city, String state,
                    String country, String industrySector, String description, LocalDateTime registrationDate,
                    String logoUrl, CompanySize companySize, List<Job> jobsPosted) {
        this.employerId = employerId;
        this.companyName = companyName;
        this.email = email;
        this.password = password;
        this.city = city;
        this.state = state;
        this.country = country;
        this.industrySector = industrySector;
        this.description = description;
        this.registrationDate = registrationDate;
        this.logoUrl = logoUrl;
        this.companySize = companySize;
        this.jobsPosted = jobsPosted;
    }


    public UUID getEmployerId() {
        return employerId;
    }

    public void setEmployerId(UUID employerId) {
        this.employerId = employerId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIndustrySector() {
        return industrySector;
    }

    public void setIndustrySector(String industrySector) {
        this.industrySector = industrySector;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public CompanySize getCompanySize() {
        return companySize;
    }

    public void setCompanySize(CompanySize companySize) {
        this.companySize = companySize;
    }

    public List<Job> getJobsPosted() {
        return jobsPosted;
    }

    public void setJobsPosted(List<Job> jobsPosted) {
        this.jobsPosted = jobsPosted;
    }

    public enum CompanySize{
        Small,
        Medium,
        Large,
    }

    @Override
    public String toString() {
        return "Employer{" +
                "employerId=" + employerId +
                ", companyName='" + companyName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", industrySector='" + industrySector + '\'' +
                ", description='" + description + '\'' +
                ", registrationDate=" + registrationDate +
                ", logoUrl='" + logoUrl + '\'' +
                ", companySize=" + companySize +
                ", jobsPosted=" + jobsPosted +
                '}';
    }
}
