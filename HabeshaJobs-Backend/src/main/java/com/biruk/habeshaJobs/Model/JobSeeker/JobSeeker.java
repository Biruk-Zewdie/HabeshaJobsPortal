package com.biruk.habeshaJobs.Model.JobSeeker;

import com.biruk.habeshaJobs.Model.Common.Address;
import com.biruk.habeshaJobs.Model.JobApplication;
import com.biruk.habeshaJobs.Model.User.User;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Component
@Entity
@Table(name = "JobSeeker")
public class JobSeeker {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID jobSeekerId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column (nullable = false)
    private String phoneNumber;


    private LocalDate dateOfBirth;

    @Embedded
    private Address address;


    @ElementCollection
    @CollectionTable(name = "jobSeeker_education", joinColumns = @JoinColumn(name = "jobSeekerId"))
    private List<Education> education = new ArrayList<>();
//    private List<String> skill;

    private String profilePictureUrl;
    private IsActiveJobSeeker isActiveJobSeeker;

    private String linkedInUrl;

    //JobSeeker skills mapped with skill levels
    @ElementCollection
    @CollectionTable(name = "jobSeeker_skills", joinColumns = @JoinColumn(name = "jobSeekerId"))
    @MapKeyColumn(name = "skill_name")
    @Column(name = "skill_level")
    private Map<String, SkillLevel> skills = new HashMap<>();

    //these are used to track the jobSeeker date of sign up
    private LocalDateTime dateOfJoining;

    private String resumeUrl;

    @ElementCollection
    @CollectionTable (name = "jobSeeker_workExperience", joinColumns = @JoinColumn(name = "jobSeekerId"))
    private List<WorkExperience> workExperiences = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "jobSeeker_Reference", joinColumns = @JoinColumn(name = "jobSeekerId"))
    private List<Reference> references = new ArrayList<>();


    @OneToMany (mappedBy = "jobSeeker", cascade = CascadeType.ALL)
    private List <JobApplication> jobApplication = new ArrayList<> ();

    @OneToOne
    @JoinColumn (name = "userId", nullable = false)
    private User user;


    public JobSeeker() {

    }

    public JobSeeker(UUID jobSeekerId, String firstName, String lastName, String phoneNumber, LocalDate dateOfBirth,
                     Address address, List<Education> education, String profilePictureUrl, IsActiveJobSeeker isActiveJobSeeker,
                     String linkedInUrl, Map<String, SkillLevel> skills, LocalDateTime dateOfJoining, String resumeUrl,
                     List<WorkExperience> workExperiences, List<Reference> references, List <JobApplication> jobApplication) {
        this.jobSeekerId = jobSeekerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.education = education;
        this.profilePictureUrl = profilePictureUrl;
        this.isActiveJobSeeker = isActiveJobSeeker;
        this.linkedInUrl = linkedInUrl;
        this.skills = skills;
        this.dateOfJoining = dateOfJoining;
        this.resumeUrl = resumeUrl;
        this.workExperiences = workExperiences;
        this.references = references;
        this.jobApplication = jobApplication;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Address getAddress (){
        return address;
    }

    public void setAddress (Address address) {
        this.address = address;
    }

    public List<Education> getEducation() {
        return education;
    }

    public void setEducation(List<Education> education) {
        this.education = education;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public IsActiveJobSeeker getIsActiveJobSeeker() {
        return isActiveJobSeeker;
    }

    public void setIsActiveJobSeeker(IsActiveJobSeeker isActiveJobSeeker) {
        this.isActiveJobSeeker = isActiveJobSeeker;
    }

    public String getLinkedInUrl() {
        return linkedInUrl;
    }

    public void setLinkedInUrl(String linkedInUrl) {
        this.linkedInUrl = linkedInUrl;
    }

    public Map<String, SkillLevel> getSkills() {
        return skills;
    }

    public void setSkills(Map<String, SkillLevel> skills) {
        this.skills = skills;
    }

    public LocalDateTime getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(LocalDateTime dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public String getResumeUrl() {
        return resumeUrl;
    }

    public void setResumeUrl(String resumeUrl) {
        this.resumeUrl = resumeUrl;
    }

    public List<WorkExperience> getWorkExperiences() {
        return workExperiences;
    }

    public void setWorkExperiences(List<WorkExperience> workExperiences) {
        this.workExperiences = workExperiences;
    }

    public List<Reference> getReferences() {
        return references;
    }

    public void setReferences(List<Reference> references) {
        this.references = references;
    }

    public List<JobApplication> getJobApplication() {
        return jobApplication;
    }

    public void setJobApplication(List<JobApplication> jobApplication) {
        this.jobApplication = jobApplication;
    }

    //Employment Status Enum
    public enum IsActiveJobSeeker {
        Yes,
        No,
    }

    //JobSeeker skills enum
    public enum SkillLevel{
        Beginner,
        Intermediate,
        Expert
    }

    @Override
    public String toString() {
        return "JobSeeker{" +
                "jobSeekerId=" + jobSeekerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                ", education=" + education +
                ", profilePictureUrl='" + profilePictureUrl + '\'' +
                ", isActiveJobSeeker=" + isActiveJobSeeker +
                ", linkedInUrl='" + linkedInUrl + '\'' +
                ", skills=" + skills +
                ", dateOfJoining=" + dateOfJoining +
                ", resumeUrl='" + resumeUrl + '\'' +
                ", workExperiences=" + workExperiences +
                ", references=" + references +
                '}';
    }
}


