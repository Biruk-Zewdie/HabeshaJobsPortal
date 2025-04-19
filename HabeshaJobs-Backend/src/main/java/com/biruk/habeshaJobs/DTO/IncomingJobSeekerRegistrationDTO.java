package com.biruk.habeshaJobs.DTO;

import com.biruk.habeshaJobs.Model.Common.Address;
import com.biruk.habeshaJobs.Model.JobSeeker.Education;
import com.biruk.habeshaJobs.Model.JobSeeker.JobSeeker;
import com.biruk.habeshaJobs.Model.JobSeeker.Reference;
import com.biruk.habeshaJobs.Model.JobSeeker.WorkExperience;
import com.biruk.habeshaJobs.Model.User.User;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/*
* previously I have used jobSeeker entity as an input for registering a job seeker. This makes the code
* Tightly coupled/entity exposure - any changes to the entity (new fields, constraints, relations) can unintentionally break the client.
* Entities can contain sensitive data such as passwords, role, internal database IDs, so we don't want to expose or allow clients to modify/ manipulate that structure.
* Entities can be large and complex, our JobSeeker entity likely includes nested objects such as address, education, work experience, client might submitting a subset of those during registration.
* DTOs are great for custom validation (example @NotBlank, @NotNull, @Email, @Size) - we can add validation annotations to the fields of the DTO to ensure that the data being passed in meets our requirements.
*/

/*
* So this DTO contains only the fields that are required for registration. and should not contain fields like
* jobSeekerId - this is auto generated by the database
* isActiveJobSeeker - can be set by Admin
* dateOfJoining - this is auto generated by the database
* List<JobApplication> jobApplication - this is filled by the app after registration if the job seeker applies for a job
* */

public class JobSeekerRegistrationDTO {


    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private Address address;
    private List<Education> education;
    private MultipartFile profilePicture;  //could be .png, .jpg, .jpeg
    private String linkedInUrl;
    private Map<String, JobSeeker.SkillLevel> skills;
    private MultipartFile resumeFile;           //could be .pdf, .docx, .txt
    private List<WorkExperience> workExperiences;
    private List<Reference> references;
    private UserRegistrationDTO user;

    public JobSeekerRegistrationDTO() {
    }

    public JobSeekerRegistrationDTO (String firstName, String lastName, String phoneNumber, LocalDate dateOfBirth, Address address,
                                    List<Education> education, MultipartFile profilePicture, String linkedInUrl,
                                    Map<String, JobSeeker.SkillLevel> skills, MultipartFile resumeFile,
                                    List<WorkExperience> workExperiences, List<Reference> references, UserRegistrationDTO user) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.education = education;
        this.profilePicture = profilePicture;
        this.linkedInUrl = linkedInUrl;
        this.skills = skills;
        this.resumeFile = resumeFile;
        this.workExperiences = workExperiences;
        this.references = references;
        this.user = user;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Education> getEducation() {
        return education;
    }

    public void setEducation(List<Education> education) {
        this.education = education;
    }

    public MultipartFile getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(MultipartFile profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getLinkedInUrl() {
        return linkedInUrl;
    }

    public void setLinkedInUrl(String linkedInUrl) {
        this.linkedInUrl = linkedInUrl;
    }

    public Map<String, JobSeeker.SkillLevel> getSkills() {
        return skills;
    }

    public void setSkills(Map<String, JobSeeker.SkillLevel> skills) {
        this.skills = skills;
    }

    public MultipartFile getResumeFile() {
        return resumeFile;
    }

    public void setResumeFile(MultipartFile resumeFile) {
        this.resumeFile = resumeFile;
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

    public UserRegistrationDTO getUser() {
        return user;
    }

    public void setUser(UserRegistrationDTO user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "JobSeekerRegistrationDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", address=" + address +
                ", education=" + education +
                ", profilePicture=" + profilePicture +
                ", linkedInUrl='" + linkedInUrl + '\'' +
                ", skills=" + skills +
                ", resumeFile=" + resumeFile +
                ", workExperiences=" + workExperiences +
                ", references=" + references +
                ", user=" + user +
                '}';
    }
}
