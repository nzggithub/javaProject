package com.ningzhengao.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 宁震高
 * @version 0.1
 * @time 2018/5/2
 * @since 0.1
 */
public class RabbitmqClient {
    static AtomicInteger atomicInteger = new AtomicInteger(0);
    public static void main(String[] args) {
//        consum();
        createQueue();
    }
    static void consum(){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setVirtualHost("/");
        factory.setHost("192.168.0.82");
        factory.setPort(5672);
        Connection conn = null;
        Channel channel = null;

        try {
            for(int i =0 ;i<10000;i++){
                String QUEUE_NAME = "queue"+i;
                conn = factory.newConnection();
                channel = conn.createChannel();
                //声明要关注的队列
                channel.queueDeclare(QUEUE_NAME, true, false, false, null);
                //DefaultConsumer类实现了Consumer接口，通过传入一个频道，
                // 告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery
                Consumer consumer = new DefaultConsumer(channel) {
                    @Override
                    public void handleDelivery(String consumerTag, Envelope envelope,
                                               AMQP.BasicProperties properties, byte[] body)
                            throws IOException {
                        System.out.println(atomicInteger.getAndAdd(1)+" "+body.length);
                    }
                };
                //自动回复队列应答 -- RabbitMQ中的消息确认机制
                channel.basicConsume(QUEUE_NAME, true, consumer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
    static void createQueue(){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setVirtualHost("/");
        factory.setHost("192.168.0.82");
        factory.setPort(5672);
        Connection conn = null;
        Channel channel = null;
        try {
            conn = factory.newConnection();
            channel = conn.createChannel();

            String EXCHANGE_NAME = "amq.fanout";
//            channel.exchangeDelete("fanout",false);
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout",true,false,null);
            Map<String, Object> map = new HashMap<>();
            for(int i =0 ;i<10000;i++){
                String QUEUE_NAME="queue"+i;
                channel.queueDeclare(QUEUE_NAME, true, false, false,map );
                // 绑定队列到交换机
                channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(new Date());
        }finally {
            try {
                if (channel != null) {
                    channel.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
    }
}
