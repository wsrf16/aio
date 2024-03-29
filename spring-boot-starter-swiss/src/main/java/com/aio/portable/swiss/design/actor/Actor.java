package com.aio.portable.swiss.design.actor;

import com.aio.portable.swiss.design.actor.behavior.ActorBehavior;
import com.aio.portable.swiss.design.actor.message.Message;
import com.aio.portable.swiss.design.actor.message.ReturnMessage;
import com.aio.portable.swiss.suite.algorithm.identity.IDS;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;

public class Actor<T, R> implements BaseActor {
    Lock lock = new ReentrantLock();

    private static ExecutorService threadPool = Executors.newSingleThreadScheduledExecutor();

    private String name = "actor-" + IDS.uuid();

    Function<Message<T>, ReturnMessage<R>> actorBehaviorFunction;

    private Queue<Message<T>> mailBox = new LinkedList<>();

    private Queue<ReturnMessage<R>> feedBackMailBox = new LinkedList<>();

    private ActorStatus status = ActorStatus.INIT;

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

    public Queue<ReturnMessage<R>> getFeedBackMailBox() {
        return feedBackMailBox;
    }

    public ActorStatus getStatus() {
        return status;
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
        status = ActorStatus.STOPPING;
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return status;
    }

    private Actor(Function<Message<T>, ReturnMessage<R>> actorBehaviorFunction) {
        this.actorBehaviorFunction = actorBehaviorFunction;
    }

    public static final <T, R> Actor<T, R> build(Function<T, R> handler) {
        Function<Message<T>, ReturnMessage<R>> actorBehaviorFunction = message -> {
            T data = message.getData();
            R rData = handler.apply(data);
            ReturnMessage<R> rMessage = message.buildReturnMessage(rData);
            return rMessage;
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
        return push(new ArrayList<>(messages));
    }

    private Actor<T, R> pushToNextActor(Message<R> message) {
        nextActor.getMailBox().add(message);
        return this;
    }

    private Actor<T, R> pushToNextActor() {
        if (!feedBackMailBox.isEmpty()) {
            ReturnMessage<R> poll = feedBackMailBox.poll();
            Message<R> message = new Message<>(poll.getData());
            nextActor.getMailBox().add(message);
        }
        return this;
    }


//    public Actor<T, R> setMailBox(Queue<Message<T>> mailBox) {
//        this.mailBox = mailBox;
//        return this;
//    }

    public void run() {
        if (status != ActorStatus.INIT)
            return;

        boolean beLocked = lock.tryLock();
        if (beLocked) {
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
        switch (status) {
            case INIT:
            case RUNNING:
                if (mailBox.size() > 0) {
                    try {
                        status = ActorStatus.RUNNING;
                        Message message = mailBox.poll();
                        ActorBehavior actorBehavior = new ActorBehavior(threadPool) {
                            @Override
                            public ReturnMessage<R> call() {
                                ReturnMessage<R> returnMessage = actorBehaviorFunction.apply(message);
                                return returnMessage;
                            }
                        };

                        ReturnMessage<R> returnMessage = actorBehavior.submit();
                        feedBackMailBox.add(returnMessage);

                        if (nextActor != null) {
                            pushToNextActor();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        status = ActorStatus.CRASHING;
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                        status = ActorStatus.CRASHING;
                    }
                } else {
                    status = ActorStatus.IDLE;
                }
                break;
            case IDLE: {
                try {
                    Thread.sleep(restIntervalMillis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                status = ActorStatus.RUNNING;
            }
            break;
            case STOPPING:
            case CRASHING:
                status = ActorStatus.STOPPED;
                break;
            case STOPPED:
                if (stoppedRecovery)
                    status = ActorStatus.RUNNING;
            default:
                if (mailBox.size() < 1) {
                    status = ActorStatus.STOPPED;
                }
                break;
        }
    }

}
