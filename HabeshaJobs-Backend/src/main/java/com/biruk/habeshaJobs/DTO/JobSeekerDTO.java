package com.biruk.habeshaJobs.DTO;

import com.biruk.habeshaJobs.Model.Common.Address;
import com.biruk.habeshaJobs.Model.Employer;
import com.biruk.habeshaJobs.Model.JobSeeker.Education;
import com.biruk.habeshaJobs.Model.JobSeeker.JobSeeker;
import com.biruk.habeshaJobs.Model.JobSeeker.Reference;
import com.biruk.habeshaJobs.Model.JobSeeker.WorkExperience;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class JobSeekerDTO {


    private UUID jobSeekerId;
    private String firstName;
    private String lastName;
    private String email;
    private Address address;
    private String profilePictureUrl;
    private String linkedInUrl;
    private String resumeUrl;
    private List<Education> education;
    private List<WorkExperience> workExperiences;
    private Map<String, JobSeeker.SkillLevel> skills;
    private List <Reference> references;

    public JobSeekerDTO() {
    }

    /*
     * Object based constructor
     * This constructor is used when we already have a JobSeeker object and we want to convert it into a DTO easily.
     * see this in use in get all job seekers method in JobSeekerServiceImpl
    * */
    public JobSeekerDTO(JobSeeker jobSeeker) {
        this.jobSeekerId = jobSeeker.getJobSeekerId();
        this.firstName = jobSeeker.getFirstName();
        this.lastName = jobSeeker.getLastName();
        this.email = jobSeeker.getUser().getEmail();
        this.address = jobSeeker.getAddress();
        this.profilePictureUrl = jobSeeker.getProfilePictureUrl();
        this.linkedInUrl = jobSeeker.getLinkedInUrl();
        this.resumeUrl = jobSeeker.getResumeUrl();
        this.education = jobSeeker.getEducation();
        this.workExperiences = jobSeeker.getWorkExperiences();
        this.skills = jobSeeker.getSkills();
        this.references = jobSeeker.getReferences();
    }

    /*
    * Field based constructor
    * used when we want to build a DTO from selected fields (partial data from the job seeker) OR
    * when you are in a method that deals with individual field values like register job seeker method in AuthService).
    * savedJobSeeker.getUser().getEmail() - this is nested, so we are assembling it piece by piece.
    * */

    public JobSeekerDTO(UUID jobSeekerId, String firstName, String lastName, String email, Address address, String profilePictureUrl, String linkedInUrl, String resumeUrl, List<Education> education, List<WorkExperience> workExperiences, Map<String, JobSeeker.SkillLevel> skills, List<Reference> references) {
        this.jobSeekerId = jobSeekerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.profilePictureUrl = profilePictureUrl;
        this.linkedInUrl = linkedInUrl;
        this.resumeUrl = resumeUrl;
        this.education = education;
        this.workExperiences = workExperiences;
        this.skills = skills;
        this.references = references;
    }


    public UUID getJobSeekerId() {
        return jobSeekerId;
    }

    public void setJobSeekerId(UUID jobSeekerId) {
        this.jobSeekerId = jobSeekerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getLinkedInUrl() {
        return linkedInUrl;
    }

    public void setLinkedInUrl(String linkedInUrl) {
        this.linkedInUrl = linkedInUrl;
    }

    public String getResumeUrl() {
        return resumeUrl;
    }

    public void setResumeUrl(String resumeUrl) {
        this.resumeUrl = resumeUrl;
    }

    public List<Education> getEducation() {
        return education;
    }

    public void setEducation(List<Education> education) {
        this.education = education;
    }

    public List<WorkExperience> getWorkExperiences() {
        return workExperiences;
    }

    public void setWorkExperiences(List<WorkExperience> workExperiences) {
        this.workExperiences = workExperiences;
    }

    public Map<String, JobSeeker.SkillLevel> getSkills() {
        return skills;
    }

    public void setSkills(Map<String, JobSeeker.SkillLevel> skills) {
        this.skills = skills;
    }

    public List<Reference> getReferences() {
        return references;
    }

    public void setReferences(List<Reference> references) {
        this.references = references;
    }

    @Override
    public String toString() {
        return "JobSeekerDTO{" +
                "jobSeekerId=" + jobSeekerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", profilePictureUrl='" + profilePictureUrl + '\'' +
                ", linkedInUrl='" + linkedInUrl + '\'' +
                ", resumeUrl='" + resumeUrl + '\'' +
                ", education=" + education +
                ", workExperiences=" + workExperiences +
                ", skills=" + skills +
                ", references=" + references +
                '}';
    }
}
