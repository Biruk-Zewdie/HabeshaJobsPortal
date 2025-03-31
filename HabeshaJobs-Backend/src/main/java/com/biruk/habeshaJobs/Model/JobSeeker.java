package com.biruk.habeshaJobs.Model;

import com.biruk.habeshaJobs.Model.Common.Address;
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

    @Column(nullable = false, unique = true)
    private String email;

    private LocalDate dateOfBirth;

    @Embedded
    private Address address;


    private String password;
    private List<String> education;
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
    private List<WorkExperience> workExperiences = new ArrayList<>();

    @ ElementCollection
    private List<Reference> references = new ArrayList<>();


    @OneToMany (mappedBy = "jobSeeker", cascade = CascadeType.ALL)
    private List <JobApplication> jobApplication = new ArrayList<> ();



    public JobSeeker() {

    }

    public JobSeeker(UUID jobSeekerId, String firstName, String lastName, String phoneNumber, String email, LocalDate dateOfBirth,
                     Address address, String password, List<String> education, String profilePictureUrl, IsActiveJobSeeker isActiveJobSeeker,
                     String linkedInUrl, Map<String, SkillLevel> skills, LocalDateTime dateOfJoining, String resumeUrl,
                     List<WorkExperience> workExperiences, List<Reference> references, List <JobApplication> jobApplication) {
        this.jobSeekerId = jobSeekerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getEducation() {
        return education;
    }

    public void setEducation(List<String> education) {
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
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                ", password='" + password + '\'' +
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


