package com.orgtest.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class MultiFileWithUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String description;


    @OneToMany(mappedBy = "multiFileWithUser",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Posts> posts;

}
