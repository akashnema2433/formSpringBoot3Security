package com.orgtest.repositories;

import com.orgtest.entities.MultiFileWithUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MultiFilesUserRepo extends JpaRepository<MultiFileWithUser,Long> {
}
