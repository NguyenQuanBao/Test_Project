package com.example.demo.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class ProductJava {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String image;
    private String cc;
    private String heHe;
    @ManyToMany(mappedBy = "enrollPicture")
    private Set<Tags> tags = new HashSet<>();
    public ProductJava() {
    }

    public ProductJava(String cc, String image) {
        this.image = image;
        this.cc = cc;
    }

    public ProductJava(Long id, String image, String cc, String heHe) {
        this.id = id;
        this.image = image;
        this.cc = cc;
        this.heHe = heHe;
    }

    public Set<Tags> getTags() {
        return tags;
    }

    public String getHeHe() {
        return heHe;
    }

    public void setHeHe(String heHe) {
        this.heHe = heHe;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public ProductJava(Long id, String image, String cc) {
        this.id = id;
        this.image = image;
        this.cc = cc;
    }
}
