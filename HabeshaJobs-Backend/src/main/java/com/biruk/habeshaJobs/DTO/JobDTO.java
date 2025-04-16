package com.biruk.habeshaJobs.DTO;

import com.biruk.habeshaJobs.Model.Common.Address;
import com.biruk.habeshaJobs.Model.Job.Job;
import com.biruk.habeshaJobs.Model.Job.JobDescription;

import java.time.LocalDateTime;
import java.util.UUID;

public class JobDTO {

    private UUID jobId;
    private String jobTitle;
    private double salary;

    /*
    might be useful for recommending jobs to job seekers based on their location.
    Including the address field can be useful even if I already have an address in employerDTO.
    The reason is that the job itself might be located in a different place than the employer's address.
     */
    private Address address;
    private Job.JobType jobType;
    private JobDescription jobDescription;
    private LocalDateTime createdAt;
    private EmployerDTO employerDTO;


    public JobDTO() {

    }

    public JobDTO(UUID jobId, String jobTitle, double salary, Address address, Job.JobType jobType,
                  JobDescription jobDescription, LocalDateTime createdAt, EmployerDTO employerDTO) {

        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.salary = salary;
        this.address = address;
        this.jobType = jobType;
        this.jobDescription = jobDescription;
        this.createdAt = createdAt;
        this.employerDTO = employerDTO;
    }

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

    public Job.JobType getJobType() {
        return jobType;
    }

    public void setJobType(Job.JobType jobType) {
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

    public EmployerDTO getEmployerDTO() {
        return employerDTO;
    }

    public void setEmployerDTO(EmployerDTO employerDTO) {
        this.employerDTO = employerDTO;
    }


    @Override
    public String toString() {
        return "JobDTO{" +
                "jobId=" + jobId +
                ", jobTitle='" + jobTitle + '\'' +
                ", salary=" + salary +
                ", address='" + address + '\'' +
                ", jobType=" + jobType +
                ", jobDescription=" + jobDescription +
                ", createdAt=" + createdAt +
                ", employerDTO=" + employerDTO +
                '}';
    }
}
