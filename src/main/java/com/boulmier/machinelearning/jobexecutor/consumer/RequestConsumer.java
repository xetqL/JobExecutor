/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boulmier.machinelearning.jobexecutor.consumer;

import com.boulmier.machinelearning.jobexecutor.JobExecutor;
import com.boulmier.machinelearning.jobexecutor.job.mlp.DIMLP;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author anthob
 */
public class RequestConsumer extends Thread{

    private static final String QUEUE_NAME = "master", DEFAULT_HOST="localhost";
    private final Connection connection;
    private final Channel channel;
    private final QueueingConsumer consumer;
    public RequestConsumer() throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(DEFAULT_HOST);
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        consumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }

    public RequestConsumer(InetAddress vmscheduler_ip, Integer port) throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(vmscheduler_ip.getHostAddress());
        if(port != null) 
            factory.setPort(port);
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        consumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME, true, consumer);    }

    private String consumeOne() throws InterruptedException {
        QueueingConsumer.Delivery delivery = consumer.nextDelivery();
        return new String(delivery.getBody());
    }

    @Override
    public void run() {
        String message;
        Gson gson = new GsonBuilder().create();
        while(true){
            try {
                message = consumeOne();
                HashMap req = gson.fromJson(message, HashMap.class);
            } catch (InterruptedException ex) {
                JobExecutor.logger.error(ex.getMessage());
            }
        }
    }
    
}
