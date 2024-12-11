package com.narola.jetdevstest.service;

import com.narola.jetdevstest.model.Files;
import com.narola.jetdevstest.model.Records;
import com.narola.jetdevstest.repository.FileUploadRepository;
import com.narola.jetdevstest.repository.RecordsRepository;
import com.narola.jetdevstest.service.component.ExcelToCSVConverter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FileUploadService {

    private final FileUploadRepository fileUploadRepository;
    private final RecordsRepository recordsRepository;
    private final FileUploadProgressService fileUploadProgressService;

    public FileUploadService(FileUploadRepository fileUploadRepository,
                             RecordsRepository recordsRepository,
                             FileUploadProgressService fileUploadProgressService) {
        this.fileUploadRepository = fileUploadRepository;
        this.recordsRepository = recordsRepository;
        this.fileUploadProgressService = fileUploadProgressService;
    }

    public void processFile(MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
        Files files = new Files();
        files.setFileName(file.getOriginalFilename());
        files.setUploadTime(LocalDateTime.now());
        files.setAccessTime(LocalDateTime.now());
        fileUploadRepository.save(files);
        fileUploadProgressService.updateProgress(String.valueOf(files.getId()), 0);  // Initialize progress as 0

        InputStream csvInputStream = ExcelToCSVConverter.convertExcelToCSVInputStream(file);

        // Now use the csvInputStream for further processing, e.g., reading with BufferedReader
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(csvInputStream));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            // Get the header and total number of rows
            Map<String, Integer> headerMap = csvParser.getHeaderMap();
            List<CSVRecord> records = csvParser.getRecords();
            int totalRows = records.size();
            int processedRows = 0;

            // Assuming each CSV record corresponds to a row
            for (CSVRecord csvRecord : records) {
                // Process each column (cell) for the row
                Map<String, Object> stringObjectMap = new HashMap<>();
                for (String header : headerMap.keySet()) {
                    String value = csvRecord.get(header);
                    stringObjectMap.put(header, value);
                }
                // Update progress for every 10 rows processed
                processedRows++;
                if (processedRows % 10 == 0 || processedRows == totalRows) {
                    int progress = (processedRows * 100) / totalRows;  // Calculate percentage
                    fileUploadProgressService.updateProgress(String.valueOf(files.getId()), progress); // Update progress
                }
                Records records1 = new Records();
                records1.setFile(files);
                records1.setRecord(stringObjectMap);
                recordsRepository.save(records1);  // Save the record to the database
            }
            // Save the data for the row
            fileUploadRepository.save(files);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        // Mark upload completion
        fileUploadProgressService.updateProgress(String.valueOf(files.getId()), 100); // Set progress to 100% once done
    }

    @Transactional
    public void deleteFile(Long fileId) {
        recordsRepository.deleteByFile_Id(fileId);  // Delete all records associated with the file
        if (fileUploadRepository.existsById(fileId))
            fileUploadRepository.deleteById(fileId);
        fileUploadProgressService.clearProgress(String.valueOf(fileId)); // Clear progress for the file
    }
}

