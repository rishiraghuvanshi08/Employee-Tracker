package com.springboot.SpringBootRestAPI.service;

import com.springboot.SpringBootRestAPI.config.MyUserDetails;
import com.springboot.SpringBootRestAPI.entity.User;
import com.springboot.SpringBootRestAPI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username+ "ihsir");
        Optional<User> optionalUser = userRepository.findByUsername(username);
        System.out.println("rishii");
        if(optionalUser.isPresent()){
            MyUserDetails myUserDetails = new MyUserDetails(optionalUser.get());
            return myUserDetails;
        }
        else {
            throw new UsernameNotFoundException("Could not find user!!");
        }
    }
}
