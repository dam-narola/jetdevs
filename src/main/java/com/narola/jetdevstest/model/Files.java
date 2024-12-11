package com.narola.jetdevstest.model;

import com.narola.jetdevstest.converter.JsonMapConverter;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "files")
@NoArgsConstructor
@AllArgsConstructor
public class Files {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private LocalDateTime uploadTime;

    @Column(nullable = false)
    private LocalDateTime accessTime;

//    @Lob
//    @Convert(converter = JsonMapConverter.class)
//    @Column(columnDefinition = "TEXT", nullable = true)
//    private Map<String, Object> data;

    public Long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }

    public LocalDateTime getAccessTime() {
        return accessTime;
    }

//    public Map<String, Object> getData() {
//        return data;
//    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setUploadTime(LocalDateTime uploadTime) {
        this.uploadTime = uploadTime;
    }

    public void setAccessTime(LocalDateTime accessTime) {
        this.accessTime = accessTime;
    }

//    public void setData(Map<String, Object> data) {
//        this.data = data;
//    }
}

