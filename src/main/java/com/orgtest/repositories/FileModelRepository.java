package com.orgtest.repositories;

import com.orgtest.entities.FileModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileModelRepository extends JpaRepository<FileModel, Long> {
}
