package com.narola.jetdevstest.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "file_access_logs")
public class FileAccessLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_id", nullable = false)
    private String fileId;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "access_time", nullable = false)
    private LocalDateTime accessTime;

    @Column(name = "user_role", nullable = false)
    private String userRole;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userId) {
        this.userName = userId;
    }

    public LocalDateTime getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(LocalDateTime accessTime) {
        this.accessTime = accessTime;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "FileAccessLog{" +
                "id=" + id +
                ", fileId='" + fileId + '\'' +
                ", userId='" + userName + '\'' +
                ", accessTime=" + accessTime +
                ", userRole='" + userRole + '\'' +
                '}';
    }
}
