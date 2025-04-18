package com.biruk.habeshaJobs.DAO;

import com.biruk.habeshaJobs.Model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserDAO extends JpaRepository<User, UUID> {

    public Optional<User> findByEmailAndPassword(String email, String password);
}
