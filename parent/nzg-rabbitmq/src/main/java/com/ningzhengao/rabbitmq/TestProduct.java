package com.ningzhengao.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeoutException;

/**
 * @author 宁震高
 * @version 0.1
 * @time 2018/5/7
 * @since 0.1
 */
public class TestProduct {
    public static void main(String[] args) {
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
            byte[] messageBodyBytes = new byte[1024];
            channel.queueDeclare("test", true, false, false, null);
            channel.basicPublish("","test",null,messageBodyBytes);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(new Date());
        } finally {
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
