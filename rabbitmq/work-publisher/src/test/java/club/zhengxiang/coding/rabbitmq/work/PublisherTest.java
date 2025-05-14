package club.zhengxiang.coding.rabbitmq.work;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PublisherTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void sendMessage2Queue() throws InterruptedException{
        final String QUEUE_NAME = "work.queue";
        for (int i = 1; i <= 100; i++) {
            rabbitTemplate.convertAndSend(QUEUE_NAME, "Hello, RabbitMQ work：message_" + i);
            Thread.sleep(20);
        }
    }
}
