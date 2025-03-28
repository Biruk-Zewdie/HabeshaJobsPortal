package com.biruk.habeshaJobs.Model;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@Entity
@Table(name = "Job")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "job_ID")
    private UUID jobId;

    @Column(nullable = false)
    private String jobTitle;

    private double salary;
    private String location;

    @Enumerated(EnumType.STRING)
    private JobType jobType;


    @Embedded
    private JobDescription jobDescription;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Employer employer;

    //no args constructor

    public Job() {

    }


    //All args constructor

    public Job(UUID jobId, String jobTitle, double salary, String location,
               JobType jobType, JobDescription jobDescription, LocalDateTime createdAt,
               LocalDateTime updatedAt, Employer employer) {
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.salary = salary;
        this.location = location;
        this.jobType = jobType;
        this.jobDescription = jobDescription;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.employer = employer;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
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

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public enum JobType {
        fullTime,
        partTime,
        contract,
        internship,
        remote
    }

    @Override
    public String toString() {
        return "Job{" +
                "jobId=" + jobId +
                ", jobTitle='" + jobTitle + '\'' +
                ", salary=" + salary +
                ", location='" + location + '\'' +
                ", jobType=" + jobType +
                ", jobDescription=" + jobDescription +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", employer=" + employer +
                '}';
    }
}
