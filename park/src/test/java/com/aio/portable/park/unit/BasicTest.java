package com.aio.portable.park.unit;

import com.aio.portable.park.bean.friend.BestFriend;
import com.aio.portable.park.bean.friend.Dog;
import com.aio.portable.park.bean.friend.Person;
import com.aio.portable.swiss.sugar.streamjoin.Join;
import com.aio.portable.swiss.suite.algorithm.identity.SerialNumberWorker;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@TestComponent
public class BasicTest {
    @Test
    public void foobar() {
        SerialNumberWorker.SerialNumberBuilder serialNumberBuilder = SerialNumberWorker.builder();
        String s1 = serialNumberBuilder.build();
        String s2 = serialNumberBuilder.build();
    }

    private ArrayList<Person> samplePerson() {
        Person person1 = new Person() {{
            setId(1);
            setNo(11);
            setName("11");
        }};

        Person person2 = new Person() {{
            setId(2);
            setNo(22);
            setName("22");
        }};
        Person person3 = new Person() {{
            setId(3);
            setNo(33);
            setName("33");
        }};
        Person person4 = new Person() {{
            setId(null);
            setNo(44);
            setName("44");
        }};
        ArrayList<Person> list1 = new ArrayList<>();
        list1.add(person3);
        list1.add(person1);
        list1.add(person4);
        list1.add(person2);
        return list1;
    }

    private ArrayList<Dog> sampleDog() {
        Dog dog1 = new Dog() {{
            setOwnerId(1);
            setNo(11);
            setAge(11);
        }};
        Dog dog2 = new Dog() {{
            setOwnerId(2);
            setNo(22);
            setAge(22);
        }};
        Dog dog3 = new Dog() {{
            setOwnerId(3);
            setNo(33);
            setAge(33);
        }};
        ArrayList<Dog> list2 = new ArrayList<>();
        list2.add(dog1);
        list2.add(dog2);
        list2.add(dog3);
        return list2;
    }

    @Test
    public void sort() {
        ArrayList<Person> list1 = samplePerson();
//        list1.add(null);

        list1.sort(Comparator.comparing(Person::getId, Comparator.nullsFirst(Integer::compareTo)).reversed());
        List<Person> collect = list1.stream().sorted(
                        Comparator.comparing(Person::getId, Comparator.nullsFirst(Integer::compareTo)).reversed())
                .collect(Collectors.toList());

        System.out.println();
    }

    @Test
    public void join() {
        ArrayList<Person> list1 = samplePerson();
        ArrayList<Dog> list2 = sampleDog();

        Stream<BestFriend> bestFriends = Join.
                join(list1.stream())
                .withKey(c -> c.getId())
                .on(list2.stream())
                .withKey(Dog::getOwnerId)
                .combine((person, dog) -> new BestFriend(person, dog))
                .asStream();
    }

}
