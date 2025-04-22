package com.scihei.buyweb.service.impl;

import com.scihei.buyweb.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    private UserService userService;

    @Test
    public void aa() {
        System.out.println(userService.getInfo("1"));
        ;
    }
}