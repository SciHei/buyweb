package com.scihei.buyweb.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.scihei.buyweb.common.repositoty.Cache;
import com.scihei.buyweb.common.repositoty.constant.CacheKindConstants;
import com.scihei.buyweb.common.repositoty.constant.RepositoryExceptionConstants;
import com.scihei.buyweb.common.repositoty.exception.RepositoryException;
import com.scihei.buyweb.common.service.exception.ServiceException;
import com.scihei.buyweb.product.repository.DO.Product;
import com.scihei.buyweb.product.repository.ProductMapper;
import com.scihei.buyweb.product.service.DTO.ProductDTO;
import com.scihei.buyweb.product.service.ProductService;
import com.scihei.buyweb.product.service.constant.ProductExceptionConstants;
import com.scihei.buyweb.store.service.constant.StoreServiceExceptionConstants;
import com.scihei.buyweb.until.SciHei;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {
    ProductMapper productMapper;
    Cache cache;
    @Autowired
    public ProductServiceImpl(ProductMapper productMapper, Cache cache) {
        this.productMapper = productMapper;
        this.cache = cache;
    }

    @Override
    public void addProduct(ProductDTO productDTO) {
        //获取要添加的商品
        Product product = (Product) SciHei.getByCopy(productDTO, Product.class);
        //----



        try{
            productMapper.insert(product);
        }catch (DuplicateKeyException e){
            //商品id重复
            throw new ServiceException(StoreServiceExceptionConstants.STORE_EXISTED);
        }catch (Exception e){
            throw new ServiceException(e.toString());
        }
    }

    @Override
    public void deleteProduct(Integer productId) {
        if(productId == null)
            throw new ServiceException(ProductExceptionConstants.PRODUCT_ID_NULL);
        if(productMapper.deleteByMap(Map.of("product_id", productId)) == 0)
            throw new ServiceException(ProductExceptionConstants.PRODUCT_NONENTITY);
        cache.delete(CacheKindConstants.PRODUCT, productId.toString());
    }

    @Override
    public void updateProduct(ProductDTO productDTO) {
        if(productDTO.getProductId() == null)
            throw new ServiceException(ProductExceptionConstants.PRODUCT_ID_NULL);
        Product product = (Product) SciHei.getByCopy(productDTO, Product.class);
        if(productMapper.update(product, new UpdateWrapper<Product>().eq("product_id", productDTO.getProductId())) == 0){
            throw new ServiceException(ProductExceptionConstants.PRODUCT_NONENTITY);
        }
        cache.delete(CacheKindConstants.PRODUCT, productDTO.getProductId().toString());
    }

    @Override
    public ProductDTO getProduct(Integer productId) {
        if(productId == null)
            throw new ServiceException(ProductExceptionConstants.PRODUCT_ID_NULL);
        Product product = null;
        try{
            product = (Product) cache.get(CacheKindConstants.PRODUCT, productId.toString());
        }catch (RepositoryException e){
            //缓存表示之前查过了并且数据库里也没有，就抛出异常过滤掉该请求
            if(e.getResult().equals(RepositoryExceptionConstants.VALUE_NULL))
                throw new ServiceException(StoreServiceExceptionConstants.STORE_NONENTITY);
        }

        if(product == null){
            product = productMapper.selectOne(new QueryWrapper<Product>().eq("product_id", productId));
            cache.set(CacheKindConstants.PRODUCT, productId.toString(), product);
        }

        if(product == null)
            throw new ServiceException(StoreServiceExceptionConstants.STORE_NONENTITY);
        return (ProductDTO) SciHei.getByCopy(product, ProductDTO.class);
    }

    @Override
    public List<ProductDTO> getProductList(String name) {
        List<Product> products = productMapper.selectList(new QueryWrapper<Product>().like("name", SciHei.getFuzzy(name)));

        return (List<ProductDTO>) (Object) SciHei.getListByCopy(products, ProductDTO.class);
    }
}
