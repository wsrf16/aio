package com.aio.portable.swiss.design.actor;

public interface ActorAction extends Runnable {
    String getName();

    ActorStatus getActorStatus();

    void run();
}
