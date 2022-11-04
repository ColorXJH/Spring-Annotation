package com.color.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * @author ColorXJH
 * @version 1.0
 * @description:
 * @date 2022/11/4 10:00
 */
@Repository
public class UserRepository {
    @Autowired
    private JdbcTemplate template;


    @Transactional
    public void insert(){
        String sql="insert into test.employee(name,age)values(?,?)";
        String username= UUID.randomUUID().toString().substring(0,5);
        template.update(sql,username,28);
        System.out.println("--插入完成--");
        int i=10/0;
    }
}
