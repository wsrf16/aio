package com.aio.portable.swiss.design.actor.behavior;

import com.aio.portable.swiss.design.actor.message.Message;
import com.aio.portable.swiss.design.actor.message.ReturnMessage;

import java.util.concurrent.ExecutorService;

public abstract class ActorBehavior extends AbstractBehavior<Message, ReturnMessage> {
    public ActorBehavior(ExecutorService service, Message message) {
        super(service, message);
    }

    public ActorBehavior(ExecutorService service) {
        super(service);
    }
}
