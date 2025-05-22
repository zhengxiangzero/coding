package club.zhengxiang.coding.rabbitmq.fanout;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PublisherTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void sendMessage2Fanout() throws InterruptedException{
        final String EXCHANGE_NAME = "qixqi.fanout";
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, null, "Hello, everyone!");
        Thread.sleep(100000);
    }
}
