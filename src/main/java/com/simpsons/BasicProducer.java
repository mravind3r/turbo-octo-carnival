package com.simpsons;

import java.util.Date;
import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class BasicProducer {
    
    private String topic;
    private KafkaProducer<String, String> kafkaProducer;
    
    public BasicProducer(String topic) {
        this.topic = topic;
        
        Properties config = new Properties();
        config.put("bootstrap.servers", "172.17.0.3:9092");
        config.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        config.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        config.put("group.id","test");
        kafkaProducer = new KafkaProducer<String, String>(config);
    }
    
    public void postSynchMessage(String message) {
        try {
            String wrappedMessage = new Date().toString() + ":" + message;
            ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(this.topic, wrappedMessage);
            kafkaProducer.send(producerRecord);
        } finally {
            //   kafkaProducer.close();
        }
    }
    
    public void postAsynchMessage(String message) {
        try {
            String wrappedMessage = new Date().toString() + ":" + message;
            ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(this.topic, wrappedMessage);
            kafkaProducer.send(producerRecord, new Callback() {
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    System.out.println("asynch message sent...:" + recordMetadata.topic());
                }
            });
            
        } finally {
            kafkaProducer.close();
        }
    }
}
