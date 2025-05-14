package com.biruk.habeshaJobs.Mailing;

import com.biruk.habeshaJobs.DAO.EmployerDAO;
import com.biruk.habeshaJobs.DAO.JobSeekerDAO;
import com.biruk.habeshaJobs.Model.Employer;
import com.biruk.habeshaJobs.Model.JobSeeker.JobSeeker;
import com.biruk.habeshaJobs.Model.User.User;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class SuccessfulEmailVerificationContext extends AbstractEmailContext{

    private final JobSeekerDAO jobSeekerDAO;
    private final EmployerDAO employerDAO;


    public SuccessfulEmailVerificationContext(JobSeekerDAO jobSeekerDAO, EmployerDAO employerDAO) {
        this.jobSeekerDAO = jobSeekerDAO;
        this.employerDAO = employerDAO;
    }

    @Override
    public <T> void init (T context){

        User user = (User) context;

        String firstName;
        System.out.println(user.getUserId());
        UUID userId = user.getUserId();
        if (user.getRole() == User.Role.JobSeeker){
            JobSeeker jobSeeker = jobSeekerDAO.findByUser_userId(userId).orElseThrow(() ->
                    new NoSuchElementException("JobSeeker with userId: " + userId + " not found"));

            firstName = jobSeeker.getFirstName();

        }else if (user.getRole() == User.Role.Employer) {
            Employer employer = employerDAO.findByUser_UserId(userId).orElseThrow(() ->
                    new NoSuchElementException("Employer with userId: " + userId + " not found"));
            firstName = employer.getCompanyName();
        }else {
            throw new IllegalStateException("Unexpected user role " + user.getRole());
        }

        put("firstName", firstName);
        setTemplateLocation("MailingTemplates/RegistrationSuccessful");
        setSubject("Your email has been successfully verified!");
        setFrom("no-reply@habeshaJobs.com");
        setTo(user.getEmail());
    }
    public void buildLoginUrl(final String baseUrl) {
        final String loginUrl = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/auth/login")
                .toUriString();
        put("loginUrl", loginUrl); // Add to context model
    }
}
