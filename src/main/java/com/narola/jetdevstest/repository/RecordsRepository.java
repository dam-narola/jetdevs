package com.narola.jetdevstest.repository;

import com.narola.jetdevstest.model.Records;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordsRepository extends JpaRepository<Records, Long> {

    List<Records> findByFile_Id(Long id);
    void deleteByFile_Id(Long id);
}
