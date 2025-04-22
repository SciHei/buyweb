package com.scihei.buyweb.product.controller;

import com.scihei.buyweb.common.HttpResultVO;
import com.scihei.buyweb.product.controller.VO.product.request.ProductAdminManageFieldVO;
import com.scihei.buyweb.product.controller.VO.productkind.request.ProductKindQueryFieldVO;
import com.scihei.buyweb.product.controller.VO.productkind.response.ProductKindSummaryInfoVO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductKindController {
    @Operation(summary = "获得商品分类的概要信息列表", description = "查询满足查询字段的所有商品分类的概要信息")
    @GetMapping("/product-kinds")
    public HttpResultVO<ProductKindSummaryInfoVO> queryProductKindSummaryInfoList(
            ProductKindQueryFieldVO productKindQueryFieldVO){
        return null;
    }

    @Operation(summary = "创建商品分类", description = "输入商品详细信息以创建商品分类")
    @PostMapping("/product-kinds")
    public HttpResultVO<Boolean> createProductKind(
            @RequestBody ProductAdminManageFieldVO productAdminManageFieldDto){
        return null;
    }

    @Operation(summary = "更新商品分类", description = "输入商品详细信息以更新商品分类product-kind-id")
    @PutMapping("/product-kinds/{product-kind-id}")
    public HttpResultVO<Boolean> updateProductKind(
            @PathVariable("product-kind-id") Integer productKindId, @RequestBody ProductAdminManageFieldVO productAdminManageFieldDto){
        return null;
    }

    @Operation(summary = "删除商品分类", description = "删除商品分类product-kind-id")
    @DeleteMapping("/product-kinds/{product-kind-id}")
    public HttpResultVO<Boolean> deleteProductKind(
            @PathVariable("product-kind-id") Integer productKindId){
        return null;
    }
}
