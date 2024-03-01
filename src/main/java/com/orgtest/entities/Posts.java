package com.orgtest.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String postURL;

    @ManyToOne
//    @JoinColumn(name = "user_id")
    private MultiFileWithUser multiFileWithUser;

}
