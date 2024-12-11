package com.narola.jetdevstest.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class FileUploadProgressService {

    private final ConcurrentMap<String, Integer> progressMap = new ConcurrentHashMap<>();

    public void updateProgress(String fileId, int progress) {
        progressMap.put(fileId, progress);
    }

    public int getProgress(String fileId) {
        return progressMap.getOrDefault(fileId, 0); // Default to 0 if no progress is recorded
    }

    public void clearProgress(String fileId) {
        progressMap.remove(fileId);
    }
}

