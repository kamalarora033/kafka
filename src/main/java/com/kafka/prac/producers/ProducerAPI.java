package com.kafka.prac.producers;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class ProducerAPI {
	
	public static void main(String[] args) {
		ProducerAPI producer = new ProducerAPI();
		producer.sendMessage();
	}
	
	public void sendMessage(){
		
		Properties props = new Properties();
		props.setProperty("bootstrap.servers", "localhost:9092,localhost:9093");
		props.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		
		Producer<String, String> producer = new KafkaProducer<String, String>(props);
		
		ProducerRecord<String, String> message = new ProducerRecord<String, String>("testTopic", "key-1","hey dude");
		producer.send(message);
		producer.close();
		System.out.println("Producer completed");
	}

}
