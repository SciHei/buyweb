package com.scihei.buyweb.user.controller;

import com.scihei.buyweb.common.HttpResultVO;
import com.scihei.buyweb.until.SciHei;
import com.scihei.buyweb.user.controller.VO.useraccount.request.AccountVO;
import com.scihei.buyweb.user.controller.VO.useraccount.request.NewOldPasswordVO;
import com.scihei.buyweb.user.service.DTO.UserAccountDTO;
import com.scihei.buyweb.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
public class UserAccountController {
    private final UserService userService;

    @Autowired
    public UserAccountController(UserService userService){
        this.userService = userService;
    }

    @Operation(summary = "用户登录", description = "创建一个token")
    @PostMapping("/tokens")
    public HttpResultVO<Boolean> loginUser() {
        return HttpResultVO.success(true, "登录成功");
    }

    @Operation(summary = "添加用户", description = "用户注册")
    @PostMapping("/accounts")
    public HttpResultVO<Boolean> createUserAccount(
            @RequestBody AccountVO accountVO){
        //映射获得UserAccountDTO
        UserAccountDTO userAccountDTO = (UserAccountDTO) SciHei.getByCopy(accountVO, UserAccountDTO.class);
        //添加用户
        userService.addAccount(userAccountDTO);
        return HttpResultVO.success(true);
    }

    //用户本人或管理员访问
    @PreAuthorize("(hasRole('USER') && principal.username == #username) || hasRole('ADMIN')")
    @Operation(summary = "更新用户", description = "更新用户密码")
    @PutMapping("/accounts/{username}")
    public HttpResultVO<Boolean> updateUserAccount(
            @PathVariable("username") String username, @RequestBody NewOldPasswordVO newOldPasswordVO){
        //更新用户密码
        userService.changePassword(username, newOldPasswordVO.getNewPassword(), newOldPasswordVO.getOldPassword());
        return HttpResultVO.success(true);
    }

    //用户本人或管理员访问
    @PreAuthorize("(hasRole('USER') && principal.username == #username) || hasRole('ADMIN')")
    @Operation(summary = "删除用户", description = "用户注销")
    @DeleteMapping ("/accounts/{username}")
    public HttpResultVO<Boolean> deleteUserAccount(
            @PathVariable @P("username") String username){
        //删除用户
        userService.deleteAccount(username);
        return HttpResultVO.success(true);
    }
}
