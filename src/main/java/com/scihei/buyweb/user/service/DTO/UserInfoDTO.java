package com.scihei.buyweb.user.service.DTO;

import com.scihei.buyweb.user.repository.DO.Users;
import lombok.Data;

@Data
public class UserInfoDTO {
    private String username;
    private String name;
    private Users.AccountState accountState;
    private String email;
    private Integer phone;
    private Users.InfoState infoState;
    private String addTime;

}
