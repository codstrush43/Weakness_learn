package com.helper.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.helper.entity.Codeforces;

public interface CodeforcesRepository extends JpaRepository<Codeforces, Integer>{
    Optional<Codeforces> findByEmail(String email);
}
