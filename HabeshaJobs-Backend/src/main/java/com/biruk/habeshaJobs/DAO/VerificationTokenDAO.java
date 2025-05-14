package com.biruk.habeshaJobs.DAO;

import com.biruk.habeshaJobs.Model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VerificationTokenDAO extends JpaRepository<VerificationToken, UUID>{

     VerificationToken findByToken(String token);

     void removeByToken(String token);
}
