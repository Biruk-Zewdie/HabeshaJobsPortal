package com.biruk.habeshaJobs.Service;

import com.biruk.habeshaJobs.DAO.*;
import com.biruk.habeshaJobs.DTO.*;
import com.biruk.habeshaJobs.Exceptions.EmailAlreadyExistsException;
import com.biruk.habeshaJobs.Interfaces.FileStorageService;
import com.biruk.habeshaJobs.Model.Common.GeoHelper;
import com.biruk.habeshaJobs.Model.Common.Skill;
import com.biruk.habeshaJobs.Model.Employer;
import com.biruk.habeshaJobs.Model.JobSeeker.JobSeeker;
import com.biruk.habeshaJobs.Model.JobSeeker.JobSeekerSkill;
import com.biruk.habeshaJobs.Model.User.User;
import com.biruk.habeshaJobs.SecurityConfig.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService {

    private final UserDAO userDAO;
    private final JobSeekerDAO jobSeekerDAO;
    private final EmployerDAO employerDAO;
    private final SkillDAO skillDAO;
    private final FileStorageService fileStorageService;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final GeoHelper geoHelper;

    @Autowired
    public AuthService (UserDAO userDAO, JobSeekerDAO jobSeekerDAO, EmployerDAO employerDAO, SkillDAO skillDAO,
                        FileStorageService fileStorageService, PasswordEncoder passwordEncoder,
                        JWTUtil jwtUtil, AuthenticationManager authenticationManager, GeoHelper geoHelper) {

        this.userDAO = userDAO;
        this.jobSeekerDAO = jobSeekerDAO;
        this.employerDAO = employerDAO;
        this.skillDAO = skillDAO;
        this.fileStorageService = fileStorageService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.geoHelper = geoHelper;
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
            User user = new User(
                    userDTO.getEmail(),
                    passwordEncoder.encode(userDTO.getPassword()),
                    userDTO.getRole());

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
//            jobSeeker.setJobSeekerSkill(JSeekerRegDTO.getJobSeekerSkills());
            jobSeeker.setResumeUrl(resumeFileUrl);
            jobSeeker.setWorkExperiences(JSeekerRegDTO.getWorkExperiences());
            jobSeeker.setReferences(JSeekerRegDTO.getReferences());
            jobSeeker.setUser(user);

            // set the location using the GeoHelper
            jobSeeker.setLocation(geoHelper.createPointFromAddress(JSeekerRegDTO.getAddress()));

            /*Steps
            1. get an input from the user with a fields skillName and skill level using IncomingJobSeekerSkillDTO.
            2. iterate through list of skills we get from the user and get the skill from the database if it is stored in a database, otherwise create a new skill
            3. set the skill in jobSeekerSkill in order to set in jobSeeker, because the jobSeeker and skill are linked with JobSeekerSkill.
            4. set a job in JobSeekerSkills back because they are interLinked (both side relation)
            5. save the JobSeeker in Database
            * */

            List<JobSeekerSkill> jobSeekerSkills = new ArrayList<>();

            for (IncomingJobSeekerSkillsDTO incomingJobSeekerSkillsDTO : JSeekerRegDTO.getJobSeekerSkills() ){

                String skillName = incomingJobSeekerSkillsDTO.getSkillName();
                Skill skill = skillDAO.findBySkillName(skillName).orElseGet(() ->
                {
                    Skill newSkill = new Skill();
                    newSkill.setSkillName(skillName);
                    return skillDAO.save(newSkill);
                });

                JobSeekerSkill jobSeekerSkill = new JobSeekerSkill();
                jobSeekerSkill.setSkill(skill);
                jobSeekerSkill.setJobSeeker(jobSeeker);
                jobSeekerSkill.setSkillLevel(incomingJobSeekerSkillsDTO.getSkillLevel());

                jobSeekerSkills.add(jobSeekerSkill);
            }

            jobSeeker.setJobSeekerSkill(jobSeekerSkills);

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
            User user = new User(
                    userDTO.getEmail(),
                    passwordEncoder.encode(userDTO.getPassword()),
                    userDTO.getRole());

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

//        User returnedUser = userDAO.findByEmailAndPassword(userLoginDTO.getEmail(), userLoginDTO.getPassword())
//                .orElseThrow(
//                        () -> new IllegalArgumentException("user not found with the given email and password"));
// the commented code above was used to check the email and password in the database.
// now we will use the authentication manager from spring security to authenticate the user.
        try{
            authenticationManager.authenticate (
                new UsernamePasswordAuthenticationToken(userLoginDTO.getEmail(), userLoginDTO.getPassword()));
        }catch (AuthenticationException e){
            throw new IllegalArgumentException("Invalid email or password", e);
        }

        //get the user from the database using the email and determine the role of the user.
        User returnedUser = userDAO.findByEmail(userLoginDTO.getEmail())
                .orElseThrow(()-> new IllegalArgumentException("user not found with the given email"));

        String token = jwtUtil.generateToken(returnedUser);

        System.out.println("the token is: " + token);

        return switch (returnedUser.getRole()) {
            case JobSeeker -> new OutgoingJobSeekerDTO(returnedUser.getJobSeeker(), token);
            case Employer -> new OutgoingEmployerDTO(returnedUser.getEmployer(), token);
            default -> throw new IllegalStateException("Unexpected value: " + returnedUser.getRole());
        };
    }
}
