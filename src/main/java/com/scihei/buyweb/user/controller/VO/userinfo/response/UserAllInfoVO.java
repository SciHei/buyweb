package com.scihei.buyweb.user.controller.VO.userinfo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class UserAllInfoVO extends UserDetailInfoVO {
    @JsonProperty("add-time")
    private String addTime;
}
