package com.scihei.buyweb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scihei.buyweb.user.controller.VO.useraccount.request.AccountVO;
import com.scihei.buyweb.user.controller.VO.useraccount.request.NewOldPasswordVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class UserAccountControllerTest {
    @Autowired
    MockMvc mockMvc;
    ObjectMapper mapper = new ObjectMapper();
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Test
    public void loginUser() throws Exception {
        mockMvc.perform(post("/tokens")
                        //USER角色
                        .header("Authorization", "Basic MTIzOjEyMw==")
                        .contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Disabled
    public void createUserAccount() throws Exception {
        AccountVO accountVO = new AccountVO();
        accountVO.setUsername("456");
        accountVO.setPassword("123");
        mockMvc.perform(post("/accounts")
                        .contentType("application/json;charset=UTF-8")
                        .content(mapper.writeValueAsString(accountVO)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void updateUserAccount() throws Exception {
        NewOldPasswordVO newOldPasswordVO = new NewOldPasswordVO();
        newOldPasswordVO.setNewPassword("123");
        newOldPasswordVO.setOldPassword("123");
        mockMvc.perform(put("/accounts/{username}", "123")
                        .header("Authorization", "Basic MTIzOjEyMw==")
                        .contentType("application/json;charset=UTF-8")
                        .content(mapper.writeValueAsString(newOldPasswordVO)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Disabled
    public void deleteUserAccount() throws Exception {
        mockMvc.perform(delete("/accounts/{username}", "456")
                        .header("Authorization", "Basic YWRtaW46MTIz")
                        .contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}