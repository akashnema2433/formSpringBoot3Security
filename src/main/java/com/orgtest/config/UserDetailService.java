package com.orgtest.config;

import com.orgtest.entities.User;
import com.orgtest.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

public class UserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("User is loaded"+username);
        User user = userRepository.findByEmail(username);
        if(user==null){
            throw new UsernameNotFoundException("user not fount exception!!");
        }
        return new MyUserDetails(user);
    }
}
