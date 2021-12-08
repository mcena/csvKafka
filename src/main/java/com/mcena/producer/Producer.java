package com.mcena.producer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class Producer {
    private final Logger logger = LoggerFactory.getLogger(Producer.class);

    private final String bootstrapServer;
    private final String topicName;

    public Producer(String bootstrapServer, String topicName) {
        this.bootstrapServer = bootstrapServer;
        this.topicName = topicName;
    }

    public void initProducer() {
        //Create properties for producer
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //Create kafka producer
        final KafkaProducer<String,String> kafkaProducer = new KafkaProducer<String,String>(properties);
        logger.info("Kafka producer created");

        //Create producer record
        ProducerRecord<String,String> producerRecord = new ProducerRecord<String, String>(topicName,"key19","value19");

        Callback callBack = new ProducerCallback();
        //send record - async
        logger.info("sending data to kafka server..");
        kafkaProducer.send(producerRecord, callBack);

        //flush and close
        logger.info("Flush and close kafka producer..");
        kafkaProducer.flush();
        kafkaProducer.close();
    }
}
