package com.aio.portable.park.dao.master.model;

import java.util.List;

public class UserConditionDTO extends User {
    private String nameLike;
    private String emailLike;
    private List<Integer> idIn;

    public String getNameLike() {
        return nameLike;
    }

    public void setNameLike(String nameLike) {
        this.nameLike = nameLike;
    }

    public String getEmailLike() {
        return emailLike;
    }

    public void setEmailLike(String emailLike) {
        this.emailLike = emailLike;
    }

    public List<Integer> getIdIn() {
        return idIn;
    }

    public void setIdIn(List<Integer> idIn) {
        this.idIn = idIn;
    }
}
