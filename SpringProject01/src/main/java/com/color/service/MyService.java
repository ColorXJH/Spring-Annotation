package com.color.service;

import com.color.repository.MyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ColorXJH
 * @version 1.0
 * @description:
 * @date 2022/10/21 9:56
 */
@Service
public class MyService {
    @Autowired
    private MyRepository myRepository;

    @Override
    public String toString() {
        return "MyService{" +
                "myRepository=" + myRepository +
                '}';
    }
}
