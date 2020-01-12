package com.aio.portable.swiss.designpattern.actor;

public interface ActorAction extends Runnable {
    String getName();

    ActorStatus getActorStatus();

    void run();
}
