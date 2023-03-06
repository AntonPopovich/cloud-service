package ru.netology.diplombackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Data
@Entity
@Transactional
@NoArgsConstructor
@Table(name = "files")
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    private String filename;
    private byte[] file;
    private long size;

    public FileEntity(String filename, byte[] file, long size) {
        this.filename = filename;
        this.file = file;
        this.size = size;
    }
}
