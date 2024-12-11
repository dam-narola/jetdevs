package com.narola.jetdevstest.dto;

import java.time.LocalDateTime;

public class FileDetailsDTO {
    private Long id;
    private String fileName;
    private LocalDateTime uploadTime;
    private LocalDateTime accessTime;

    public FileDetailsDTO() {
    }

    public FileDetailsDTO(Long id, String fileName, LocalDateTime uploadTime, LocalDateTime accessTime) {
        this.id = id;
        this.fileName = fileName;
        this.uploadTime = uploadTime;
        this.accessTime = accessTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(LocalDateTime uploadTime) {
        this.uploadTime = uploadTime;
    }

    public LocalDateTime getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(LocalDateTime accessTime) {
        this.accessTime = accessTime;
    }
}
