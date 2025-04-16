package com.biruk.habeshaJobs.DTO;

import com.biruk.habeshaJobs.Model.Common.Address;

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
    private String companySize;
    private LocalDateTime registrationDate;

    public EmployerDTO() {

    }

    public EmployerDTO(UUID employerId, String companyName, String email, Address address, String description,
                       String industrySector, String logoUrl, String companySize, LocalDateTime registrationDate) {
        this.employerId = employerId;
        this.companyName = companyName;
        this.email = email;
        this.address = address;
        this.description = description;
        this.industrySector = industrySector;
        this.logoUrl = logoUrl;
        this.companySize = companySize;
        this.registrationDate = registrationDate;
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

    public String getCompanySize() {
        return companySize;
    }

    public void setCompanySize(String companySize) {
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
