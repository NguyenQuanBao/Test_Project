package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Demo26Application {

    public static void main(String[] args) {
        SpringApplication.run(Demo26Application.class, args);
        System.out.println("http://localhost:8082/product");
    }

}
