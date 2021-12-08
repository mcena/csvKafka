package com.mcena.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class Consumer {
    private final Logger logger = LoggerFactory.getLogger(Consumer.class);

    private final String bootstrapServer;
    private final String consumerId;

    public Consumer(String bootstrapServer, String consumerId) {
        this.bootstrapServer = bootstrapServer;
        this.consumerId = consumerId;
    }

    public void initConsumer() {
        // properties
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, consumerId);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // create consumer
        final KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(properties);

        // subscribe to topics
        kafkaConsumer.subscribe(Arrays.asList("sample-topic"));
        // poll and consume records (infinite loop to poll)
        while (true) {
            ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(1000));
            for(ConsumerRecord record : consumerRecords) {
                logger.info("Key: " + record.key() + ", Value: " + record.value() + ", Topic: " + record.topic()
                        + ", Partition: " + record.partition() + ", Offset: " + record.offset());
            }

        }
    }

}
