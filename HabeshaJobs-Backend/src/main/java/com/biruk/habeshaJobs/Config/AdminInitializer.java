package com.biruk.habeshaJobs.Config;

import com.biruk.habeshaJobs.DAO.UserDAO;
import com.biruk.habeshaJobs.Model.User.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

/*
🧭 Application Startup Flow
	1.	App starts (SpringApplication.run()).
        2.	A CommandLineRunner runs automatically after Spring context loads.
        3.	Inside the runner:
        •	The app reads the admin email & password from environment variables (e.g., application.properties or env file).
        •	It checks if a user with that email already exists.
	•	If not, it:
        •	Encrypts the password (using BCryptPasswordEncoder).
        •	Creates a new User with role ADMIN.
        •	Saves it to the users table in the DB.
	4.	✅ Now that admin exists in the database, you can log in via your login endpoint (/auth/login) using those credentials.
*/
@Configuration
public class AdminInitializer {

    @Bean
    public CommandLineRunner registerAdmin (UserDAO userDAO, PasswordEncoder passwordEncoder){
        return args -> {
            String AdminEmail = System.getenv("ADMIN_EMAIL");
            String AdminPassword = System.getenv("ADMIN_PASSWORD");

            if(AdminEmail == null || AdminPassword == null){
                System.out.println("Admin email and password are not set in the environment variables.");
                return;
            }

            Optional<User> existingAdmin = userDAO.findByEmail(AdminEmail);
            if (existingAdmin.isEmpty()){
                User admin = new User();
                admin.setEmail(AdminEmail);
                admin.setPassword(passwordEncoder.encode(AdminPassword));
                admin.setRole(User.Role.Admin);

                userDAO.save(admin);
                System.out.println("Admin user created with email: " + AdminEmail);
            }else {
                System.out.println("Admin user already exists with email: " + AdminEmail);
            }
        };
    }


}
