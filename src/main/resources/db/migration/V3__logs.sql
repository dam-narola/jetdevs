CREATE TABLE file_access_logs
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    file_id     VARCHAR(255)          NOT NULL,
    user_name   VARCHAR(255)          NOT NULL,
    access_time datetime              NOT NULL,
    user_role   VARCHAR(255)          NOT NULL,
    CONSTRAINT pk_file_access_logs PRIMARY KEY (id)
);