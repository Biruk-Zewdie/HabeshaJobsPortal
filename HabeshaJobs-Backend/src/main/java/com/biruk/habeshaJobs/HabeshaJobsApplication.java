package com.biruk.habeshaJobs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HabeshaJobsApplication {

	public static void main(String[] args) {

		SpringApplication.run(HabeshaJobsApplication.class, args);

		System.out.println("Habesha jobs launched!");

		String password = "Biruk1234!";
		String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+?',])[A-Za-z\\d!@#$%^&*()_+?',]{8,}$";

		System.out.println(password.matches(pattern));
	}

}
