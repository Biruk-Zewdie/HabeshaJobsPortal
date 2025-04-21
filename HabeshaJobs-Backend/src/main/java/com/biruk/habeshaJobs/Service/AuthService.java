package com.biruk.habeshaJobs.Service;

import com.biruk.habeshaJobs.DAO.EmployerDAO;
import com.biruk.habeshaJobs.DAO.JobSeekerDAO;
import com.biruk.habeshaJobs.DAO.UserDAO;
import com.biruk.habeshaJobs.DTO.*;
import com.biruk.habeshaJobs.Exceptions.EmailAlreadyExistsException;
import com.biruk.habeshaJobs.Interfaces.FileStorageService;
import com.biruk.habeshaJobs.Model.Employer;
import com.biruk.habeshaJobs.Model.JobSeeker.JobSeeker;
import com.biruk.habeshaJobs.Model.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserDAO userDAO;
    private final JobSeekerDAO jobSeekerDAO;
    private final EmployerDAO employerDAO;
    private final FileStorageService fileStorageService;

    @Autowired
    public AuthService (UserDAO userDAO, JobSeekerDAO jobSeekerDAO, EmployerDAO employerDAO, FileStorageService fileStorageService) {
        this.userDAO = userDAO;
        this.jobSeekerDAO = jobSeekerDAO;
        this.employerDAO = employerDAO;
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

    //this method is used to validate email and password of the user before registering the user.
    // it is common for both job seeker and employer.

    private void validateUserCredentials (String email, String password){

        // check if the email is not null or blank
        if (email == null || email.isBlank()){
            throw new IllegalArgumentException("Email cannot be null or Blank");
        }


        if (!email.matches(emailPattern)){
            throw new IllegalArgumentException("The email that you have entered is not valid");
        }

        // check if the email is already exists in the database because it is unique user identifier.
        if (userDAO.findByEmail(email).isPresent()){
            throw new EmailAlreadyExistsException("Email " + email + " already exists");
        }

        // check if the password is not null or blank
        if (password == null || password.isBlank()){
            throw new IllegalArgumentException("password cannot be null or blank");
        }

        //check if the password is valid using regex
        if (!password.matches(passwordPattern)){
            throw new IllegalArgumentException("password is not valid");
        }
    }



    public OutgoingJobSeekerDTO registerJobSeeker (IncomingJobSeekerRegDTO JSeekerRegDTO) {
        IncomingUserRegDTO userDTO = JSeekerRegDTO.getUser();

        String jobSeekerEmail = userDTO.getEmail();
        String jobSeekerPassword = userDTO.getPassword();

        // validate the user credentials(email and password) before registering the user.
        validateUserCredentials(jobSeekerEmail, jobSeekerPassword);

        // save the profile picture and resume file to the file storage and get the URLs
        String profilePictureUrl = fileStorageService.saveFile(JSeekerRegDTO.getProfilePicture(), "profile_pictures");
        String resumeFileUrl = fileStorageService.saveFile(JSeekerRegDTO.getResumeFile(), "resumes");


        try{
            // create a new user instance and set the user properties with the data we get from the client(IncomingUserRegDTO).
            User user = new User(userDTO.getEmail(), userDTO.getPassword(), userDTO.getRole());

            // create a new jobSeeker instance and set the jobSeeker properties with the data we get from the client(IncomingJobSeekerRegDTO).
            JobSeeker jobSeeker = new JobSeeker();

            jobSeeker.setFirstName(JSeekerRegDTO.getFirstName());
            jobSeeker.setLastName(JSeekerRegDTO.getLastName());
            jobSeeker.setPhoneNumber(JSeekerRegDTO.getPhoneNumber());
            jobSeeker.setDateOfBirth(JSeekerRegDTO.getDateOfBirth());
            jobSeeker.setAddress(JSeekerRegDTO.getAddress());
            jobSeeker.setEducation(JSeekerRegDTO.getEducation());
            jobSeeker.setProfilePictureUrl(profilePictureUrl);
            jobSeeker.setLinkedInUrl(JSeekerRegDTO.getLinkedInUrl());
            jobSeeker.setSkills(JSeekerRegDTO.getSkills());
            jobSeeker.setResumeUrl(resumeFileUrl);
            jobSeeker.setWorkExperiences(JSeekerRegDTO.getWorkExperiences());
            jobSeeker.setReferences(JSeekerRegDTO.getReferences());
            jobSeeker.setUser(user);

            //Save the JobSeeker in our database
            JobSeeker savedJobSeeker = jobSeekerDAO.save(jobSeeker);

            // Now we need to return the jobSeekerDTO to the client
            return new OutgoingJobSeekerDTO(savedJobSeeker);

        }catch(DataIntegrityViolationException e){
            throw new RuntimeException("failed to register JobSeeker: " + e.getMessage(), e);
        }
    }
    
    public OutgoingEmployerDTO registerEmployer (IncomingEmployerRegDTO employerRegDTO){

        IncomingUserRegDTO userDTO = employerRegDTO.getUser();

        String employerEmail = userDTO.getEmail();
        String employerPassword = userDTO.getPassword();

        //validate the Employer Credentials (email and password) before registering the employer.
        validateUserCredentials(employerEmail, employerPassword);

        //save the logo picture to the file storage and get the Url.
        String logoPicturesUrl = fileStorageService.saveFile(employerRegDTO.getLogoPicture(), "logo_pictures");

        try{

            //Create a new user instance and set the user properties
            //this user instance is used to create the employer instance at the below code (employer.setUser(user)).
            User user = new User(userDTO.getEmail(), userDTO.getPassword(), userDTO.getRole());

            //create a new employer instance and set the employer properties with the data we get from the client(IncomingEmployerRegDTO).
            Employer employer = new Employer ();

            employer.setCompanyName(employerRegDTO.getCompanyName());
            employer.setPhoneNumber(employerRegDTO.getPhoneNumber());
            employer.setAddress(employerRegDTO.getAddress());
            employer.setIndustrySector(employerRegDTO.getIndustrySector());
            employer.setCompanyDescription(employerRegDTO.getCompanyDescription());
            employer.setLogoUrl(logoPicturesUrl);
            employer.setCompanySize(employerRegDTO.getCompanySize());
            employer.setUser(user);

            //save the employer in our database
            Employer savedEmployer = employerDAO.save(employer);

            //Now we need to return the OutgoingEmployerDTO to the client.
            return new OutgoingEmployerDTO(savedEmployer);



        }catch (DataIntegrityViolationException e){
            throw new RuntimeException("failed to register Employer: " + e.getMessage(), e);
        }
    }

    // this login method used to login the user whether it is a job seeker or employer.
    // it takes the user email and password and check if the user is found in the database.
    public Object login (IncomingUserLoginDTO userLoginDTO) {

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
            case JobSeeker -> new OutgoingJobSeekerDTO(returnedUser.getJobSeeker());
            case Employer -> new OutgoingEmployerDTO(returnedUser.getEmployer());
            default -> throw new IllegalStateException("Unexpected value: " + returnedUser.getRole());
        };
    }
}
