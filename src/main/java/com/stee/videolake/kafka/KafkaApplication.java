package com.stee.videolake.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

@SpringBootApplication

public class KafkaApplication {
    @Autowired
    private KafkaProperties kafkaProperties;
    
	public static void main(String[] args) {
		SpringApplication.run(KafkaApplication.class, args);
	}

}
