package com.scihei.buyweb.user.service;

import com.scihei.buyweb.user.service.DTO.UserAccountDTO;
import com.scihei.buyweb.user.service.DTO.UserInfoDTO;

import java.util.List;

public interface UserService {
    void addAccount(UserAccountDTO userAccountDTO);
    void deleteAccount(String username);
    void changePassword(String username, String newPassword, String oldPassword);
    void updateInfo(UserInfoDTO userInfoDTO);
    UserInfoDTO getInfo(String username);
    List<UserInfoDTO> getInfoList(String fuzzyName);
}
