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

    @Embedded
    private Address address;

    private String industrySector;
    private String description;
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

    public Employer(UUID employerId, String companyName, Address address, String industrySector, String description,
                    LocalDateTime registrationDate, String logoUrl, CompanySize companySize, List<Job> jobsPosted,
                    List <JobApplication> jobApplications) {

        this.employerId = employerId;
        this.companyName = companyName;
        this.address = address;
        this.industrySector = industrySector;
        this.description = description;
        this.registrationDate = registrationDate;
        this.logoUrl = logoUrl;
        this.companySize = companySize;
        this.jobsPosted = jobsPosted;
        this.jobApplications = jobApplications;
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

    public List<JobApplication> getJobApplications() {
        return jobApplications;
    }

    public void setJobApplications (List <JobApplication> jobApplications) {
        this.jobApplications = jobApplications;
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
                ", address=" + address +
                ", industrySector='" + industrySector + '\'' +
                ", description='" + description + '\'' +
                ", registrationDate=" + registrationDate +
                ", logoUrl='" + logoUrl + '\'' +
                ", companySize=" + companySize +
                ", jobsPosted=" + jobsPosted +
                ", jobApplications=" + jobApplications +
                '}';
    }
}
