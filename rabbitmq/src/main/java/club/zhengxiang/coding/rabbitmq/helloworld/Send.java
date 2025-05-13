package club.zhengxiang.coding.rabbitmq.helloworld;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.nio.charset.StandardCharsets;

/**
 * publisher
 */
public class Send {

    // queue 名称
    private final static String QUEUE_NAME = "test.queue";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            // declare 幂等，不存在则创建
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Hello World!777";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
