package com.biruk.habeshaJobs.DTO;

import com.biruk.habeshaJobs.Model.Common.Address;
import com.biruk.habeshaJobs.Model.Employer;
import com.biruk.habeshaJobs.Model.User.User;

import java.time.LocalDateTime;
import java.util.UUID;

public class OutgoingEmployerDTO {

    private UUID employerId;
    private String companyName;
    private String email;
    private User.Role role;
    private Address address;
    private String companyDescription;
    private String industrySector;
    private String logoUrl;
    private Employer.CompanySize companySize;
    private LocalDateTime registrationDate;


    public OutgoingEmployerDTO() {

    }

    public OutgoingEmployerDTO(Employer employer) {
        this.employerId = employer.getEmployerId();
        this.companyName = employer.getCompanyName();
        this.email = employer.getUser().getEmail();
        this.role = employer.getUser().getRole();
        this.address = employer.getAddress();
        this.companyDescription = employer.getCompanyDescription();
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
    public User.Role getRole() {
        return role;
    }
    public void setRole(User.Role role) {
        this.role = role;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
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
        return "OutgoingEmployerDTO{" +
                "employerId=" + employerId +
                ", companyName='" + companyName + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", address=" + address +
                ", companyDescription='" + companyDescription + '\'' +
                ", industrySector='" + industrySector + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                ", companySize='" + companySize + '\'' +
                ", registrationDate=" + registrationDate +
                '}';
    }
}
