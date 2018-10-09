import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author 宁震高
 * @version 0.1
 * @time 2018/4/28
 * @since 0.1
 */
public class BindFanout {
    public static void main(String[] args) {
        bindFanout();
    }
    public static void bindFanout() {
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

            String EXCHANGE_NAME = "amq.fanout";
//            channel.exchangeDelete("fanout",false);
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout",true,false,null);
            Map<String, Object> map = new HashMap<>();
            map.put("x-expires",86400000L);
            for(int i =0 ;i<10000;i++){
                String QUEUE_NAME="mqtt-subscription-test"+i+"qos1";

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
