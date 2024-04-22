package com.example.demo.service;

import com.example.demo.model.ProductJava;
import com.example.demo.repository.UploadRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService implements IImageService{
    @Autowired
    UploadRepo uploadRepo;

    @Override
    public Page<ProductJava> findAll(Pageable pageable) {
        return uploadRepo.findAll(pageable);
    }

    @Override
    public Page<ProductJava> searchPicture(Pageable pageable , String keyword) {
        return this.uploadRepo.searchPics(pageable, keyword);
    }
}
