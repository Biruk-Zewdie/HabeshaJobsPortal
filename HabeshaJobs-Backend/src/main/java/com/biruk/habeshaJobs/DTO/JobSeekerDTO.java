package com.biruk.habeshaJobs.DTO;

import com.biruk.habeshaJobs.Model.JobSeeker;
import com.biruk.habeshaJobs.Model.Reference;
import com.biruk.habeshaJobs.Model.WorkExperience;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class JobSeekerDTO {


    private UUID jobSeekerId;
    private String firstName;
    private String lastName;
    private String email;
    private String zipcode;
    private String city;
    private String state;
    private String country;
    private String profilePictureUrl;
    private String linkedInUrl;
    private String resumeUrl;
    private List<String> education;
    private List<WorkExperience> workExperiences;
    private Map<String, JobSeeker.SkillLevel> skills;
    private List <Reference> references;

    public JobSeekerDTO() {
    }

    public JobSeekerDTO(UUID jobSeekerId, String firstName, String lastName, String email, String zipcode,
                        String city, String state, String country, String profilePictureUrl, String linkedInUrl,
                        String resumeUrl, List<String> education, List<WorkExperience> workExperiences,
                        Map<String, JobSeeker.SkillLevel> skills, List<Reference> references) {
        this.jobSeekerId = jobSeekerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.zipcode = zipcode;
        this.city = city;
        this.state = state;
        this.country = country;
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

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public List<String> getEducation() {
        return education;
    }

    public void setEducation(List<String> education) {
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
                ", zipcode='" + zipcode + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
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
