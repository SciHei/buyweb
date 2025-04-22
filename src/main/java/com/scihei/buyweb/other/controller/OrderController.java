package com.scihei.buyweb.other.controller;

import com.scihei.buyweb.common.HttpResultVO;
import com.scihei.buyweb.other.model.VO.order.OrderQueryFieldVO;
import com.scihei.buyweb.other.model.VO.order.read.OrderDetailInfoVO;
import com.scihei.buyweb.other.model.VO.order.read.OrderSummaryInfoVO;
import com.scihei.buyweb.other.model.VO.order.write.OrderSelfManageFieldVO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class OrderController {
    @Operation(summary = "获得订单概要信息列表", description = "查询符合查询字段所有的订单的概要信息")
    @GetMapping("/orders")
    public HttpResultVO<ArrayList<OrderSummaryInfoVO>> queryOrderSummaryInfoList(
            OrderQueryFieldVO orderQueryFieldVO){
        return null;
    }

    @Operation(summary = "获得订单详细信息", description = "查询订单order-id的详细信息")
    @GetMapping("/orders/{order-id}/detail")
    public HttpResultVO<OrderDetailInfoVO> queryOrderDetailInfo(
            @PathVariable("order-id") String orderId){
        return null;
    }

    @Operation(summary = "创建订单", description = "输入订单详细信息以创建订单")
    @PostMapping("/orders")
    public HttpResultVO<Boolean> createOrder(
            @RequestBody OrderSelfManageFieldVO orderSelfManageFieldVO){
        return null;
    }

    @Operation(summary = "收货订单", description = "确认订单order-id已收货")
    @PatchMapping ("/orders/{order-id}/?state=receive")
    public HttpResultVO<Boolean> receiveOrder(
            @PathVariable("order-id") Integer orderId){
        return null;
    }

    @Operation(summary = "发货订单", description = "确认订单order-id已发货")
    @PatchMapping("/orders/{order-id}/?state=ship")
    public HttpResultVO<Boolean> shipOrder(
            @PathVariable("order-id") Integer orderId){
        return null;
    }

}
