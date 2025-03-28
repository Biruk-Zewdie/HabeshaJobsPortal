package com.biruk.habeshaJobs.DAO;

import com.biruk.habeshaJobs.Model.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmployerDAO extends JpaRepository<Employer, UUID> {

}
