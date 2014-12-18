/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boulmier.machinelearning.jobexecutor.consumer;

import com.boulmier.machinelearning.jobexecutor.JobExecutor;
import com.boulmier.machinelearning.request.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.reflect.TypeToken;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.util.EnumMap;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    static class EnumMapInstanceCreator<K extends Enum<K>, V> implements
            InstanceCreator<EnumMap<K, V>> {

        private final Class<K> enumClazz;

        public EnumMapInstanceCreator(final Class<K> enumClazz) {
            super();
            this.enumClazz = enumClazz;
        }

        @Override
        public EnumMap<K, V> createInstance(final Type type) {
            return new EnumMap<>(enumClazz);
        }
    }

    @Override
    public void run() {
        String message;
        while (true) {
            try {
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                message = new String(delivery.getBody());
                Request r = Request.fromString(message);
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), true);
                System.out.println(r);
            } catch (InterruptedException ex) {
                JobExecutor.logger.error(ex.getMessage());
            } catch (IOException ex) {
                Logger.getLogger(RequestConsumer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
