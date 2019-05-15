package com.ningzhengao.kafka;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class NzgKafkaProduct {
    public static void main(String[] args) {
        Properties props = new Properties();
//        props.put("bootstrap.servers",
//                "127.0.0.1:9092");//该地址是集群的子集，用来探测集群。
        props.put("bootstrap.servers",
                "172.16.22.23:9092,172.16.22.25:9092,172.16.22.26:9092");//该地址是集群的子集，用来探测集群。
        props.put("acks", "all");// 记录完整提交，最慢的但是最大可能的持久化
        props.put("retries", 3);// 请求失败重试的次数
        props.put("batch.size", 16384);// batch的大小
        props.put("linger.ms", 1);// 默认情况即使缓冲区有剩余的空间，也会立即发送请求，设置一段时间用来等待从而将缓冲区填的更多，单位为毫秒，producer发送数据会延迟1ms，可以减少发送到kafka服务器的请求数据
        props.put("buffer.memory", 33554432);// 提供给生产者缓冲内存总量
        props.put("key.serializer",
                StringSerializer.class);// 序列化的方式，
        // ByteArraySerializer或者StringSerializer
        props.put("value.serializer",
                StringSerializer.class);

//        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG
//                , MessageHeaderEncoder.class);
//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG
//                , "org.apache.kafka.common.serialization.ByteArraySerializer");
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        producer.send(new ProducerRecord<String, String>("my-test-topic",
                "z中文test"));
//        StringBuilder sb = new StringBuilder();
//        for(int i=0;i<1000;i++){
//            sb.append("a");
//        }
//        for(int a=0;a<20;a++){
//            Thread t=new Thread(()->{
//                long start = System.currentTimeMillis();
//                try {
//                    for (int i = 0; i < 1000; i++) {
//                        // 三个参数分别为topic, key,value，send()是异步的，添加到缓冲区立即返回，更高效。
//                        producer.send(new ProducerRecord<String, String>("my-test-topic",
//                                Integer.toString(i), sb.toString())).get();
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                }
//                System.out.println(System.currentTimeMillis() - start);
//            });
//            t.start();
//        }
//        try {
//            Thread.sleep(10000000l);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        producer.close();
    }
}
