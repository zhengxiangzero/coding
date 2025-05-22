package club.zhengxiang.coding.rabbitmq.direct;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PublisherTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void sendMessage2Direct() throws InterruptedException{
        final String EXCHANGE_NAME = "qixqi.direct";
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, "blue", "Hello, blue!");
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, "yellow", "Hello, yellow!");
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, "red", "Hello, red!");
    }
}
