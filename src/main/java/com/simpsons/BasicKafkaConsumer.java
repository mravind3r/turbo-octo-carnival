package com.simpsons;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 * Created by ravi on 21/06/2017.
 */
public class BasicKafkaConsumer {
    
    private String topic;
    private KafkaConsumer<String, String> kafkaConsumer;
    
    public BasicKafkaConsumer(String topic) {
        this.topic = topic;
        
        Properties config = new Properties();
        config.put("bootstrap.servers", "172.17.0.3:9092");
        config.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        config.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        config.put("group.id","test");
        kafkaConsumer = new KafkaConsumer<String, String>(config);
    }
    
    public String getMessage() {
        StringBuffer sb = new StringBuffer();
        try {
            List<String> topics = Arrays.asList(topic);
            kafkaConsumer.subscribe(topics);
            System.out.println("consuming from:"+topic);
            TimeUnit.MILLISECONDS.sleep(1000);
            ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(100);
            System.out.println("polling done....");
            System.out.println("num records consumed:" + consumerRecords.count());
            for (ConsumerRecord r : consumerRecords) {
                sb.append(r.key()).append(r.value());
            }
            return sb.toString();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            kafkaConsumer.close();
        }
        return sb.toString();
    }
}
