package com.narola.jetdevstest.repository;

import com.narola.jetdevstest.model.Files;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileUploadRepository extends JpaRepository<Files, Long> {
}

