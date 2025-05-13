package club.zhengxiang.coding.rabbitmq.springamqp;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpringAmqpTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    void testSendMessage2Queue() {
        String queueName = "simple.queue";
        String msg = "hello, spring amqp again!";
        System.out.println("发送消息：" + msg);
        rabbitTemplate.convertAndSend(queueName, msg);
    }
}
