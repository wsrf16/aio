package com.aio.portable.park.dao.slave.model;

import com.aio.portable.swiss.suite.storage.db.jpa.annotation.order.OrderBy;
import com.aio.portable.swiss.suite.storage.db.jpa.annotation.where.*;
import org.springframework.data.domain.Sort;

import java.util.List;

public class BookVO extends Book {
    @GreaterThan(targetProperty = "id")
    private Integer idGreaterThanEqual;
    @LessThanOrEqualTo(targetProperty = "id")
    private Integer idLessThanEqual;
    @Equal(targetProperty = "name")
    private String nameLike;
    @In(targetProperty = "author")
    private List<String> authorIn;
    @Like(targetProperty = "description")
    private String descriptionLike;
    @OrderBy(targetProperty = "id", direction = Sort.Direction.DESC, priority = 1)
    private String idOrderBy;

    public Integer getIdGreaterThanEqual() {
        return idGreaterThanEqual;
    }

    public void setIdGreaterThanEqual(Integer idGreaterThanEqual) {
        this.idGreaterThanEqual = idGreaterThanEqual;
    }

    public Integer getIdLessThanEqual() {
        return idLessThanEqual;
    }

    public void setIdLessThanEqual(Integer idLessThanEqual) {
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
