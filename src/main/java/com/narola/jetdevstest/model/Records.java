package com.narola.jetdevstest.model;

import com.narola.jetdevstest.converter.JsonMapConverter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "file_records")
@NoArgsConstructor
@AllArgsConstructor
public class Records {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "file_id", nullable = false) // Foreign key relationship to File entity
    private Files file;

    @Lob
    @Convert(converter = JsonMapConverter.class)
    @Column(columnDefinition = "TEXT", nullable = true)
    private Map<String, Object> record;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Files getFile() {
        return file;
    }

    public void setFile(Files file) {
        this.file = file;
    }

    public Map<String, Object> getRecord() {
        return record;
    }

    public void setRecord(Map<String, Object> record) {
        this.record = record;
    }
}
