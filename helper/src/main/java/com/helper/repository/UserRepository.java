package com.helper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.helper.entity.Users;

public interface UserRepository extends JpaRepository<Users, Long> {

}
