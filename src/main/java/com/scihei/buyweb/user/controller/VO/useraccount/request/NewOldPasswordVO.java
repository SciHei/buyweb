package com.scihei.buyweb.user.controller.VO.useraccount.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NewOldPasswordVO {
    @JsonProperty("new-password")
    private String newPassword;
    @JsonProperty("old-password")
    private String oldPassword;
}
