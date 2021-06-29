package com.kafka.prac.producers;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class ProducerSynchronous {
	public static void main(String[] args) {
		ProducerSynchronous producer = new ProducerSynchronous();
		producer.sendMessage();
	}
	
	public void sendMessage(){
		Properties prop = new Properties();
		prop.setProperty("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
		prop.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		prop.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		
		Producer<String, String> kafkaProducer = new KafkaProducer<String, String>(prop);
		
		ProducerRecord<String, String> message = new ProducerRecord<String, String>("testTopic", "hello Kamal");
		
		try {
			RecordMetadata metaData = kafkaProducer.send(message).get();
			System.out.println(metaData.partition()+","+metaData.offset());
		} catch (Exception e) {
			e.printStackTrace();
		}
		kafkaProducer.close();
	}

}
