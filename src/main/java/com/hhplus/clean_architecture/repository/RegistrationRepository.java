package com.hhplus.clean_architecture.repository;

import com.hhplus.clean_architecture.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
}
