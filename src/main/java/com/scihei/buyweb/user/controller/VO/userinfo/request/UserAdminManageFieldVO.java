package com.scihei.buyweb.user.controller.VO.userinfo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scihei.buyweb.user.repository.DO.Users;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class UserAdminManageFieldVO extends UserSelfManageFieldVO {
    @JsonProperty("account-state")
    private Users.AccountState accountState;
    @JsonProperty("info-state")
    private Users.InfoState infoState;
}
