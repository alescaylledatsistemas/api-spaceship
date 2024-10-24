package com.w2m.tvmedia.client.kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

	Logger log = LoggerFactory.getLogger(MessageConsumer.class);

	@KafkaListener(topics = "spaceship-topic", groupId = "spaceship-group")
	public void listen(String message) {
		log.info("Received Message: {}", message);
	}

}
