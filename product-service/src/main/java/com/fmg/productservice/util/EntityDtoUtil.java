package com.fmg.productservice.util;

import org.springframework.beans.BeanUtils;

import com.fmg.productservice.dto.ProductDto;
import com.fmg.productservice.entity.Product;

public class EntityDtoUtil {

    private EntityDtoUtil() {
    }

    public static ProductDto toDto(Product product) {
        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(product, productDto);
        return productDto;
    }

    public static Product toEntity(ProductDto productDto) {
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);
        return product;
    }
}
