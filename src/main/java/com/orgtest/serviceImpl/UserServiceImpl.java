package com.orgtest.serviceImpl;

import com.orgtest.entities.User;
import com.orgtest.repositories.UserRepository;
import com.orgtest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public User register(User user) {
        try {
            user.setRole("ADMIN");
            user.setEnabled(true);
            String encodePassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodePassword);
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
                userEx.setPassword(passwordEncoder.encode(user.getPassword()));
                userEx.setUserLastName(user.getUserLastName());
                if(user.getProfile()!=null) {
                    userEx.setProfile(user.getProfile());
                }
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

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getByEmail(String email) {
        User result = userRepository.findByEmail(email);
        return result;
    }
    //token
    //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
}
