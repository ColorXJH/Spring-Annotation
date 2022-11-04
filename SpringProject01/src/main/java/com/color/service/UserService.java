package com.color.service;

import com.color.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ColorXJH
 * @version 1.0
 * @description:
 * @date 2022/11/4 9:59
 */
@Service
    public class UserService {
    @Autowired
    private UserRepository userRepository;
    public void insertUser(){
        userRepository.insert();
    }

}
