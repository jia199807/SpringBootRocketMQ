package com.tianyangjia.springbootrocketmq.consumer;

import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * topic 是主题
 * consumerGroup 是消费者组，一条消息只能被同一个消费者组里的一个消费者消费。
 * selectorExpression 是用于消息过滤的，以 TAG 方式为例：
 * 默认为 "*"，表示不过滤，消费此 topic 下所有消息
 * 配置为 "tagA"，表示只消费此 topic 下 TAG = tagA 的消息
 * 配置为 "tagA || tagB"，表示消费此 topic 下 TAG = tagA 或  TAG = tagB 的消息，以此类推
 * 消费模式：默认 CLUSTERING （ CLUSTERING：负载均衡 ）（ BROADCASTING：广播机制 ）
 */
@RocketMQMessageListener(topic = "sendMessage_topic",
        consumerGroup = "consumer-group-test1",
//        selectorExpression = "tagA || tagB",
        messageModel = MessageModel.CLUSTERING)
@Component
public class DemoConsumer1 implements RocketMQListener<String> {

    @Override
    public void onMessage(String s) {
        System.out.println("receive message1:" + s);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("处理完成");
    }
}
