package com.example.demo.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Tags {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long TagId;
    private String TagName;
    @ManyToMany
    @JoinTable(
            name = "tags_picture",
            joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "picture_id")
    )
    private Set<ProductJava> enrollPicture = new HashSet<>();



    public Tags(Long tagId, String tagName) {
        TagId = tagId;
        TagName = tagName;
    }

    public Tags() {
    }

    public Long getTagId() {
        return TagId;
    }

    public void setTagId(Long tagId) {
        TagId = tagId;
    }

    public String getTagName() {
        return TagName;
    }

    public void setTagName(String tagName) {
        TagName = tagName;
    }
    Set<ProductJava> getEnrollPicture() {
        return enrollPicture;
    }
}
