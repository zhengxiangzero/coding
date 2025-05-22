package club.zhengxiang.coding.rabbitmq.declare.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MqListener {

    @RabbitListener(queues = "topic.queue3")
    public void listenWorkQueue3(String msg) throws InterruptedException {
        System.out.println("rabbitmq topic consumer3 收到消息：" + msg);
    }

    @RabbitListener(queues = "topic.queue4")
    public void listenWorkQueue4(String msg) throws InterruptedException{
        System.out.println("rabbitmq topic consumer4 收到消息：" + msg);
    }
}
