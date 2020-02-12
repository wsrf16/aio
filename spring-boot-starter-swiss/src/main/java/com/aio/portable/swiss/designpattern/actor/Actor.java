package com.aio.portable.swiss.designpattern.actor;

import com.aio.portable.swiss.designpattern.actor.behavior.ActorBehavior;
import com.aio.portable.swiss.designpattern.actor.message.Message;
import com.aio.portable.swiss.designpattern.actor.message.MessageReturn;
import com.aio.portable.swiss.sugar.algorithm.identity.IDS;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;

public class Actor<T, R> implements ActorAction {
    Lock lock = new ReentrantLock();

    private static ExecutorService threadPool = Executors.newSingleThreadScheduledExecutor();

    private String name = "actor-" + IDS.uuid();

    Function<Message<T>, MessageReturn<R>> actorBehaviorFunction;

    private Queue<Message<T>> mailBox = new LinkedList<>();

    private Queue<MessageReturn<R>> mailBoxFeedBack = new LinkedList<>();

    private ActorStatus actorStatus = ActorStatus.INIT;

    private Actor<R, ?> nextActor;

    public boolean stoppedRecovery = false;

    public long restIntervalMillis = 500;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Queue<Message<T>> getMailBox() {
        return mailBox;
    }

    public Queue<MessageReturn<R>> getMailBoxFeedBack() {
        return mailBoxFeedBack;
    }

    public ActorStatus getActorStatus() {
        return actorStatus;
    }

    public void setNextActor(Actor<R, ?> nextActor) {
        this.nextActor = nextActor;
    }

    public Actor<R, ?> getNextActor() {
        return nextActor;
    }

    public boolean isStoppedRecovery() {
        return stoppedRecovery;
    }

    public void setStoppedRecovery(boolean stoppedRecovery) {
        this.stoppedRecovery = stoppedRecovery;
    }

    public long getRestIntervalMillis() {
        return restIntervalMillis;
    }

    public void setRestIntervalMillis(long restIntervalMillis) {
        this.restIntervalMillis = restIntervalMillis;
    }







    public ActorStatus tryStop() {
        actorStatus = ActorStatus.STOPPING;
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return actorStatus;
    }

    private Actor(Function<Message<T>, MessageReturn<R>> actorBehaviorFunction) {
        this.actorBehaviorFunction = actorBehaviorFunction;
    }

    public static <T, R> Actor<T, R> build(Function<T, R> handle) {
        Function<Message<T>, MessageReturn<R>> actorBehaviorFunction = (Message<T> message) -> {
            R r = handle.apply(message.getData());
            MessageReturn<R> messageReturn = message.toReturn(r);
            return messageReturn;
        };
        Actor<T, R> actor = new Actor<>(actorBehaviorFunction);
        return actor;
    }

//    public AbsActor(Function<T, R> function) {
//        Function<Message<T>, MessageReturn<R>> actorBehaviorFunction = (Message<T> message) -> {
//            R r = function.apply(message.getData());
//            MessageReturn<R> messageReturn = message.toReturn(r);
//            return messageReturn;
//        };
//        this.actorBehaviorFunction = actorBehaviorFunction;
//    }

    public Actor<T, R> push(Message<T>... messages) {
        Arrays.stream(messages).forEach(message -> mailBox.add(message));
        return this;
    }

    public Actor<T, R> push(Collection<Message<T>> messages) {
        messages.stream().forEach(message -> mailBox.add(message));
        return this;
    }

    private Actor<T, R> pushToNextActor(Message<R> message) {
        nextActor.getMailBox().add(message);
        return this;
    }

    private Actor<T, R> pushToNextActor() {
        if (!mailBoxFeedBack.isEmpty()) {
            MessageReturn<R> poll = mailBoxFeedBack.poll();
            Message<R> message = new Message<>(poll.getData());
            nextActor.getMailBox().add(message);
        }
        return this;
    }


    public Actor<T, R> setMailBox(Queue<Message<T>> mailBox) {
        this.mailBox = mailBox;
        return this;
    }

    public void run() {
        if (actorStatus != ActorStatus.INIT)
            return;

        boolean beLock = lock.tryLock();
        if (beLock) {
            try {
                loop:
                while (true) {
                    release();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }


    }

    private void release() {
        switch (actorStatus) {
            case INIT:
            case RUNNING:
                if (mailBox.size() > 0) {
                    try {
                        actorStatus = ActorStatus.RUNNING;
                        Message message = mailBox.poll();
                        ActorBehavior actorBehavior = new ActorBehavior(threadPool) {
                            @Override
                            public MessageReturn<R> call() {
                                MessageReturn<R> messageReturn = actorBehaviorFunction.apply(message);
                                return messageReturn;
                            }
                        };

                        MessageReturn<R> messageReturn = actorBehavior.submit();
                        mailBoxFeedBack.add(messageReturn);

                        if (nextActor != null) {
                            pushToNextActor();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        actorStatus = ActorStatus.CRASHING;
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                        actorStatus = ActorStatus.CRASHING;
                    }
                } else {
                    actorStatus = ActorStatus.IDLE;
                }
                break;
            case IDLE: {
                try {
                    Thread.sleep(restIntervalMillis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                actorStatus = ActorStatus.RUNNING;
            }
            break;
            case STOPPING:
            case CRASHING:
                actorStatus = ActorStatus.STOPPED;
                break;
            case STOPPED:
                if (stoppedRecovery)
                    actorStatus = ActorStatus.RUNNING;
            default:
                if (mailBox.size() < 1) {
                    actorStatus = ActorStatus.STOPPED;
                }
                break;
        }
    }

}
