package com.example.demo.service;

import com.example.demo.model.ProductJava;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IImageService{

    Page<ProductJava> findAll(Pageable pageable);
Page<ProductJava> searchPicture(Pageable pageable,String keyword);
}
