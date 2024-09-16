package com.example.jobhunter.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.jobhunter.domain.Company;
import com.example.jobhunter.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    User findByEmail(String email);

    boolean existsByEmail(String email);

    List<User> findByCompany(Company company);

    User findByRefreshTokenAndEmail(String token, String email);
}
