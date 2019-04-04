package com.york.portable.swiss.sandbox;

import java.lang.reflect.Type;

class Fruit<T> extends Food {
    public T t;
}
class Food<T>{
    public T t;
    public Food(){
        Type genericSuperclass = getClass().getGenericSuperclass();
        System.out.println(genericSuperclass);
    }
}
class Tmp extends Fruit<Teacher>{}
class Teacher{}

class Container {
    public static void main(){
        new Fruit<Teacher>();                   // getGenericSuperclass of Fruit is com.art.Food
        new Fruit<Teacher>(){};                 // getGenericSuperclass of anonymous class is com.art.Fruit<com.art.Teacher>
        new Tmp();                              // same as above
    }
}
