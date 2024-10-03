package com.hhplus.clean_architecture.infrastructure.persistence;

import com.hhplus.clean_architecture.domain.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, String> {
}
