## Features to be corrected 
1. password validation should be done separately 
- the password should be at least 8 characters long
- the password should contain at least one uppercase letter
- the password should contain at least one lowercase letter
- the password should contain at least one digit
- the password should contain at least one special character
2. Custom Exception handling should be added
3. Avoid duplicate job posting by the same employer
4. Error message should be returned in a standard format when response is not successful.
5. solve the problem of the token working/valid for 24 hours(until the token expires) even if the user logs out and logs back in.

## Features to be added in the future 
1. JWT authentication should be added
2. email verification by sending a verification link to the user's email address should be added
3. password reset functionality should be added 
4. email should be sent to the user when they register
5. email should be sent to the user when they reset their password
6. messaging application within the job board application to handle communication between the job seeker and the employer should be added
7. recommendation based on their profile, location and job history should be added.
8. search functionality should be added to the job board application to allow users to search for jobs based on keywords, location, and other criteria.
9. job alert functionality should be added to allow users to set up alerts for new job postings that match their criteria.
10. job application tracking functionality should be added to allow users to track the status of their job applications.
11. job posting tracking functionality should be added to allow employers to track the status of their job postings.
12. job posting analytics functionality should be added to allow employers to track the performance of their job postings.
13. how to change this application into a real business.
14. make the application microservices based.
15. containerize the application using docker.
16. add a feature of CI/CD to the application using jenkins.
17. add malware/vires scanning for uploaded files such as resumes and profile pictures using AWS Lambda clamAV and file type/size validation using spring boot.
18. hide/visible fields such as phone number, email, etc. and access features like in-app message to employers and job seekers based on their subscription plan and membership using multiple DTOs for different plans, single DTO with conditional mapping or using JSON views (Spring features).

## new Things learned
1. Registration DTO to transfer input data from the user input to our entity/ DB instead of using the entity directly.
2. MultipartFile to upload files in Spring Boot.
3. validate the file type and size before uploading get the url
4. we use form-data instead of raw data to files in Postman.
5. we should use @ModelAttribute instead of @RequestBody if we are using form-data in Postman to upload files.
6. If you includes spring security in you dependency, it will automatically block all endpoints by default and we need to configure it to allow access to the endpoints we want.
7. The maximum file size for uploading files in Spring Boot is 1MB by default and we can change it by adding the following properties in the application.properties file:
   - spring.servlet.multipart.max-file-size=10MB
   - spring.servlet.multipart.max-request-size=10MB
8. It is a best practice to add JWT auth after the login endpoint built in order to keep in consideration JWT authorization for other endpoints. 
9. admin role creation using CommandLineRunner.
10. store admin credential in environment variables using intelliJ idea.(steps: run -> edit configurations -> environment variables -> add the variables) 


## End points 
1. registration for Employer ✅ tested on postman
2. registration for Job Seeker ✅ tested on postman
3. login for user (Job seeker and Employer) ✅ tested on postman
4. get all jobs 
5. get all jobs by employer id
6. get jobs by id 
7. get all jobs applied by job seeker using job seeker id
8. get employer by id 
9. get job seeker by id
10. get all job seekers
11. get all employers get all jobs with in a specific range of distance from the job seeker's location
12. get all jobs with in a specific range of salary from the job seeker's expected salary
13. get all jobs with in a specific range of experience from the job seeker's expected experience
14. get all jobs with in a specific range of education from the job seeker's expected education
15. get all jobs with in a specific range of skills from the job seeker's expected skills
16. post a job by employer id
17. update a job by employer id
18. update a job seeker profile by job seeker id
19. update an employer profile by employer id
20. delete a job by employer id
21. delete a job seeker profile by job seeker id
22. delete an employer profile by employer id

