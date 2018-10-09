import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Date;

/**
 * @author 宁震高
 * @version 0.1
 * @time 2018/4/28
 * @since 0.1
 */
public class ProductExchange {

    public static void main(String[] args) {
        try {
//            recieveMqtt();
            productExchange();

//            try {
//                Thread.sleep(10000l);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void productExchange() throws  Exception {
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
            int count = 20000;
            System.out.println(new Date());
            for(int i = 10000;i <count;i++){
                String EXCHANGE_NAME = "testExchange" + i;
//            channel.exchangeDelete("fanout",false);
                channel.exchangeDeclare(EXCHANGE_NAME, "fanout",true,false,null);
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
