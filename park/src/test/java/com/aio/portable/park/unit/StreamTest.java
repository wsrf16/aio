package com.aio.portable.park.unit;

import com.aio.portable.park.bean.friend.BestFriend;
import com.aio.portable.park.bean.friend.Dog;
import com.aio.portable.park.bean.friend.Person;
import com.aio.portable.swiss.sugar.streamjoin.Join;
import com.aio.portable.swiss.sugar.type.CollectionSugar;
import com.aio.portable.swiss.sugar.type.StreamSugar;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@TestComponent
public class StreamTest {
    @Test
    public void foobar() {
        List<Integer> list = StreamSugar.increase(1, n -> n + 1, 100).collect(Collectors.toList());
        CollectionSugar.split(list, 8);
    }

    @Test
    public void join() {
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
        ArrayList<Person> list1 = new ArrayList<>();
        list1.add(person1);
        list1.add(person2);
        list1.add(person3);


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

        Stream<BestFriend> bestFriends = Join.
                join(list1.stream())
                .withKey(Person::getId)
                .on(list2.stream())
                .withKey(Dog::getOwnerId)
                .combine((person, dog) -> new BestFriend(person, dog))
                .asStream();
        List<BestFriend> collect = bestFriends.collect(Collectors.toList());

        System.out.println();
    }
}
