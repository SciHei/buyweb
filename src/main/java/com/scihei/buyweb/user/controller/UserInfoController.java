package com.scihei.buyweb.user.controller;

import com.scihei.buyweb.common.HttpResultVO;
import com.scihei.buyweb.until.SciHei;
import com.scihei.buyweb.user.controller.VO.userinfo.request.UserAdminManageFieldVO;
import com.scihei.buyweb.user.controller.VO.userinfo.request.UserQueryFieldVO;
import com.scihei.buyweb.user.controller.VO.userinfo.request.UserSelfManageFieldVO;
import com.scihei.buyweb.user.controller.VO.userinfo.response.UserAllInfoVO;
import com.scihei.buyweb.user.controller.VO.userinfo.response.UserBasicInfoVO;
import com.scihei.buyweb.user.controller.VO.userinfo.response.UserDetailInfoVO;
import com.scihei.buyweb.user.controller.VO.userinfo.response.UserSummaryInfoVO;
import com.scihei.buyweb.user.service.DTO.UserInfoDTO;
import com.scihei.buyweb.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserInfoController {
    private final UserService userService;

    @Autowired
    public UserInfoController(UserService userService){
        this.userService = userService;
    }

    @Operation(summary = "获得客户概要信息列表")
    //至少用户权限
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/users")
    public HttpResultVO<List<UserSummaryInfoVO>> queryUserSummaryInfoList (
            UserQueryFieldVO userQueryFieldVO){
        //获得用户信息列表
        List<UserInfoDTO> userInfoDTOList = userService.getInfoList(userQueryFieldVO.getData());
        //通过用户信息列表映射获得用户概要信息列表
        List<UserSummaryInfoVO> userSummaryInfoVOList = (List<UserSummaryInfoVO>) (Object) SciHei.getListByCopy(userInfoDTOList, UserSummaryInfoVO.class);
        return HttpResultVO.success(userSummaryInfoVOList);
    }

    @Operation(summary = "获得客户基本信息")
    //至少用户权限
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/users/{username}/basic")
    public HttpResultVO<UserBasicInfoVO> queryUserBasicInfo(
            @PathVariable String username){
        //获得用户信息
        UserInfoDTO info = userService.getInfo(username);
        //映射获得用户基本信息
        UserBasicInfoVO userBasicInfoVO = (UserBasicInfoVO) SciHei.getByCopy(info, UserBasicInfoVO.class);
        return HttpResultVO.success(userBasicInfoVO);
    }

    @Operation(summary = "获得客户详细信息")
    //至少用户权限并且用户本人
    @PreAuthorize("hasRole('USER') && principal.username == #username")
    @GetMapping("/users/{username}/detail")
    public HttpResultVO<UserDetailInfoVO> queryUserDetailInfo(
            @PathVariable String username){
        //获得用户信息
        UserInfoDTO info = userService.getInfo(username);
        //映射获得用户详细信息
        UserDetailInfoVO userDetailInfoVO = (UserDetailInfoVO) SciHei.getByCopy(info, UserDetailInfoVO.class);
        return HttpResultVO.success(userDetailInfoVO);
    }

    @Operation(summary = "全量更新用户详细信息", description = "替换用户user-id的详细信息")
    //至少用户权限并且用户本人
    @PreAuthorize("hasRole('USER') && principal.username == #username")
    @PutMapping("/users/{username}/detail")
    public HttpResultVO<Boolean> updateUserSelf(
            @PathVariable String username, @RequestBody UserSelfManageFieldVO userSelfManageFieldVO){
        //映射获得userInfoDTO
        UserInfoDTO userInfoDTO = (UserInfoDTO) SciHei.getByCopy(userSelfManageFieldVO, UserInfoDTO.class);
        userInfoDTO.setUsername(username);
        //按用户名更新用户信息
        userService.updateInfo(userInfoDTO);
        return HttpResultVO.success(true);
    }

    @Operation(summary = "获得客户全部信息", description = "查看用户user-id的全部信息")
    //至少管理员权限
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/{username}")
    public HttpResultVO<UserAllInfoVO> queryUserAllInfo(
            @PathVariable String username){
        //获得用户信息
        UserInfoDTO info = userService.getInfo(username);
        //映射获得用户全部信息
        UserAllInfoVO userAllInfoVO = (UserAllInfoVO) SciHei.getByCopy(info, UserAllInfoVO.class);
        return HttpResultVO.success(userAllInfoVO);
    }

    @Operation(summary = "全量更新用户全部信息", description = "更新用户user-id的全部信息")
    //至少管理员权限
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/users/{username}")
    public HttpResultVO<Boolean> updateUserAdmin(
            @PathVariable String username, @RequestBody UserAdminManageFieldVO userAdminManageFieldVO){
        //映射获得userInfoDTO
        UserInfoDTO userInfoDTO = (UserInfoDTO) SciHei.getByCopy(userAdminManageFieldVO, UserInfoDTO.class);
        userInfoDTO.setUsername(username);
        //按用户名更新用户信息
        userService.updateInfo(userInfoDTO);
        return HttpResultVO.success(true);
    }

}
