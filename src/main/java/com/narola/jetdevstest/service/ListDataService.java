package com.narola.jetdevstest.service;

import com.narola.jetdevstest.dto.FileDetailsDTO;
import com.narola.jetdevstest.model.FileAccessLog;
import com.narola.jetdevstest.model.Files;
import com.narola.jetdevstest.model.Records;
import com.narola.jetdevstest.repository.FileUploadRepository;
import com.narola.jetdevstest.repository.LogsRepository;
import com.narola.jetdevstest.repository.RecordsRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ListDataService {
    private final FileUploadRepository fileUploadRepository;
    private final RecordsRepository recordsRepository;
    private final LogsRepository logsRepository;

    public ListDataService(FileUploadRepository fileUploadRepository, RecordsRepository recordsRepository, LogsRepository logsRepository) {
        this.fileUploadRepository = fileUploadRepository;
        this.recordsRepository = recordsRepository;
        this.logsRepository = logsRepository;
    }

    public List<FileDetailsDTO> listFiles() {
        List<FileDetailsDTO> fileDetailsDTOS = new ArrayList<>();
        fileUploadRepository.findAll().forEach(files ->
                fileDetailsDTOS.add(new FileDetailsDTO(
                        files.getId(),
                        files.getFileName(),
                        files.getUploadTime(),
                        files.getAccessTime()
                )));
        return fileDetailsDTOS;
    }

    public Map<String, Object> listRecords(Long fileId) {
        Files files = fileUploadRepository.findById(fileId).orElse(null);
        if (files == null)
            return new HashMap<>();
        files.setAccessTime(LocalDateTime.now());
        fileUploadRepository.save(files);
        Map<String, Object> stringObjectMap = new HashMap<>();
        List<Records> records1 = recordsRepository.findByFile_Id(fileId);
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        FileAccessLog fileAccessLog = new FileAccessLog();
        fileAccessLog.setFileId(String.valueOf(fileId));
        fileAccessLog.setUserName(authentication.getPrincipal().toString());
        fileAccessLog.setAccessTime(LocalDateTime.now());
        fileAccessLog.setUserRole(authentication.getAuthorities().stream().findAny().get().getAuthority());
        logsRepository.save(fileAccessLog);
        System.out.println(authentication);
        records1.forEach(records ->
                stringObjectMap.put(String.valueOf(records.getId()), records.getRecord()));
        return stringObjectMap;
    }

}
