package com.hhplus.clean_architecture.repository;

import com.hhplus.clean_architecture.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
