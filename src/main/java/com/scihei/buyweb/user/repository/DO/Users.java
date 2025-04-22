package com.scihei.buyweb.user.repository.DO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class Users {

    public enum InfoState{
        WAITING, PASSED, REJECTED
    }
    public enum AccountState{
        RUNNING, BANNED
    }

    private String username;
    private String password;
    private AccountState accountState;
    private String name;
    private String phone;
    private String email;
    private InfoState infoState;
    private String addTime;

}
