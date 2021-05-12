package com.aio.portable.swiss.design.actor.behavior;

import com.aio.portable.swiss.design.actor.message.Message;
import com.aio.portable.swiss.design.actor.message.MessageReturn;

import java.util.concurrent.ExecutorService;

public abstract class ActorBehavior extends AbsBehavior<Message, MessageReturn> {
    public ActorBehavior(ExecutorService service, Message message) {
        super(service, message);
    }

    public ActorBehavior(ExecutorService service) {
        super(service);
    }
}
