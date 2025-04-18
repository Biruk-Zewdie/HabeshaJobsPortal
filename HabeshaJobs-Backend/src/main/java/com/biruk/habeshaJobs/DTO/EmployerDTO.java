package com.biruk.habeshaJobs.DTO;

import com.biruk.habeshaJobs.Model.Common.Address;
import com.biruk.habeshaJobs.Model.Employer;

import java.time.LocalDateTime;
import java.util.UUID;

public class EmployerDTO {

    private UUID employerId;
    private String companyName;
    private String email;
    private Address address;
    private String description;
    private String industrySector;
    private String logoUrl;
    private Employer.CompanySize companySize;
    private LocalDateTime registrationDate;


    public EmployerDTO() {

    }

    public EmployerDTO(Employer employer) {
        this.employerId = employer.getEmployerId();
        this.companyName = employer.getCompanyName();
        this.email = employer.getUser().getEmail();
        this.address = employer.getAddress();
        this.description = employer.getDescription();
        this.industrySector = employer.getIndustrySector();
        this.logoUrl = employer.getLogoUrl();
        this.companySize = employer.getCompanySize();
        this.registrationDate = employer.getRegistrationDate();
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIndustrySector() {
        return industrySector;
    }

    public void setIndustrySector(String industrySector) {
        this.industrySector = industrySector;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public Employer.CompanySize getCompanySize() {
        return companySize;
    }

    public void setCompanySize(Employer.CompanySize companySize) {
        this.companySize = companySize;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return "EmployerDTO{" +
                "employerId=" + employerId +
                ", companyName='" + companyName + '\'' +
                ", email='" + email + '\'' +
                ", address=" + address +
                ", description='" + description + '\'' +
                ", industrySector='" + industrySector + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                ", companySize='" + companySize + '\'' +
                ", registrationDate=" + registrationDate +
                '}';
    }
}
