package com.example.jobhunter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.jobhunter.domain.Job;
import com.example.jobhunter.domain.Skill;

@Repository
public interface JobRepository extends JpaRepository<Job, Long>,
        JpaSpecificationExecutor<Job> {

    List<Job> findBySkillsIn(List<Skill> skills);
}