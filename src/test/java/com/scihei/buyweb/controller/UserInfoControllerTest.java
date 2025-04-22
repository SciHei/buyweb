package com.scihei.buyweb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scihei.buyweb.user.controller.VO.userinfo.request.UserAdminManageFieldVO;
import com.scihei.buyweb.user.controller.VO.userinfo.request.UserSelfManageFieldVO;
import com.scihei.buyweb.user.repository.DO.Users;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class UserInfoControllerTest {
    @Autowired
    MockMvc mockMvc;
    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void queryUserSummaryInfoList() throws Exception {
        mockMvc.perform(get("/users")
                        //USER角色
                        .header("Authorization", "Basic MTIzOjEyMw==")
                        .contentType("application/json;charset=UTF-8")
                        .queryParam("data", "1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void queryUserBasicInfo() throws Exception {
        mockMvc.perform(get("/users/{username}/basic", "123")
                        .header("Authorization", "Basic MTIzOjEyMw==")
                        .contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void queryUserDetailInfo() throws Exception {
        mockMvc.perform(get("/users/{username}/detail", "123")
                        .header("Authorization", "Basic MTIzOjEyMw==")
                        .contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void updateUserSelf() throws Exception {
        UserSelfManageFieldVO userSelfManageFieldVO = new UserSelfManageFieldVO();
        userSelfManageFieldVO.setName("haha");
        userSelfManageFieldVO.setEmail("32157@");
        userSelfManageFieldVO.setPhone(186123);
        mockMvc.perform(put("/users/{username}/detail", "123")
                        .header("Authorization", "Basic MTIzOjEyMw==")
                        .contentType("application/json;charset=UTF-8")
                        .content(mapper.writeValueAsString(userSelfManageFieldVO)))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    public void queryUserAllInfo() throws Exception {
        mockMvc.perform(get("/users/{username}", "123")
                        //ADMIN角色
                        .header("Authorization", "Basic YWRtaW46MTIz")
                        .contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void updateUserAdmin() throws Exception {
        UserAdminManageFieldVO userAdminManageFieldVO = new UserAdminManageFieldVO();
        userAdminManageFieldVO.setAccountState(Users.AccountState.RUNNING);
        userAdminManageFieldVO.setInfoState(Users.InfoState.PASSED);
        mockMvc.perform(put("/users/{username}", "123")
                        .header("Authorization", "Basic YWRtaW46MTIz")
                        .contentType("application/json;charset=UTF-8")
                        .content(mapper.writeValueAsString(userAdminManageFieldVO)))
                .andExpect(status().isOk())
                .andDo(print());
    }
}