package com.scihei.buyweb.user.controller.VO.userinfo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scihei.buyweb.user.repository.DO.Users;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class UserDetailInfoVO extends UserBasicInfoVO {
    private String email;
    private Integer phone;
    @JsonProperty("info-state")
    private Users.InfoState infoState;
}
