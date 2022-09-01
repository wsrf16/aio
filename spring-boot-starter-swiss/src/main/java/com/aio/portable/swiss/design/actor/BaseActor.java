package com.aio.portable.swiss.design.actor;

public interface BaseActor extends Runnable {
    String getName();

    ActorStatus getStatus();

//    void run();
}
