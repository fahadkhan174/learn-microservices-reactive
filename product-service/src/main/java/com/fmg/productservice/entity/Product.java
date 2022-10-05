package com.fmg.productservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Product {
    
    @Id
    private String id;
    private String description;
    private Integer price;

}
