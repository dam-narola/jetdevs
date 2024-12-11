package com.narola.jetdevstest.controller;

import com.narola.jetdevstest.dto.FileDetailsDTO;
import com.narola.jetdevstest.service.FileUploadProgressService;
import com.narola.jetdevstest.service.FileUploadService;
import com.narola.jetdevstest.service.ListDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/files")
public class FileController {

    private final FileUploadProgressService fileUploadProgressService;
    private final FileUploadService fileUploadService;
    private final ListDataService listDataService;

    public FileController(FileUploadProgressService fileUploadProgressService,
                          FileUploadService fileUploadService,
                          ListDataService listDataService) {
        this.fileUploadProgressService = fileUploadProgressService;
        this.fileUploadService = fileUploadService;
        this.listDataService = listDataService;
    }


    @PostMapping("/upload")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            fileUploadService.processFile(file);
            return ResponseEntity.ok("File uploaded successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during file upload");
        }
    }


    @GetMapping("/progress/{fileId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Integer> getProgress(@PathVariable String fileId) {
        int progress = fileUploadProgressService.getProgress(fileId);
        return ResponseEntity.ok(progress);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<List<FileDetailsDTO>> listFiles() {
        List<FileDetailsDTO> fileNames = listDataService.listFiles();
        return ResponseEntity.ok(fileNames);
    }

    @GetMapping("/{fileId}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Map<String, Object>> listRecords(@PathVariable Long fileId) {
        Map<String, Object> fileNames = listDataService.listRecords(fileId);
        return ResponseEntity.ok(fileNames);
    }

    @DeleteMapping("/{fileId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteFile(@PathVariable Long fileId) {
        fileUploadService.deleteFile(fileId);
        return ResponseEntity.noContent().build();
    }

}
