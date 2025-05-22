package club.zhengxiang.coding.rabbitmq.topic.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MqListener {

    @RabbitListener(queues = "topic.queue1")
    public void listenWorkQueue1(String msg) throws InterruptedException {
        System.out.println("rabbitmq topic consumer1 收到消息：" + msg);
    }

    @RabbitListener(queues = "topic.queue2")
    public void listenWorkQueue2(String msg) throws InterruptedException{
        System.out.println("rabbitmq topic consumer2 收到消息：" + msg);
    }
}
