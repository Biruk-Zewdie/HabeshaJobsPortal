package com.biruk.habeshaJobs.Model;

import com.biruk.habeshaJobs.Model.Common.Address;
import com.biruk.habeshaJobs.Model.Job.Job;
import com.biruk.habeshaJobs.Model.User.User;
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
    @Column (name = "employer_Id", nullable = false)
    private UUID employerId;

    @Column(nullable = false)
    private String companyName;

    private String phoneNumber;

    @Embedded
    private Address address;

    private String industrySector;
    private String companyDescription;
    private LocalDateTime registrationDate = LocalDateTime.now();
    private String logoUrl;
    private CompanySize companySize;

    //the purpose of mappedBy in the @OneToMany annotation is to specify the field

    @OneToMany(mappedBy = "employer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Job> jobsPosted = new ArrayList<>();

    @OneToMany(mappedBy = "employer", cascade = CascadeType.ALL)
    private List<JobApplication> jobApplications = new ArrayList<> ();     // this will hold the job applications associated with this job.

    @OneToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    public Employer() {
    }

    public Employer(UUID employerId, String companyName, String phoneNumber, Address address, String industrySector, String companyDescription,
                    LocalDateTime registrationDate, String logoUrl, CompanySize companySize, List<Job> jobsPosted,
                    List <JobApplication> jobApplications, User user) {

        this.employerId = employerId;
        this.companyName = companyName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.industrySector = industrySector;
        this.companyDescription = companyDescription;
        this.registrationDate = registrationDate;
        this.logoUrl = logoUrl;
        this.companySize = companySize;
        this.jobsPosted = jobsPosted;
        this.jobApplications = jobApplications;
        this.user = user;
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

    public List<JobApplication> getJobApplications() {
        return jobApplications;
    }

    public void setJobApplications (List <JobApplication> jobApplications) {
        this.jobApplications = jobApplications;
    }

    public User getUser() {
        return user;
    }

    public void setUser (User user){
        this.user = user;
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
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address=" + address +
                ", industrySector='" + industrySector + '\'' +
                ", companyDescription='" + companyDescription + '\'' +
                ", registrationDate=" + registrationDate +
                ", logoUrl='" + logoUrl + '\'' +
                ", companySize=" + companySize +
                ", jobsPosted=" + jobsPosted +
                ", jobApplications=" + jobApplications +
                ", user=" + user +
                '}';
    }
}
