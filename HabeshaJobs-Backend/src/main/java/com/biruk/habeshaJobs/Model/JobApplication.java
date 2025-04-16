package com.biruk.habeshaJobs.Model;

import com.biruk.habeshaJobs.Model.Job.Job;
import com.biruk.habeshaJobs.Model.JobSeeker.JobSeeker;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;


/*
- this class breakdown many to many relationship between
        - Job and JobSeeker (many jobSeekers can apply for many jobs)
        - JobSeekers and employer (many jobSeekers can apply for many employers) into one to many to each other.
        - Also handles one to many relationship between Employer and jobSeeker.
- A job can have multiple job seekers applying for it and a A job seeker can apply for multiple jobs.
- This class can also be used to store additional information about the application, such as the date of application, status of the application
(e.g., pending, accepted, rejected), etc.
- By creating this entity, we can track which job seekers have applied for which jobs and vice versa.
- This allows for better management of job applications and can help employers and job seekers keep track of their interactions.
- The purpose of this class is mainly relates the job entity with the jobSeeker entity
- to track the number of jobSeekers applied for a specific job or the number of jobs applied for  )
*/

@Component
@Entity
public class JobApplication {

    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    @Column(name = "job_application_id", nullable = false)
    private UUID jobApplicationId;

    private LocalDateTime applicationDate = LocalDateTime.now();       // this will store the date and time when the job application was submitted.

    @ManyToOne (cascade = CascadeType.ALL, fetch = FetchType.LAZY)  // this will create a many to one relationship with the Job entity, indicating that multiple job applications can be associated with a single job.
    @JoinColumn(name = "job_id")
    private Job job;

    @ManyToOne (cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "job_Seeker_id")
    private JobSeeker jobSeeker; // this will create a many to one relationship with the JobSeeker entity, indicating that multiple job applications can be associated with a single job seeker.

    //This field helps as to track the number of job applications submitted for a specific employer
    // this might be useful to calculate the total number of jobSeekers applied for all jobs posted by a specific employer or to calculate the price the employer should pay based on the number of applications received for a job posting.
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "employer_Id")
    private Employer employer; // this will hold the reference to the employer who posted the job. This can be used to track which employer the job application is associated with.

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;


    public JobApplication() {
    }

    public JobApplication(UUID jobApplicationId, LocalDateTime applicationDate, Job job, JobSeeker jobSeeker, ApplicationStatus status) {
        this.jobApplicationId = jobApplicationId;
        this.applicationDate = applicationDate;
        this.job = job;
        this.jobSeeker = jobSeeker;
        this.status = status;
    }

    public UUID getJobApplicationId() {
        return jobApplicationId;
    }

    public void setJobApplicationId(UUID jobApplicationId) {
        this.jobApplicationId = jobApplicationId;
    }

    public LocalDateTime getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDateTime applicationDate) {
        this.applicationDate = applicationDate;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public JobSeeker getJobSeeker() {
        return jobSeeker;
    }

    public void setJobSeeker(JobSeeker jobSeeker) {
        this.jobSeeker = jobSeeker;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public enum ApplicationStatus {
        Pending,                        // this can be used to indicate that the job application has been submitted but not yet reviewed by the employer.
        Interviewing,
        OfferReceived,
        Hired,
        NotSelectedByEmployer,         // this can be used to indicate that the job seeker was not selected by the employer after the interview process.
        Withdrawn                      // this can be used to indicate that the job seeker has withdrawn their application for a job.
    }

    @Override
    public String toString() {
        return "JobApplicationDAO{" +
                "jobApplicationId=" + jobApplicationId +
                ", applicationDate=" + applicationDate +
                ", job=" + job +
                ", jobSeeker=" + jobSeeker +
                ", status=" + status +
                '}';
    }
}
