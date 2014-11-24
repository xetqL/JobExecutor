
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author antho
 */
public class QueuingService {
    private static final String name="master",
                                host="127.0.0.1";
    private final Connection connection;
    private final Channel channel;
    
    public QueuingService() throws IOException{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(name,false,false,false,null);
    }
    
    public void send(HashMap<String,String> request) throws IOException{
        Gson gson = new GsonBuilder().create();
        channel.basicPublish("", name, null, gson.toJson(request, HashMap.class).getBytes());
    }
    
}