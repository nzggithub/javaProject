package com.ningzhengao.rabbitmq;

import org.fusesource.hawtbuf.Buffer;
import org.fusesource.hawtbuf.UTF8Buffer;
import org.fusesource.mqtt.client.*;

import java.net.URISyntaxException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 宁震高
 * @version 0.1
 * @time 2018/4/20
 * @since 0.1
 */
public class MqttClient {
    static int clientCount = 0;
    static AtomicInteger atomicInteger = new AtomicInteger(0);
    static int count;
    static boolean clean = false;
    static QoS q = QoS.AT_LEAST_ONCE;

    public static void main(String[] args) {
        try {
//            recieveMqtt();

            int tCount = 10000;

            for (int i = 0; i < tCount; i++) {
                recieveMqtt();
            }
            System.out.println(tCount);
//            recieveMqtt();
//            testSendMqtt();

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while(true){
                            int t = atomicInteger.get();
                            if(t>0){
                                System.out.println(t);
                            }
                            Thread.sleep(1000);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
//            t.start();
            try {
                Thread.sleep(1000000000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void recieveMqtt() throws URISyntaxException {
        MQTT mqtt = new MQTT();
        mqtt.setHost("tcp://192.168.0.81:1883");
//        mqtt.setHost("192.168.0.81",1883);
        mqtt.setUserName("admin");
        mqtt.setPassword("admin");
        mqtt.setClientId("test" + clientCount++);
        mqtt.setCleanSession(clean);
//        DispatcherConfig config = new DispatcherConfig();
//        HawtDispatcher dispatcher = new HawtDispatcher(config);
//        GlobalDispatchQueue queue= new GlobalDispatchQueue(dispatcher, DispatchPriority.DEFAULT,5);
//        mqtt.setDispatchQueue(queue.createQueue("testMqtt"));
        final CallbackConnection connection = mqtt.callbackConnection();
        connection.listener(new Listener() {

            public void onDisconnected() {
                log("listen disConnect");
            }

            public void onConnected() {
                log("listent connect");
            }

            public void onPublish(UTF8Buffer topic, Buffer payload, Runnable ack) {
                log("listen publish");
//                log(UTF8Buffer.decode(topic));
//                log(UTF8Buffer.decode(Buffer.utf8(payload)));
//                atomicInteger.getAndAdd(1);
                System.out.println(atomicInteger.getAndAdd(1) + "recieve" + payload.length);
                ack.run();
            }

            public void onFailure(Throwable value) {
                value.printStackTrace();
                ;
                log("listen fail" + value.toString());
            }
        });
        connection.connect(new Callback<Void>() {
            public void onFailure(Throwable value) {
                value.printStackTrace();
                log("connect fail");
            }

            // Once we connect..
            public void onSuccess(Void v) {
                // Subscribe to a topic
                Topic[] topics = {new Topic("mqtt/test/#", q)};
                connection.subscribe(topics, new Callback<byte[]>() {
                    public void onSuccess(byte[] qoses) {
                        log("subscribe success");
                        log(new String(qoses).toString());
                    }

                    public void onFailure(Throwable value) {
                        log("subscribe fail");
                    }
                });

                // Send a message to a topic
                // connection.publish("foo", "Hello".getBytes(),
                // QoS.AT_LEAST_ONCE, false, new Callback<Void>() {
                // public void onSuccess(Void v) {
                // log("publish success");
                // }
                //
                // public void onFailure(Throwable value) {
                // log("publish fail");
                // }
                // });
            }
        });
//        try {
//            Thread.sleep(100000l);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        // To disconnect..
//        connection.disconnect(new Callback<Void>() {
//            public void onSuccess(Void v) {
//                log("disconnect success");
//            }
//
//            public void onFailure(Throwable value) {
//                value.printStackTrace();;
//                log("fail");
//            }
//        });
    }

    public static void log(String str) {

    }
}
