package club.zhengxiang.coding.rabbitmq.declare.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfiguration {

    @Bean
    public TopicExchange topicExchange() {
        // return new TopicExchange("qixqi.topic2");
        return ExchangeBuilder.topicExchange("qixqi.topic2").build();
    }

    @Bean
    public Queue topicQueue3() {
        // return new Queue("topic.queue3");
        return QueueBuilder.durable("topic.queue3").build();
    }

    @Bean
    public Queue topicQueue4() {
        // return new Queue("topic.queue3");
        return QueueBuilder.durable("topic.queue4").build();
    }

    @Bean
    public Binding topicBinding3(TopicExchange topicExchange, Queue topicQueue3) {
        return BindingBuilder.bind(topicQueue3).to(topicExchange).with("china.#");
    }

    /**
     * Configuration 类中，所有的 bean 方法，都会被 spring 动态代理，
     * 调用该方法时，spring 首先检查，spring 容器中是否有对应的 bean，如果有，直接返回 bean，
     * 如果没有，才会执行返回，创建 bean，并放到 spring 容器中。
     */
    @Bean
    public Binding topicBinding4() {
        return BindingBuilder.bind(topicQueue4()).to(topicExchange()).with("#.news");
    }
}
