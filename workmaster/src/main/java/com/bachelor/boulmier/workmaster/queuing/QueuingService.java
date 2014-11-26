package com.bachelor.boulmier.workmaster.queuing;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author antho
 */
public class QueuingService implements Closeable{

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
            channel.queueDeclare(name, false, false, false, null);
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

    public void send(HashMap<String, String> request) throws IOException {
        Gson gson = new GsonBuilder().create();
        channel.basicPublish("", name, null, gson.toJson(request, HashMap.class).getBytes());
        
    }

    @Override
    public void close() throws IOException {
        channel.close();
        connection.close();
    }

}
