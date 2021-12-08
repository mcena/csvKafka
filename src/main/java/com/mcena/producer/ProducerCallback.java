package com.mcena.producer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducerCallback implements Callback {
    private final Logger logger = LoggerFactory.getLogger(ProducerCallback.class);
    @Override
    public void onCompletion(RecordMetadata metadata, Exception exception) {
        if(exception == null) {
            logger.info("Received record metadata. \n" + "Topic: " + metadata.topic() + ", Partition: "
                    + metadata.partition() + ", Offset: " + metadata.offset() + " @ Timestamp: " + metadata.timestamp());
        } else {
            logger.error("Error occurred when sending message: " + exception);
        }
    }
}
