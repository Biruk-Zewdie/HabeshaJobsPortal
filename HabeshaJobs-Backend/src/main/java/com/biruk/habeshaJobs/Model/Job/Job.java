package com.biruk.habeshaJobs.Model.Job;

import com.biruk.habeshaJobs.Model.Common.Address;
import com.biruk.habeshaJobs.Model.Employer;
import com.biruk.habeshaJobs.Model.JobApplication;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonMerge;
import jakarta.persistence.*;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@Entity
@Table(name = "job")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "job_Id", nullable = false)
    private UUID jobId;

    @Column(nullable = false)
    private String jobTitle;

    private double salary;

    @Embedded
    @JsonMerge // this is used to merge the address object when deserializing the JSON object
    private Address address;

    @Column(columnDefinition = "geometry(point, 4326)")
    private Point location; // this is used to store the location of the job in a spatial format. This will be used to find jobs based on location.

    @Enumerated(EnumType.STRING)
    private JobType jobType;


    @Embedded
    @JsonMerge
    private JobDescription jobDescription;

    private LocalDateTime createdAt = LocalDateTime.now();
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    private LocalDate applicationDeadline;

    private int numberOfOpenings;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobRequiredSkill> jobRequiredSkills;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employer_id", nullable = false)
    @JsonBackReference // this is used to prevent infinite recursion when serializing the employer object
    private Employer employer;


    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    private List <JobApplication> jobApplications; // this will hold the job applications associated with this job. This allows us to track which job seekers have applied for this job.


    //no args constructor
    public Job() {

    }

    //All args constructor

    public Job(UUID jobId, String jobTitle, double salary, Address address, Point location,
               JobType jobType, JobDescription jobDescription, LocalDateTime createdAt,
               LocalDateTime updatedAt, LocalDate applicationDeadline, int numberOfOpenings,
               List<JobRequiredSkill> jobRequiredSkills, Employer employer, List<JobApplication> jobApplications) {
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.salary = salary;
        this.address = address;
        this.location = location;
        this.jobType = jobType;
        this.jobDescription = jobDescription;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.applicationDeadline = applicationDeadline;
        this.numberOfOpenings = numberOfOpenings;
        this.jobRequiredSkills = jobRequiredSkills;
        this.employer = employer;
        this.jobApplications = jobApplications;
    }

    // The @PrePersist annotation is used to specify a method that should be called before the entity is persisted (saved) to the database.
    @PreUpdate
    public void setLastUpdated(){
        this.updatedAt = LocalDateTime.now();
    }
    //getters and setters

    public UUID getJobId() {
        return jobId;
    }

    public void setJobId(UUID jobId) {
        this.jobId = jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Point getLocation () {
        return location;
    }

    public void setLocation (Point location) {
        this.location = location;
    }

    public JobType getJobType() {
        return jobType;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }

    public JobDescription getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(JobDescription jobDescription) {
        this.jobDescription = jobDescription;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDate getApplicationDeadline () {
        return applicationDeadline;
    }

    public void setApplicationDeadline (LocalDate applicationDeadline) {
        this.applicationDeadline = applicationDeadline;
    }

    public int getNumberOfOpenings () {
        return numberOfOpenings;
    }

    public void setNumberOfOpenings (int numberOfOpenings) {
        this.numberOfOpenings = numberOfOpenings;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public List<JobRequiredSkill> getJobRequiredSkills() {
        return jobRequiredSkills;
    }

    public void setJobRequiredSkill(List<JobRequiredSkill> jobRequiredSkills) {
        this.jobRequiredSkills = jobRequiredSkills;
    }

    public List<JobApplication> getJobApplications() {
        return jobApplications;
    }

    public void setJobApplications(List<JobApplication> jobApplications) {
        this.jobApplications = jobApplications;
    }

    public enum JobType {
        fullTime,
        partTime,
        contract,
        internship,
        remote,
        hybrid
    }

    @Override
    public String toString() {
        return "Job{" +
                "jobId=" + jobId +
                ", jobTitle='" + jobTitle + '\'' +
                ", salary=" + salary +
                ", address='" + address + '\'' +
                ", location=" + location +
                ", jobType=" + jobType +
                ", jobDescription=" + jobDescription +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", applicationDeadline=" + applicationDeadline +
                ", numberOfOpenings=" + numberOfOpenings +
                ", employer=" + employer +
                ", jobRequiredSkills=" + jobRequiredSkills +
                ", jobApplications=" + jobApplications +
                '}';
    }
}
