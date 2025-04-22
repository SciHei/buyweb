package com.scihei.buyweb.product.controller;

import com.scihei.buyweb.common.HttpResultVO;
import com.scihei.buyweb.product.controller.VO.product.request.ProductQueryFieldVO;
import com.scihei.buyweb.product.controller.VO.product.response.ProductAllInfoVO;
import com.scihei.buyweb.product.controller.VO.product.response.ProductBasicInfoVO;
import com.scihei.buyweb.product.controller.VO.product.response.ProductDetailInfoVO;
import com.scihei.buyweb.product.controller.VO.product.response.ProductSummaryInfoVO;
import com.scihei.buyweb.product.controller.VO.product.request.ProductSelfManageFieldVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@Tag(name = "商品模块")
@RestController
public class ProductController {
    @Operation(summary = "获得商品的概要信息列表", description = "查询符合查询字段的所有商品的概要信息")
    @Parameters(value = {
            @Parameter(name = "fuzzy", description = "是否启用模糊搜索 启用则只需传入keywords；不启用则三个id只有一个非空会生效，用于精确查询"),
            @Parameter(name = "keywords", description = "模糊搜索关键词")
    })
    @GetMapping( "/products")
    public HttpResultVO<ArrayList<ProductSummaryInfoVO>> queryProductSummaryInfoList(
            ProductQueryFieldVO productQueryFieldVO) {
        return null;
    }

    @Operation(summary = "获得商品的基本信息", description = "查询商品product-id的基本信息")
    @GetMapping("/products/{product-id}/basic")
    public HttpResultVO<ProductBasicInfoVO> queryProductBasicInfo(
            @PathVariable("product-id") Integer productId){
        return null;
    }

    @Operation(summary = "获得商品的详细信息", description = "查询商品product-id的详细信息")
    @GetMapping("/products/{product-id}/detail")
    public HttpResultVO<ProductDetailInfoVO> queryProductDetailInfo(
            @PathVariable("product-id") Integer productId){
        return null;
    }

    @Operation(summary = "获得商品的全部信息", description = "查询商品product-id的全部信息")
    @GetMapping("/products/{product-id}")
    public HttpResultVO<ProductAllInfoVO> queryProductAllInfo(
            @PathVariable("product-id") Integer productId){
        return null;
    }

    @Operation(summary = "创建商品", description = "输入商品详细信息以创建商品")
    @PostMapping("/products")
    public HttpResultVO<Boolean> createProduct(
            @RequestBody ProductSelfManageFieldVO productSelfManageFieldVO){
        return null;
    }

    @Operation(summary = "全量更新商品详细信息", description = "输入商品详细信息以更新商品product-id的详细信息")
    @PutMapping("/products/{product-id}/detail")
    public HttpResultVO<Boolean> updateProduct(
            @PathVariable("product-id") Integer productId, @RequestBody ProductSelfManageFieldVO productSelfManageFieldVO){
        return null;
    }

    @Operation(summary = "删除商品", description = "删除商品product-id")
    @DeleteMapping("/products/{product-id}")
    public HttpResultVO<Boolean> deleteProduct(
            @PathVariable("product-id") Integer productId){
        return null;
    }

    @Operation(summary = "更新商品状态", description = "更新商品product-id的状态为state")
    @PatchMapping("/products/{product-id}/?{state}")
    public HttpResultVO<Boolean> auditProduct(
            @PathVariable("product-id") Integer productId, @PathVariable String state){
        return null;
    }

}
