package com.w2m.tvmedia.client.kafka.producer;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {

	Logger log = LoggerFactory.getLogger(MessageProducer.class);

	private final KafkaTemplate<String, String> kafkaTemplate;

	public MessageProducer(KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void sendMessage(String topicName, String message) {
		CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, message);
		future.whenComplete((result, ex) -> {
			if (ex == null) {
				log.info("Sent messag: {} with offset: {}", message, result.getRecordMetadata().offset());
			} else {
				log.info("Unable to send message: {}. Error: {}", message, ex.getMessage());
			}
		});
	}

}
