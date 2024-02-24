package com.orgtest.entities;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Data
public class FileModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
    @Column(name = "name")
    String fileName;
    //    @Lob
//    @Column(name = "content")
//    String content;
    @Lob
    @Column(name = "content")
    private String content;
    @Column(name = "filetype")
    private String fileType;

}
