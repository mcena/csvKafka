package com.mcena.producer;

import com.mcena.Object.Person;
import com.mcena.serializer.JsonSerializer;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Properties;

public class Producer {
    private final Logger logger = LoggerFactory.getLogger(Producer.class);

    private final String bootstrapServer;
    private final String topicName;
    private final Properties properties;

    public Producer(String bootstrapServer, String topicName) {
        this.bootstrapServer = bootstrapServer;
        this.topicName = topicName;
        this.properties = new Properties();
    }
    public void initProducer(List<Person> personList) {
        //Create properties for producer
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        //Create kafka producer
        final KafkaProducer<String, Person> kafkaProducer = new KafkaProducer<String,Person>(properties);
        logger.info("Kafka producer created");

        Callback callBack = new ProducerCallback();

        for(Person person : personList) {
            //Create producer record
            ProducerRecord<String,Person> producerRecord = new ProducerRecord<String, Person>(topicName,"person-info",person);

            //send record - async
            logger.info("sending data to kafka server..");
            kafkaProducer.send(producerRecord, callBack);
        }

        //flush and close
        logger.info("Flush and close kafka producer..");
        kafkaProducer.flush();
        kafkaProducer.close();
    }
}
