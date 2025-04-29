package com.biruk.habeshaJobs.DTO;

import com.biruk.habeshaJobs.Model.Common.Address;
import com.biruk.habeshaJobs.Model.Employer;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

// this DTO is used to register an employer and the fields below are the fields that are required to register an employer.
public class IncomingEmployerRegDTO {

    private String companyName;
    private String phoneNumber;
    private Address address;
    private String industrySector;

    @Size(max = 1000, message = "Company description must not exceed 1000 characters")
    private String companyDescription;
    private MultipartFile logoPicture;
    private Employer.CompanySize companySize;
    private IncomingUserRegDTO user;

    public IncomingEmployerRegDTO() {

    }

    public IncomingEmployerRegDTO(String companyName, String phoneNumber, Address address, String industrySector, String companyDescription,
                                  MultipartFile logoPicture, Employer.CompanySize companySize, IncomingUserRegDTO user) {
        this.companyName = companyName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.industrySector = industrySector;
        this.companyDescription = companyDescription;
        this.logoPicture = logoPicture;
        this.companySize = companySize;
        this.user = user;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getIndustrySector() {
        return industrySector;
    }

    public void setIndustrySector(String industrySector) {
        this.industrySector = industrySector;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

    public MultipartFile getLogoPicture() {
        return logoPicture;
    }

    public void setLogoPicture(MultipartFile logoPicture) {
        this.logoPicture = logoPicture;
    }

    public Employer.CompanySize getCompanySize() {
        return companySize;
    }

    public void setCompanySize(Employer.CompanySize companySize) {
        this.companySize = companySize;
    }

    public IncomingUserRegDTO getUser() {
        return user;
    }

    public void setUser(IncomingUserRegDTO user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "IncomingEmployerRegDTO{" +
                "companyName='" + companyName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address=" + address +
                ", industrySector='" + industrySector + '\'' +
                ", companyDescription='" + companyDescription + '\'' +
                ", logoPicture=" + logoPicture +
                ", companySize=" + companySize +
                ", user=" + user +
                '}';
    }
}
