package com.biruk.habeshaJobs.DTO;

import com.biruk.habeshaJobs.Model.Common.Address;
import com.biruk.habeshaJobs.Model.Job.Job;
import com.biruk.habeshaJobs.Model.Job.JobDescription;

import java.time.LocalDate;

public class IncomingJobDTO {

    private String jobTitle;
    private double salary;
    private Address address;
    private Job.JobType jobType;
    private JobDescription jobDescription;
    private LocalDate applicationDeadline;
    private int numberOfOpenings;


    public IncomingJobDTO() {
    }

    public IncomingJobDTO(String jobTitle, double salary, Address address, Job.JobType jobType, JobDescription jobDescription, LocalDate applicationDeadline, int numberOfOpenings) {
        this.jobTitle = jobTitle;
        this.salary = salary;
        this.address = address;
        this.jobType = jobType;
        this.jobDescription = jobDescription;
        this.applicationDeadline = applicationDeadline;
        this.numberOfOpenings = numberOfOpenings;
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

    public LocalDate getApplicationDeadline() {
        return applicationDeadline;
    }

    public void setApplicationDeadline(LocalDate applicationDeadline) {
        this.applicationDeadline = applicationDeadline;
    }

    public int getNumberOfOpenings() {
        return numberOfOpenings;
    }

    public void setNumberOfOpenings(int numberOfOpenings) {
        this.numberOfOpenings = numberOfOpenings;
    }


    @Override
    public String toString() {
        return "IncomingJobDTO{" +
                "jobTitle='" + jobTitle + '\'' +
                ", salary=" + salary +
                ", address=" + address +
                ", jobType=" + jobType +
                ", jobDescription=" + jobDescription +
                ", applicationDeadline=" + applicationDeadline +
                ", numberOfOpenings=" + numberOfOpenings +
                '}';
    }
}
