package com.orgtest.serviceImpl;

import com.orgtest.entities.User;
import com.orgtest.repositories.UserRepository;
import com.orgtest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public User register(User user) {
        try {
            user.setRole("ADMIN");
            user.setEnabled(true);
            User result = userRepository.save(user);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User update(User user) {
        try {
            Optional<User> isUserNotNull = userRepository.findById(user.getId());
            if (isUserNotNull.isPresent()) {
                User userEx = isUserNotNull.get();
                userEx.setUserFirstName(user.getUserFirstName());
                userEx.setUserLastName(user.getUserLastName());
                userEx.setProfile(user.getProfile());
                User result = userRepository.save(userEx);
                return result;
            }
            return null;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).get();
    }
}
