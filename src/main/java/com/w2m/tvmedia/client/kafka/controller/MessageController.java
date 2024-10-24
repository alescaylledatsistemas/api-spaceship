package com.w2m.tvmedia.client.kafka.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.w2m.tvmedia.client.kafka.producer.MessageProducer;

@RestController
@RequestMapping("/api/v1/kafka-messages")
public class MessageController {

	private final MessageProducer messageProducer;

	public MessageController(MessageProducer messageProducer) {
		this.messageProducer = messageProducer;
	}

	@PostMapping
	public String sendMessage(@RequestBody String message) {
		messageProducer.sendMessage("spaceship-topic", message);
		return "Message sent: " + message;
	}

}
