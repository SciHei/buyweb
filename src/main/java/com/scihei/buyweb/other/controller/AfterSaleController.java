package com.scihei.buyweb.other.controller;

import com.scihei.buyweb.common.HttpResultVO;
import com.scihei.buyweb.other.model.VO.aftersale.AfterSaleQueryFieldVO;
import com.scihei.buyweb.other.model.VO.aftersale.read.AfterSaleDetailInfoVO;
import com.scihei.buyweb.other.model.VO.aftersale.read.AfterSaleSummaryInfoVO;
import com.scihei.buyweb.other.model.VO.aftersale.write.AfterSaleSelfManageFieldVO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class AfterSaleController {

    @Operation(summary = "获得售后申请概要信息列表", description = "查询符合查询字段的所有售后申请概要信息")
    @GetMapping("/after-sales")
    public HttpResultVO<ArrayList<AfterSaleSummaryInfoVO>> queryAfterSaleSummaryInfoList(
            AfterSaleQueryFieldVO afterSaleQueryFieldVO){

        return null;
    }

    @Operation(summary = "获得售后申请详细信息", description = "查询售后申请after-sales-id的详细信息")
    @GetMapping("/after-sales/{after-sale-id}/detail")
    public HttpResultVO<AfterSaleDetailInfoVO> queryAfterSaleDetailInfo(
            @PathVariable("after-sale-id") Integer AfterSaleId){
        return null;
    }

    @Operation(summary = "创建售后申请", description = "使用详细信息创建售后申请")
    @PostMapping("/after-sales")
    public HttpResultVO<Boolean> createAfterSale(
            @RequestBody AfterSaleSelfManageFieldVO afterSaleSelfManageFieldVO){
        return null;
    }

    @Operation(summary = "更新售后申请状态", description = "更新售后申请after-sale-id状态")
    @PatchMapping("/after-sales/{after-sale-id}/?{state}")
    public HttpResultVO<Boolean> auditAfterSale(
            @PathVariable("after-sale-id") Integer afterSaleId, @PathVariable String state){
        return null;
    }

}
