package com.biruk.habeshaJobs.DTO;

import com.biruk.habeshaJobs.Model.Common.Address;
import com.biruk.habeshaJobs.Model.Job.Job;
import com.biruk.habeshaJobs.Model.Job.JobDescription;
import com.biruk.habeshaJobs.Model.Job.JobRequiredSkill;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class OutgoingJobDTO {

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

//    private List<JobRequiredSkill> jobRequiredSkills; //this makes unnecessary very complicated nest in our response body
    private List <String> skillsRequired;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDate applicationDeadline;
    private int numberOfOpenings;

    private OutgoingEmployerDTO employer;


    public OutgoingJobDTO() {

    }

    public OutgoingJobDTO(UUID jobId, String jobTitle, double salary, Address address, Job.JobType jobType, JobDescription jobDescription,
                          List<String> skillsRequired, LocalDateTime createdAt, LocalDateTime updatedAt,
                          LocalDate applicationDeadline, int numberOfOpenings, OutgoingEmployerDTO employer) {

        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.salary = salary;
        this.address = address;
        this.jobType = jobType;
        this.jobDescription = jobDescription;
        this.skillsRequired = skillsRequired;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.applicationDeadline = applicationDeadline;
        this.numberOfOpenings = numberOfOpenings;
        this.employer = employer;
    }

    public OutgoingJobDTO(Job job) {
        this.jobId = job.getJobId();
        this.jobTitle = job.getJobTitle();
        this.salary = job.getSalary();
        this.address = job.getAddress();
        this.jobType = job.getJobType();
        this.jobDescription = job.getJobDescription();
        this.skillsRequired = job.getJobRequiredSkills().stream()
                .map(sr-> sr.getSkill().getSkillName()).toList();
        this.createdAt = job.getCreatedAt();
        this.updatedAt = job.getUpdatedAt();
        this.applicationDeadline = job.getApplicationDeadline();
        this.numberOfOpenings = job.getNumberOfOpenings();
        this.employer = new OutgoingEmployerDTO(job.getEmployer());

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

    public List<String> getSkillsRequired() {
        return skillsRequired;
    }

    public void setJobRequiredSkills(List<String> skillsRequired){
        this.skillsRequired = skillsRequired;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt () {
        return updatedAt;
    }

    public void setUpdatedAt (LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
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

    public OutgoingEmployerDTO getEmployer() {
        return employer;
    }

    public void setEmployerDTO(OutgoingEmployerDTO employer) {
        this.employer = employer;
    }

    @Override
    public String toString() {
        return "OutgoingJobDTO{" +
                "jobId=" + jobId +
                ", jobTitle='" + jobTitle + '\'' +
                ", salary=" + salary +
                ", address=" + address +
                ", jobType=" + jobType +
                ", jobDescription=" + jobDescription +
                ", skillsRequired=" + skillsRequired +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", applicationDeadline=" + applicationDeadline +
                ", numberOfOpenings=" + numberOfOpenings +
                ", employer=" + employer +
                '}';
    }
}
