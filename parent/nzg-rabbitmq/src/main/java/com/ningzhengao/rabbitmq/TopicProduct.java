package com.ningzhengao.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import org.fusesource.hawtbuf.Buffer;
import org.fusesource.hawtbuf.UTF8Buffer;
import org.fusesource.mqtt.client.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.concurrent.TimeoutException;

/**
 * @author 宁震高
 * @version 0.1
 * @time 2018/4/20
 * @since 0.1
 */
public class TopicProduct {
    public static void main(String[] args) {
        try {
//            recieveMqtt();
            testSendMqtt();

//            try {
//                Thread.sleep(10000l);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void testSendMqtt() throws  Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("admin");
        factory.setPassword("admin");
         factory.setVirtualHost("/");
        factory.setHost("192.168.0.81");
        factory.setPort(5672);
        Connection conn = null;
        Channel channel = null;
        try {
            conn = factory.newConnection();
            channel = conn.createChannel();

//            byte[] messageBodyBytes = "{'text':'Hello, world!中文'}".getBytes("utf-8");
            byte[] messageBodyBytes=new byte[1];
            int count = 1000;
//            channel.exchangeDeclare("amq.topic","topic",true,false,null);
            System.out.println(new Date());
            for(int i = 0;i <count;i++){
                long l = System.currentTimeMillis();
                channel.basicPublish("amq.topic", "mqtt.test.aaa", null, messageBodyBytes);
                l = System.currentTimeMillis() - l;
                System.out.println(i+" " +l);
                long t = 500 -l;
                if(t<0)t=500;
                Thread.sleep(t);
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(new Date());
        }finally {
            if (channel != null) {
                channel.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
}
