package com.aio.portable.swiss.design.actor.behavior;

import com.aio.portable.swiss.design.actor.message.Message;
import com.aio.portable.swiss.design.actor.message.ReturnMessage;

import java.util.concurrent.ExecutorService;

public abstract class ActorBehavior extends AbstractBehavior<Message, ReturnMessage> {
    public ActorBehavior(Message message, ExecutorService service) {
        super(message, service);
    }

    public ActorBehavior(ExecutorService service) {
        super(service);
    }
}
