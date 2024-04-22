package com.example.demo.repository;

import com.example.demo.model.ProductJava;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UploadRepo extends JpaRepository<ProductJava,Long> {
Page<ProductJava> findAll(Pageable pageable);
//Page<ProductJava> searchAllByHeHeContaining (Pageable pageable,String name);
@Query("SELECT c from ProductJava c WHERE c.heHe like %?1%")
Page<ProductJava> searchPics(Pageable pageable, String keyword);
}
