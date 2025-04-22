package com.scihei.buyweb.user.controller.VO.userinfo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scihei.buyweb.user.repository.DO.Users;
import lombok.Data;

@Data
public class UserBasicInfoVO {
    private String username;
    private String name;
    @JsonProperty("account-state")
    private Users.AccountState accountState;
}
