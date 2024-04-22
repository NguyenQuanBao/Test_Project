package com.example.demo.controller;

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

@Controller
@RequestMapping("/product")
public class UploadController {
    @Autowired
    UploadRepo uploadRepo;
    @Autowired
    ImageService imageService;
    Page<ProductJava> picturePage;
    public static String UPLOAD_DIRECTORY = "F:\\image\\";
    @GetMapping
    public ModelAndView listCustomers(@PageableDefault(3) Pageable pageable) {
        Page<ProductJava> image = imageService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("all", image);
        return modelAndView;
    }

    @GetMapping("/uploadImage")
    public ModelAndView displayUploadForm() {
        ModelAndView modelAndView = new ModelAndView("AWD");
        modelAndView.addObject("ob", new ProductJava());
        return modelAndView;
    }
    public String uploadImage(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        FileCopyUtils.copy(file.getBytes(), new File(UPLOAD_DIRECTORY + fileName));
        return fileName;
    }
    @PostMapping("/conC")
    public ModelAndView upload(@RequestParam(value = "file") MultipartFile file, ProductJava productJava) throws IOException {
        ModelAndView modelAndView = new ModelAndView("AWD");
        productJava.setImage(uploadImage(file));
        uploadRepo.save(productJava);
        modelAndView.addObject("ob", new ProductJava());
        modelAndView.addObject("message","Picture upload successful");
        return modelAndView;
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

    @GetMapping("/update/{id}")
    public ModelAndView showEditForm(@PathVariable("id") Long id) {
        Optional<ProductJava> picture = uploadRepo.findById(id);
        if (picture.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("update");
            modelAndView.addObject("picture", picture.get());
            return modelAndView;
        }
        return null;
    }

    @PostMapping("/update")
    public ModelAndView updateCustomer(@ModelAttribute("picture") ProductJava picture) {
        uploadRepo.save(picture);
        ModelAndView modelAndView = new ModelAndView("update");
        modelAndView.addObject("picture", picture);
        modelAndView.addObject("message","Picture updated");
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        Optional<ProductJava> image = uploadRepo.findById(id);
        ModelAndView modelAndView = new ModelAndView("delete");
        modelAndView.addObject("picture", image.get());
        return modelAndView;
    }

    @PostMapping("/delete")
    public String deleteCustomer(@ModelAttribute("picture") ProductJava picture, RedirectAttributes redirect) {
        uploadRepo.deleteById(picture.getId());
        redirect.addFlashAttribute("message", "Delete image successfully");
        return "redirect:/product";
    }
}

