## RabbitMQ
> RabbitMQ 版本：4.1.0 
> 
> 系统版本：MacOS 13.6.6 
### 1. [官网 HelloWorld 示例](./src/main/java/club/zhengxiang/coding/rabbitmq/helloworld/)
* [RabbitMQ tutorial - "Hello World"](https://www.rabbitmq.com/tutorials/tutorial-one-java)
### 2. Spring AMQP 示例：[Publisher](./springamqp-publisher)、[Consumer](./springamqp-consumer)
* 发送消息：RabbitTemplate
  * RabbitTemplate 注入过程
    1. **Spring Boot 自动配置**：Spring Boot 的自动配置机制会根据类路径中的库和你应用中的配置自动创建和配置 beans；引入spring-boot-starter-amqp 后，Spring Boot 将会启用对 RabbitMQ 的支持并根据配置创建 RabbitTemplate bean
    2. **条件性配置**：Spring Boot 使用 @ConditionalOnMissingBean 注解来确保只有在没有定义相同类型的 bean 时才会创建默认的 RabbitTemplate bean；可以在配置类中定义一个 RabbitTemplate bean，这样 Spring Boot 就不会创建默认的 RabbitTemplate
    3. **依赖注入**：应用启动时，Spring 的依赖注入机制会扫描所有的 @Autowired 注解，并尝试为它们找到合适的 bean；如果 ApplicationContext 中存在一个类型为 RabbitTemplate 的 bean，Spring 会自动将其注入
* 接收消息：RabbitListener 非阻塞式监听
### 3. Work 模式：[Publisher](./work-publisher)、[Consumer](./work-consumer)
* 多个 consumer 绑定同一个队列，加快消息处理
* 同一条消息只会被一个 consumer 处理
* **consumer** 设置 prefetch 参数控制消费者预取的消息数量，处理完一条再处理下一条，实现能者多劳
### 4. Fanout 广播交换机：[Publisher](./fanout-publisher)、[Consumer](./fanout-consumer)
* fanout 交换机：将消息广播到所有绑定的队列
* consumer: 一个队列对应多个消费者，其实是一个服务的多个实例，一条消息只被一个消费者处理就行
* queue: 多个队列对应多个微服务，每个微服务都能收到消息