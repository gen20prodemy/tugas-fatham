package com.jpa.jpa.repository;

import com.jpa.jpa.entity.OurUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<OurUsers, Long> {
    Optional<OurUsers> findByUsername(String username);
}
