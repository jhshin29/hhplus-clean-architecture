package com.hhplus.clean_architecture.repository;

import com.hhplus.clean_architecture.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, String> {
}
