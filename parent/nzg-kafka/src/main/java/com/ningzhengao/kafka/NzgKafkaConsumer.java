package com.ningzhengao.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class NzgKafkaConsumer {
    public static void main(String[] args) {
        String topicName = "my-topic";
        Properties props = new Properties();
        props.put("bootstrap.servers",
                "127.0.0.1:9092");// 该地址是集群的子集，用来探测集群。
        props.put("group.id", "test");// cousumer的分组id
        props.put("enable.auto.commit", "false");// 自动提交offsets
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
//        consumer.subscribe(Arrays.asList(topicName));// 订阅的topic,可以多个
        /////////已存在的group  offset从n开始
        consumer.assign(Arrays.asList(new TopicPartition(topicName, 0)));
//        consumer.seek(new TopicPartition(topicName, 0), 10);

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d, key = %s, value = %s",
                        record.offset(), record.key(), record.value());
                System.out.println();
            }
        }
    }
}
