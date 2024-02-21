package com.orgtest.service;

import com.orgtest.entities.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    User register(User user);
    User update(User user);
    List<User> getAll();
    User getById(Long id);

    User getByEmail(String email);

}
