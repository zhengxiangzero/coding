package club.zhengxiang.coding.rabbitmq.declare;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PublisherTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void sendMessage2Topic() throws InterruptedException {
        final String EXCHANGE_NAME = "qixqi.topic2";
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, "china.news", "Hello, china news!");
    }
}