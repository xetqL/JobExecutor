/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boulmier.machinelearning.jobexecutor.consumer;

import com.boulmier.machinelearning.jobexecutor.JobExecutor;
import com.boulmier.machinelearning.request.Request;
import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import java.io.IOException;
import java.net.InetAddress;

/**
 *
 * @author anthob
 */
public class RequestConsumer extends Thread {

    private static final String QUEUE_NAME = "master", DEFAULT_HOST = "localhost";
    private final Connection connection;
    private final Channel channel;
    private final QueueingConsumer consumer;

    public RequestConsumer() throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(DEFAULT_HOST);
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        consumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }

    public RequestConsumer(InetAddress vmscheduler_ip, Integer port) throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(vmscheduler_ip.getHostAddress());
        if (port != null) {
            factory.setPort(port);
        }
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        consumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }

    @Override
    public void run() {
        String message;
        long tag;
        Gson gson = new Gson();
        while (true) {
            try {
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                message = new String(delivery.getBody());
                tag = delivery.getEnvelope().getDeliveryTag();
                gson.fromJson(message, Request.class);
                channel.basicAck(tag, false);
                System.err.println(message);
            } catch (InterruptedException | IOException ex) {
                JobExecutor.logger.error(ex.getMessage());
            }
        }
    }

}
