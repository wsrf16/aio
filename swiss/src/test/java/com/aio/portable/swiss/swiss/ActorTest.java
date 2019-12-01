package com.aio.portable.swiss.swiss;

import com.aio.portable.swiss.designpattern.actor.Actor;
import com.aio.portable.swiss.designpattern.actor.ActorManager;
import com.aio.portable.swiss.designpattern.actor.message.Message;
import com.aio.portable.swiss.designpattern.actor.message.MessageReturn;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@TestComponent
public class ActorTest {
    private static boolean isPrimeNormal(int num) {
        for (int i = 2; i < num; i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    private static List<Integer> pickPrimeNormal(List<Integer> numList) {
        List<Integer> collect = numList.stream().filter(c -> isPrimeNormal(c)).collect(Collectors.toList());
        return collect;
    }

    private static Integer sum(List<Integer> numList) {
        return numList.stream().mapToInt(c -> c).sum();
    }

    private static List<Actor<List<Integer>, List<Integer>>> buildActor2(List<Message<List<Integer>>> collect2, Actor<List<Integer>, Void> actorConcat) {
        return collect2.stream()
                .map(c -> {
                    Actor<List<Integer>, List<Integer>> actor = Actor
//                            .build((List<Integer> integerList) -> integerList.stream().mapToInt(_c -> _c).sum())
                            .build((List<Integer> integerList) -> pickPrimeNormal(integerList))
                            .push(c);
                    actor.setNextActor(actorConcat);
                    return actor;
                }).collect(Collectors.toList());
    }

    private static Actor<List<Integer>, Void> buildActor1() {
        return Actor.build((List<Integer> list) -> {
            list.stream().forEach(c -> System.out.println(c));
            return null;
        });
    }

    private static List<Message<List<Integer>>> groupbyPrime(Map<Integer, List<Integer>> collect1) {
        return collect1.values().stream().map(c -> new Message<>(c)).collect(Collectors.toList());
    }

    private static Map<Integer, List<Integer>> generatePrime() {
        return Stream.iterate(1, item -> item + 1).limit(100)
                .collect(Collectors.groupingBy(c -> c % 10, Collectors.mapping(c -> c, Collectors.toList())));
    }

    @Test
    public static void todo1() {
        // 产生1到100的正整数，并分组
        Map<Integer, List<Integer>> collect1 = generatePrime();
        // 构建Message
        List<Message<List<Integer>>> collect2 = groupbyPrime(collect1);
        // 构建“输出Actor”
        Actor<List<Integer>, Void> actorConcat = buildActor1();
        // 构建“质数判断Actor”
        List<Actor<List<Integer>, List<Integer>>> actorList = buildActor2(collect2, actorConcat);
        // 通过Actor管理器，启动Actor
        ActorManager absActorManager = new ActorManager()
                .addThenStart(actorConcat)
                .addThenStart(actorList);
        boolean loop = true;
        while (loop) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            List _prime = pickPrimeNormal(new ArrayList() {{
                add(new Random().nextInt(100));
            }});

            actorConcat.push(new Message<>(_prime));
        }
    }


    @Test
    public static void todo2() {
        Actor<Integer, Integer> actor1 = Actor
                .build((Integer integer) -> integer * 2)
                .push(new Message<>(121),
                        new Message<>(122),
                        new Message<>(123),
                        new Message<>(124),
                        new Message<>(131),
                        new Message<>(132),
                        new Message<>(133),
                        new Message<>(134));
        actor1.setName("actor1");

        Actor<Integer, Void> actor2 = Actor.build((Integer integer) -> {
            System.out.println(integer);
            return null;
        });
        actor2.setName("actor2");

        ActorManager absActorManager = new ActorManager();
        absActorManager.add(actor1);
        absActorManager.add(actor2);
        absActorManager.start();
        Queue<MessageReturn<Integer>> mailBoxFeedBack = actor1.getMailBoxFeedBack();
        actor1.setNextActor(actor2);
    }
}
