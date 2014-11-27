package com.bachelor.boulmier.workmaster.queuing;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author antho
 */
public class QueuingService implements Closeable {

    private static final String name = "master",
            host = "127.0.0.1";
    private Connection connection;
    private Channel channel;
    private static QueuingService instance = null;

    private QueuingService(String host, String name) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(host);
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(name, true, false, false, null);
            
            channel.clearConfirmListeners();
            channel.addConfirmListener(new ConfirmListener() {
                //print out which one is acked
                @Override
                public void handleAck(long arg0, boolean arg1) throws IOException {
                    Request r = QueuingExecutionTable.getRequestFromTag(arg0);
                    QueuingExecutionTable.removeEntry(arg0);
                    if (arg1) {
                        Set<Long> s = QueuingExecutionTable.asSetOfTag();
                        for (Long l : s) {
                            if (l < arg0) {
                                QueuingExecutionTable.removeEntry(l);
                            }
                        }
                    }
                    System.out.println("HEIL");
                }

                //resend
                @Override
                public void handleNack(long arg0, boolean arg1) throws IOException {
                    Request r = QueuingExecutionTable.getRequestFromTag(arg0);
                    System.out.println("HEIL");
                    send(r);
                }

            });
            
        } catch (IOException ex) {
            System.err.println("RabbitMQ server is not installed !\nexit...");
            System.exit(0);
        }
    }

    public static QueuingService get() {
        if (instance == null) {
            instance = new QueuingService(QueuingService.host, QueuingService.name);
        }
        return instance;
    }

    public static QueuingService get(String host, String name) {
        if (name == null) {
            name = QueuingService.name;
        }
        if (host == null) {
            host = QueuingService.host;
        }
        if (instance == null) {
            instance = new QueuingService(host, name);
        }
        return instance;
    }

    public void send(Request request) throws IOException {
        Gson gson = new GsonBuilder().create();
        QueuingExecutionTable.newRequest(channel.getNextPublishSeqNo(), (Request) request);
        channel.basicPublish("", name, null, gson.toJson(request, Request.class).getBytes());
    }

    @Override
    public void close() throws IOException {
        channel.close();
        connection.close();
    }

}
