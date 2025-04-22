package com.scihei.buyweb.store.controller;

import com.scihei.buyweb.common.HttpResultVO;
import com.scihei.buyweb.store.controller.VO.request.StoreAdminManageFieldVO;
import com.scihei.buyweb.store.controller.VO.request.StoreQueryFieldVO;
import com.scihei.buyweb.store.controller.VO.response.StoreAllInfoVO;
import com.scihei.buyweb.store.controller.VO.response.StoreBasicInfoVO;
import com.scihei.buyweb.store.controller.VO.response.StoreDetailInfoVO;
import com.scihei.buyweb.store.controller.VO.response.StoreSummaryInfoVO;
import com.scihei.buyweb.store.controller.VO.request.StoreSelfManageFieldVO;
import com.scihei.buyweb.store.service.DTO.StoreDTO;
import com.scihei.buyweb.store.service.StoreService;
import com.scihei.buyweb.until.SciHei;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StoreController {
    StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @Operation(summary = "获得店铺概要信息列表")
    @GetMapping("/stores")
    public HttpResultVO<List<StoreSummaryInfoVO>> queryStoreSummaryInfoList(
            StoreQueryFieldVO storeQueryFieldVO){
        List<StoreDTO> storeDTOS = storeService.getStoreList(storeQueryFieldVO.getData());
        List<StoreSummaryInfoVO> storeSummaryInfoVOS = (List<StoreSummaryInfoVO>) (Object) SciHei.getListByCopy(storeDTOS, StoreSummaryInfoVO.class);
        return HttpResultVO.success(storeSummaryInfoVOS);
    }

    @Operation(summary = "获得店铺基本信息")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/stores/{store-id}/basic")
    public HttpResultVO<StoreBasicInfoVO> queryStoreBasicInfo(
            @PathVariable("store-id") Integer storeId){
        StoreDTO storeDTO = storeService.getStore(storeId);
        StoreBasicInfoVO storeBasicInfoVO = (StoreBasicInfoVO) SciHei.getByCopy(storeDTO, StoreBasicInfoVO.class);
        return HttpResultVO.success(storeBasicInfoVO);
    }

    @Operation(summary = "获得店铺详细信息")
    @PreAuthorize("hasRole('STORE') && principal.username == storeId")
    @GetMapping("/stores/{store-id}/detail")
    public HttpResultVO<StoreDetailInfoVO> queryStoreDetailInfo(
            @PathVariable("store-id") Integer storeId){
        StoreDTO storeDTO = storeService.getStore(storeId);
        StoreDetailInfoVO storeDetailInfoVO = (StoreDetailInfoVO) SciHei.getByCopy(storeDTO, StoreDetailInfoVO.class);
        return HttpResultVO.success(storeDetailInfoVO);
    }

    @Operation(summary = "获得店铺全部信息")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/stores/{store-id}")
    public HttpResultVO<StoreAllInfoVO> queryStoreAllInfo(
            @PathVariable("store-id") Integer storeId){
        StoreDTO storeDTO = storeService.getStore(storeId);
        StoreAllInfoVO storeAllInfoVO = (StoreAllInfoVO) SciHei.getByCopy(storeDTO, StoreAllInfoVO.class);
        return HttpResultVO.success(storeAllInfoVO);
    }

    @Operation(summary = "创建店铺")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/stores")
    public HttpResultVO<Boolean> createStore(
            @RequestBody StoreSelfManageFieldVO storeSelfManageFieldVO){
        StoreDTO storeDTO = (StoreDTO) SciHei.getByCopy(storeSelfManageFieldVO, StoreDTO.class);
        storeService.addStore(storeDTO);
        return HttpResultVO.success(true);
    }

    @Operation(summary = "全量更新店铺详细信息")
    @PreAuthorize("hasRole('STORE') && principal.username == storeId")
    @PostMapping("/stores/{store-id}/detail")
    public HttpResultVO<Boolean> updateStoreSelf(
            @PathVariable("store-id") Integer storeId, @RequestBody StoreSelfManageFieldVO storeSelfManageFieldVO){
        StoreDTO storeDTO = (StoreDTO) SciHei.getByCopy(storeSelfManageFieldVO, StoreDTO.class);
        storeDTO.setStoreId(storeId);
        storeService.update(storeDTO);
        return HttpResultVO.success(true);
    }

    @Operation(summary = "删除店铺")
    @PreAuthorize("(hasRole('STORE') && principal.username == storeId) || hasRole('ADMIN')")
    @DeleteMapping("/stores/{store-id}")
    public HttpResultVO<Boolean> deleteStore(
            @PathVariable("store-id") Integer storeId){
        storeService.delete(storeId);
        return HttpResultVO.success(true);
    }

    @Operation(summary = "全量更新店铺全部信息")
    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/stores/{store-id}")
    public HttpResultVO<Boolean> updateStoreAll(
            @PathVariable("store-id") Integer storeId, @RequestBody StoreAdminManageFieldVO storeAdminManageFieldVO){
        StoreDTO storeDTO = (StoreDTO) SciHei.getByCopy(storeAdminManageFieldVO, StoreDTO.class);
        storeDTO.setStoreId(storeId);
        storeService.update(storeDTO);
        return HttpResultVO.success(true);
    }


}
