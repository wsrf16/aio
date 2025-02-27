package com.aio.portable.park.bean.friend;

import lombok.Data;

@Data
public class BestFriend {
    private int id;
    private String name;
    private int age;

    public BestFriend() {}

    public BestFriend(Person person, Dog dog) {
        this.id = person.getId();
        this.name = person.getName();
        this.age = dog.getAge();
    }

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
