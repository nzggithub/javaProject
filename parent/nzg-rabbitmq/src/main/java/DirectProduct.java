import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeoutException;

/**
 * @author 宁震高
 * @version 0.1
 * @time 2018/4/28
 * @since 0.1
 */
public class DirectProduct {
    public static void main(String[] args) {
        try {
            testSendMqtt();
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
            byte[] messageBodyBytes=new byte[1024];
            int count = 1000;
//            channel.exchangeDeclare("amq.topic","topic",true,false,null);
            System.out.println(new Date());
            String EXCHANGE_NAME = "amq.direct";
            for(int i = 0;i <count;i++){
                long l = System.currentTimeMillis();
                for(int m= 0;m<10000;m++){
                    String QUEUE_NAME="mqtt-subscription-test"+m+"qos1";
                    channel.basicPublish(EXCHANGE_NAME, QUEUE_NAME, null, messageBodyBytes);
                }
                l = System.currentTimeMillis() - l;
                System.out.println(i+" " +l);
                long t = 1000 -l;
                if(t<0){
                    t=500;
                    Thread.sleep(t);
                }
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
    static void do1(final Channel channel){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//            byte[] messageBodyBytes = "{'text':'Hello, world!中文'}".getBytes("utf-8");
                    byte[] messageBodyBytes=new byte[1024];
                    int count = 1000;
//            channel.exchangeDeclare("amq.topic","topic",true,false,null);
                    System.out.println(new Date());
                    for(int i = 0;i <count;i++){
                        long l = System.currentTimeMillis();
                        for(int m= 0;m<10000;m++){
                            String QUEUE_NAME="mqtt-subscription-test"+m+"qos1";
                            channel.basicPublish("amq.direct", QUEUE_NAME, null, messageBodyBytes);
                        }
                        l = System.currentTimeMillis() - l;
                        System.out.println(Thread.currentThread().getId()+i+" " +l);
                        long t = 1000 -l;
                        if(t<0)t=500;
                        Thread.sleep(10);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    System.out.println(new Date());
                }finally {
                    try {
                        if (channel != null) {
                            channel.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
    }
}
