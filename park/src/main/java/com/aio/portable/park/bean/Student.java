package com.aio.portable.park.bean;

import com.aio.portable.swiss.suite.db.ddl.ForeignKey;
import com.aio.portable.swiss.suite.db.ddl.PrimaryKey;
import com.aio.portable.swiss.suite.db.ddl.Unique;

import java.math.BigDecimal;
import java.util.Date;

public class Student {
    @PrimaryKey
    public int id;

    public String name;

    @ForeignKey(table = "Teacher", column = "id")
    public int teacherId;

    @Unique
    public String email;

    public Date birthDate;

    public BigDecimal salary;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
}
