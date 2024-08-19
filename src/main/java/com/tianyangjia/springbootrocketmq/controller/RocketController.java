package com.tianyangjia.springbootrocketmq.controller;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RocketController {

  @Autowired
  private RocketMQTemplate rocketMQTemplate;

  @RequestMapping(value = "/rocket", method = RequestMethod.GET)
  public void noTag() {
    // convertAndSend() 发送普通字符串消息
    rocketMQTemplate.convertAndSend("sendMessage_topic", "Hello Word");
  }

  @RequestMapping(value = "/tagA", method = RequestMethod.GET)
  public void tagA() {
    rocketMQTemplate.convertAndSend("sendMessage_topic:tagA", "hello world tagA");
  }

  @RequestMapping(value = "/tagB", method = RequestMethod.GET)
  public void tagB() {
    rocketMQTemplate.convertAndSend("sendMessage_topic:tagB", "hello world tagB");
  }

  @RequestMapping(value = "/syncSend", method = RequestMethod.GET)
  public void syncSend() {
    String json = "发送同步消息";
    SendResult sendResult = rocketMQTemplate.syncSend("sendMessage_topic:1", json);
    System.out.println(sendResult);
  }

  @RequestMapping(value = "/aSyncSend", method = RequestMethod.GET)
  public void aSyncSend() {
    String json = "发送异步消息";
    SendCallback callback = new SendCallback() {
      @Override
      public void onSuccess(SendResult sendResult) {
        System.out.println("发送消息成功");
      }

      @Override
      public void onException(Throwable throwable) {
        System.out.println("发送消息失败");
      }
    };
    rocketMQTemplate.asyncSend("sendMessage_topic", json, callback);
  }

  @RequestMapping(value = "/sendOneWay", method = RequestMethod.GET)
  public void sendOneWay() {
    rocketMQTemplate.sendOneWay("sendMessage_topic", "发送单向消息");
  }

  @RequestMapping(value = "/sendDelay", method = RequestMethod.GET)
  public void sendDelay() {
    SendResult sendResult = rocketMQTemplate.syncSend("sendMessage_topic", MessageBuilder.withPayload("发送延迟消息").build(),1000,5);
    System.out.println(sendResult);
  }
}
