package com.narola.jetdevstest.repository;

import com.narola.jetdevstest.model.FileAccessLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogsRepository extends JpaRepository<FileAccessLog, Long> {
}

