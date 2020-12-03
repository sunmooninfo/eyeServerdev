package com.eye.core.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eye.core.utils.ESConstant;


@Component
public class MessageSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void updateIndex(String IndexName){

        amqpTemplate.convertAndSend(ESConstant.exchange,ESConstant.update_index_rk,IndexName);
    }

}
