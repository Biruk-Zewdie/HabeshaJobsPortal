package com.biruk.habeshaJobs.Service;

import com.biruk.habeshaJobs.DAO.JobSeekerDAO;
import com.biruk.habeshaJobs.DAO.UserDAO;
import com.biruk.habeshaJobs.DTO.*;
import com.biruk.habeshaJobs.Exceptions.EmailAlreadyExistsException;
import com.biruk.habeshaJobs.Interfaces.FileStorageService;
import com.biruk.habeshaJobs.Model.JobSeeker.JobSeeker;
import com.biruk.habeshaJobs.Model.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserDAO userDAO;
    private final JobSeekerDAO jobSeekerDAO;
    private final FileStorageService fileStorageService;

    @Autowired
    public AuthService (UserDAO userDAO, JobSeekerDAO jobSeekerDAO, FileStorageService fileStorageService) {
        this.userDAO = userDAO;
        this.jobSeekerDAO = jobSeekerDAO;
        this.fileStorageService = fileStorageService;
    }

    /*
    1. Email validation
        - should be unique
        - should be in a valid format
        - should not be empty
        - should not be null
    2. Password validation
    - should be at least 8 characters long
    - should contain at least one uppercase letter
    - should contain at least one special character
    - should contain at least one number
    - should contain at least one lowercase letter.

     */

    //validation pattern for email and password using regex
    String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+?',])[A-Za-z\\d!@#$%^&*()_+?',]{8,}$";


    public JobSeekerDTO registerJobSeeker (JobSeekerRegistrationDTO dto) {
        UserRegistrationDTO userDTO = dto.getUser();

        String jobSeekerEmail = userDTO.getEmail();
        String jobSeekerPassword = userDTO.getPassword();

        // save the profile picture and resume file to the file storage and get the URLs
        String profilePictureUrl = fileStorageService.saveFile(dto.getProfilePicture(), "profile_pictures");
        String resumeFileUrl = fileStorageService.saveFile(dto.getResumeFile(), "resumes");

        //Validation
        // check if the email is not null or blank

        if (jobSeekerEmail == null || jobSeekerEmail.isBlank()){
            throw new IllegalArgumentException("Email cannot be null or Blank");
        }


        if (!jobSeekerEmail.matches(emailPattern)){
            throw new IllegalArgumentException("The email that you have entered is not valid");
        }

        // check if the email is already exists in the database because it is unique user identifier.
        if (jobSeekerDAO.findByEmail(jobSeekerEmail).isPresent()){
            throw new EmailAlreadyExistsException("Email " + jobSeekerEmail + " already exists");
        }

        // check if the password is not null or blank
        if (jobSeekerPassword == null || jobSeekerPassword.isBlank()){
            throw new IllegalArgumentException("password cannot be null or blank");
        }

        //check if the password is valid using regex
        if (jobSeekerPassword.matches(passwordPattern)){
            throw new IllegalArgumentException("password is not valid");
        }

        try{
            // create a new user instance and set the user properties with the data we get from the client(UserRegistrationDTO).
            User user = new User(userDTO.getEmail(), userDTO.getPassword(), userDTO.getRole());

            // create a new jobSeeker instance and set the jobSeeker properties with the data we get from the client(JobSeekerRegistrationDTO).
            JobSeeker jobSeeker = new JobSeeker();

            jobSeeker.setFirstName(dto.getFirstName());
            jobSeeker.setLastName(dto.getLastName());
            jobSeeker.setPhoneNumber(dto.getPhoneNumber());
            jobSeeker.setDateOfBirth(dto.getDateOfBirth());
            jobSeeker.setAddress(dto.getAddress());
            jobSeeker.setEducation(dto.getEducation());
            jobSeeker.setProfilePictureUrl(profilePictureUrl);
            jobSeeker.setLinkedInUrl(dto.getLinkedInUrl());
            jobSeeker.setSkills(dto.getSkills());
            jobSeeker.setResumeUrl(resumeFileUrl);
            jobSeeker.setWorkExperiences(dto.getWorkExperiences());
            jobSeeker.setReferences(dto.getReferences());
            jobSeeker.setUser(user);

            //Save the JobSeeker in our database
            JobSeeker savedJobSeeker = jobSeekerDAO.save(jobSeeker);

            // Now we need to return the jobSeekerDTO to the client
            return new JobSeekerDTO(savedJobSeeker);

        }catch(DataIntegrityViolationException e){
            throw new RuntimeException("failed to register JobSeeker: " + e.getMessage(), e);
        }
    }

    public Object login (UserLoginDTO userLoginDTO) {

        // .isBlank() checks if the string is empty or contains only whitespace.
        if (userLoginDTO.getEmail() == null || userLoginDTO.getEmail().isBlank()){
            throw new IllegalArgumentException("email cannot be null or empty");
        }

        //pattern to check if the email is valid using regex

        if (!userLoginDTO.getEmail().matches(emailPattern)){
            throw new IllegalArgumentException("email is not valid");
        }


        // check if the password is null or empty
        if (userLoginDTO.getPassword() == null || userLoginDTO.getPassword().isBlank()){
            throw new IllegalArgumentException("password cannot be null or empty");
        }

        //(?=....) is a positive lookahead assertion that checks if the string contains at least one lowercase letter, one uppercase letter, one digit, and one special character.
        //(.*...) is used to match zero or more occurrences of any character.
        //{8,} means that the string must be at least 8 characters long.

        if (!userLoginDTO.getPassword().matches(passwordPattern)){
            throw new IllegalArgumentException("password is not valid");
        }

        /*
        - get the user from the database using userDAO if the input email and password by the user is -
          valid/matched with stored email and password in the database.
        - if the user is found return the user. If not found throw an exception.
        */

        User returnedUser = userDAO.findByEmailAndPassword(userLoginDTO.getEmail(), userLoginDTO.getPassword())
                .orElseThrow(
                        () -> new IllegalArgumentException("user not found with the given email and password"));

        return switch (returnedUser.getRole()) {
            case JobSeeker -> new JobSeekerDTO(returnedUser.getJobSeeker());
            case Employer -> new EmployerDTO(returnedUser.getEmployer());
            default -> throw new IllegalStateException("Unexpected value: " + returnedUser.getRole());
        };


    }
}
