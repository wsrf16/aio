package com.aio.portable.parkdb.dao.third.model;

import com.aio.portable.swiss.data.jpa.annotation.where.Like;

public class BookVO extends Book {
    private Long id;
    @Like(targetProperty = "name")
    private String name;
    private String author;
    private String description;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
