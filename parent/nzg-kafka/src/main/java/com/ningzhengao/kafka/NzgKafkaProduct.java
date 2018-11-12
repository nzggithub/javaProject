package com.ningzhengao.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class NzgKafkaProduct {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers",
                "127.0.0.1:9092");//该地址是集群的子集，用来探测集群。
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
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 10000; i++) {
            // 三个参数分别为topic, key,value，send()是异步的，添加到缓冲区立即返回，更高效。
            producer.send(new ProducerRecord<String, String>("my-topic",
                    Integer.toString(i), Integer.toString(i)));
        }
        producer.close();
    }
}
