package com.biruk.habeshaJobs.Model.JobSeeker;

import com.biruk.habeshaJobs.Model.Common.Address;
import com.biruk.habeshaJobs.Model.Common.Skill;
import com.biruk.habeshaJobs.Model.JobApplication;
import com.biruk.habeshaJobs.Model.User.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonMerge;
import jakarta.persistence.*;
import org.locationtech.jts.geom.Point;
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
    @Column(name = "jobSeekerId")
    private UUID jobSeekerId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column (nullable = false)
    private String phoneNumber;


    private LocalDate dateOfBirth;

    @Embedded
    @JsonMerge // used to update specific field in nested fields or arrays without replacing the whole object. The old field will be replaced with the new one.
    private Address address;

    //Point → Refers to the geometry type, in this case a single point with latitude and longitude.
    //4326 → Refers to the SRID (Spatial Reference Identifier) for WGS 84, which is a standard for latitude and longitude coordinates.
    @Column(columnDefinition = "geometry(point, 4326)")
    private Point location;


    @ElementCollection
    @CollectionTable(name = "jobSeeker_education", joinColumns = @JoinColumn(name = "jobSeekerId"))
    @JsonMerge
    private List<Education> education = new ArrayList<>();
//    private List<String> skill;

    private String profilePictureUrl;
    private IsActiveJobSeeker isActiveJobSeeker;

    private String linkedInUrl;

    /*
    Deprecated because I have to normalize the JobSeekerSkill table in order to write queries to match the jobSeeker skills with the job required skills.
    Otherwise I should convert the skills Map key into a list of strings and fetch all the list of skills required in to the memory and compare them with the jobSeeker skills.
    This is not efficient for large data sets.
    */

    //JobSeeker skills mapped with skill levels
//    @ElementCollection
//    @CollectionTable(name = "jobSeeker_skills", joinColumns = @JoinColumn(name = "jobSeekerId"))
//    @MapKeyColumn(name = "skill_name")
//    @Column(name = "skill_level")
//    @JsonMerge
//    private Map<String, SkillLevel> skills = new HashMap<>();

    /*
    * Now in order to efficiently handle the matching of jobSeeker skills with the job required skills, we have to create a new table called JobSeekerSkill and set a many to one relationship with the JobSeeker and Skill tables.
    * This will allow us to match the jobSeeker skills with the job required skills using a join query which is more efficient than fetching all the skills in to the memory and comparing them.
    * */
    @OneToMany(mappedBy = "jobSeeker", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobSeekerSkill> jobSeekerSkill = new ArrayList<>();



    //these are used to track the jobSeeker date of sign up
    private LocalDateTime dateOfJoining = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime profileLastUpdatedAt = LocalDateTime.now();

    private String resumeUrl;

    @ElementCollection
    @CollectionTable (name = "jobSeeker_workExperience", joinColumns = @JoinColumn(name = "jobSeekerId"))
    @JsonMerge
    private List<WorkExperience> workExperiences = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "jobSeeker_Reference", joinColumns = @JoinColumn(name = "jobSeekerId"))
    @JsonMerge
    private List<Reference> references = new ArrayList<>();


    @OneToMany (mappedBy = "jobSeeker", cascade = CascadeType.ALL)
    private List <JobApplication> jobApplication = new ArrayList<> ();

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn (name = "userId", nullable = false)
    private User user;

    @PreUpdate
    public void setProfileLastUpdate(){
        this.profileLastUpdatedAt = LocalDateTime.now();
    }


    public JobSeeker() {

    }

    public JobSeeker(UUID jobSeekerId, String firstName, String lastName, String phoneNumber, LocalDate dateOfBirth,
                     Address address, Point location, List<Education> education, String profilePictureUrl, IsActiveJobSeeker isActiveJobSeeker,
                     String linkedInUrl, List<JobSeekerSkill> jobSeekerSkill, LocalDateTime dateOfJoining, LocalDateTime profileLastUpdatedAt, String resumeUrl,
                     List<WorkExperience> workExperiences, List<Reference> references, List <JobApplication> jobApplication, User user) {
        this.jobSeekerId = jobSeekerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.location = location;
        this.education = education;
        this.profilePictureUrl = profilePictureUrl;
        this.isActiveJobSeeker = isActiveJobSeeker;
        this.linkedInUrl = linkedInUrl;
        this.jobSeekerSkill = jobSeekerSkill;
        this.dateOfJoining = dateOfJoining;
        this.profileLastUpdatedAt = profileLastUpdatedAt;
        this.resumeUrl = resumeUrl;
        this.workExperiences = workExperiences;
        this.references = references;
        this.jobApplication = jobApplication;
        this.user = user;
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

    public Point getLocation () {
        return location;
    }

    public void setLocation (Point location) {
        this.location = location;
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

    public List<JobSeekerSkill> getJobSeekerSkill() {
        return jobSeekerSkill;
    }

    public void setJobSeekerSkill(List<JobSeekerSkill> jobSeekerSkill) {
        this.jobSeekerSkill = jobSeekerSkill;
    }

    public LocalDateTime getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(LocalDateTime dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public LocalDateTime getProfileLastUpdatedAt () {
        return profileLastUpdatedAt;
    }

    public void setProfileLastUpdatedAt (LocalDateTime profileLastUpdatedAt){

        this.profileLastUpdatedAt = profileLastUpdatedAt;
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

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    //Employment Status Enum
    public enum IsActiveJobSeeker {
        Yes,
        No,
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
                ", jobSeekerSkill=" + jobSeekerSkill +
                ", dateOfJoining=" + dateOfJoining +
                ", resumeUrl='" + resumeUrl + '\'' +
                ", workExperiences=" + workExperiences +
                ", references=" + references +
                ", jobApplication=" + jobApplication +
                ", user=" + user +
                '}';
    }
}


