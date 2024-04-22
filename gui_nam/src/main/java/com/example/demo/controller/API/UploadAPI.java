package com.example.demo.controller.API;
import com.example.demo.model.ProductJava;
import com.example.demo.repository.UploadRepo;
import com.example.demo.service.ImageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.HttpRequestHandlerServlet;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.List;
import java.util.Optional;
import java.util.Timer;

@RestController("apiPictureController")
@RequestMapping("/api/v1/picture")
public class UploadAPI {
    @Autowired
    UploadRepo uploadRepo;
    @Autowired
    ImageService imageService;
    Page<ProductJava> picturePage;
    public static String UPLOAD_DIRECTORY = "F:\\image\\";
    @GetMapping
    public ResponseEntity<List<ProductJava>> listCustomers(@PageableDefault(3) Pageable pageable) {
        Page<ProductJava> image = imageService.findAll(pageable);
        return new ResponseEntity<>(image.getContent() , HttpStatus.OK);
    }

    public String uploadImage(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        FileCopyUtils.copy(file.getBytes(), new File(UPLOAD_DIRECTORY + fileName));
        return fileName;
    }
    @PostMapping("/create")
    public ResponseEntity<ProductJava> upload(@RequestBody ProductJava productJava) throws IOException {
        ProductJava picture = uploadRepo.save(productJava);
        return new ResponseEntity<>(picture,HttpStatus.CREATED);
    }
    @GetMapping("/search")
    public ModelAndView listCustomersSearch(@Param("keyword") String keyword, Pageable pageable) {
        Page<ProductJava> image = null;
        if(keyword != null){
            image = this.imageService.searchPicture(pageable, keyword);
        }

        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("all", image);
        return modelAndView;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> edit(@PathVariable("id") Long id, @RequestBody ProductJava productJava) {
        if (uploadRepo.findById(id).isPresent()) {
            ProductJava picture = uploadRepo.findById(id).get();
            picture.setHeHe(productJava.getHeHe());
            uploadRepo.save(picture);
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> showDeleteForm(@PathVariable Long id) {
       if(uploadRepo.findById(id).isPresent()){
       uploadRepo.deleteById(id);
       return new ResponseEntity<Boolean>(true,HttpStatus.OK);
       }
       return new ResponseEntity<Boolean>(false,HttpStatus.OK);
    }

}
