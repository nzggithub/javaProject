package com.ningzhengao.kafka;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class NzgKafkaConsumer {
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(new Date()));
        String topicName = "my-topic";
        Properties props = new Properties();
        props.put("bootstrap.servers",
                "127.0.0.1:9092");// 该地址是集群的子集，用来探测集群。
        props.put("group.id", "test1");// cousumer的分组id
        props.put("enable.auto.commit", "true");// 自动提交offsets
//        props.put("auto.commit.interval.ms", "1000");// 每隔1s，自动提交offsets
        props.put("session.timeout.ms", "30000");// Consumer向集群发送自己的心跳，超时则认为Consumer已经死了，kafka会把它的分区分配给其他进程
        props.put("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");// 反序列化器
        props.put("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        ///////已存在的group  offset从0开始
//        consumer.assign(Arrays.asList(new TopicPartition(topicName, 0)));
//        consumer.seekToBeginning(Arrays.asList(new TopicPartition(topicName, 0)));//不改变当前offset
        /////新group  offset从0开始
//        Map<TopicPartition, OffsetAndMetadata> hashMaps = new HashMap<TopicPartition, OffsetAndMetadata>();
//        hashMaps.put(new TopicPartition(topicName, 0), new OffsetAndMetadata(0));
//        consumer.commitSync(hashMaps);
        consumer.subscribe(Arrays.asList(topicName));// 订阅的topic,可以多个
        /////////已存在的group  offset从n开始
//        consumer.assign(Arrays.asList(new TopicPartition(topicName, 0)));
//        consumer.seek(new TopicPartition(topicName, 0), 10);

        KafkaConsumer<String, String> consumer1 = new KafkaConsumer<>(props);
        consumer1.subscribe(Arrays.asList(topicName));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("consumer1 = %d, key = %s, value = %s",
                        record.offset(), record.key(), record.value());
                System.out.println();
            }
            records = consumer1.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("consumer2 = %d, key = %s, value = %s",
                        record.offset(), record.key(), record.value());
                System.out.println();
            }
        }
    }
}
