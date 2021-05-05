package com.aio.portable.parkdb.dao.third.model;

import com.aio.portable.swiss.suite.storage.db.jpa.annotation.order.OrderBy;
import com.aio.portable.swiss.suite.storage.db.jpa.annotation.where.*;
import org.springframework.data.domain.Sort;

import java.util.List;

public class BookVO extends Book {
    @GreaterThan(targetProperty = "id")
    private Long idGreaterThanEqual;
    @LessThanEqual(targetProperty = "id")
    private Long idLessThanEqual;
    @Equal(targetProperty = "name")
    private String nameLike;
    @In(targetProperty = "author")
    private List<String> authorIn;
    @Like(targetProperty = "description")
    private String descriptionLike;
    @OrderBy(targetProperty = "id", direction = Sort.Direction.DESC, priority = 1)
    private String idOrderBy;

    public Long getIdGreaterThanEqual() {
        return idGreaterThanEqual;
    }

    public void setIdGreaterThanEqual(Long idGreaterThanEqual) {
        this.idGreaterThanEqual = idGreaterThanEqual;
    }

    public Long getIdLessThanEqual() {
        return idLessThanEqual;
    }

    public void setIdLessThanEqual(Long idLessThanEqual) {
        this.idLessThanEqual = idLessThanEqual;
    }

    public String getNameLike() {
        return nameLike;
    }

    public void setNameLike(String nameLike) {
        this.nameLike = nameLike;
    }

    public List<String> getAuthorIn() {
        return authorIn;
    }

    public void setAuthorIn(List<String> authorIn) {
        this.authorIn = authorIn;
    }

    public String getDescriptionLike() {
        return descriptionLike;
    }

    public void setDescriptionLike(String descriptionLike) {
        this.descriptionLike = descriptionLike;
    }

    public String getIdOrderBy() {
        return idOrderBy;
    }

    public void setIdOrderBy(String idOrderBy) {
        this.idOrderBy = idOrderBy;
    }
}
