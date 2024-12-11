CREATE TABLE files
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    file_name   VARCHAR(255)          NOT NULL,
    upload_time datetime              NOT NULL,
    access_time datetime              NOT NULL,
    CONSTRAINT pk_files PRIMARY KEY (id)
);

CREATE TABLE file_records
(
    id      BIGINT AUTO_INCREMENT NOT NULL,
    file_id BIGINT NOT NULL,
    record  TEXT   NULL,
    CONSTRAINT pk_file_records PRIMARY KEY (id)
);

ALTER TABLE file_records
    ADD CONSTRAINT FK_FILE_RECORDS_ON_FILE FOREIGN KEY (file_id) REFERENCES files (id);