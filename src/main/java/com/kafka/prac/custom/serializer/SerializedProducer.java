package com.kafka.prac.custom.serializer;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class SerializedProducer {
	public static void main(String[] args) {
		SerializedProducer producer = new SerializedProducer();
		try {
			producer.sendMessage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessage() throws InterruptedException, ExecutionException{
		String topic = "testTopicNew";
		Properties props = new Properties();
		props.setProperty("bootstrap.servers", "localhost:9092,localhost:9093");
		props.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "com.kafka.prac.custom.serializer.SupplierSerializer");
		
		Producer<String, Supplier> producer = new KafkaProducer<String, Supplier>(props);
		
		producer.send(new ProducerRecord<String, Supplier>(topic, new Supplier(1,"kamal",new Date()))).get();
		producer.send(new ProducerRecord<String, Supplier>(topic, new Supplier(2,"arora",new Date()))).get();
		
		producer.close();
	}

}
