package com.biruk.habeshaJobs.DAO;

import com.biruk.habeshaJobs.Model.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployerDAO extends JpaRepository<Employer, UUID> {

    /*
    so naming of the method below is important, it shows the path to the field we are looking for, here spring data
    jpa will looking for the field userId step by step based on the naming, so first it will look for Employer.User
    and then User.UserId
     */
    Optional<Employer> findByUser_UserId(UUID userId);

}
