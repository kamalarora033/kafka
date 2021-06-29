package com.kafka.prac.producers;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class ProducerAsynchronous {
	public static void main(String[] args) {
		ProducerAsynchronous producer = new ProducerAsynchronous();
		producer.sendMessage();
	}

	public void sendMessage(){
		Properties prop = new Properties();
		prop.setProperty("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
		prop.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		prop.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		
		Producer<String, String> kafkaProducer = new KafkaProducer<String, String>(prop);
		
		ProducerRecord<String, String> message = new ProducerRecord<String, String>("testTopic", "hello Kamal");
		
		kafkaProducer.send(message, new MyCallback());
		kafkaProducer.close();
	}
	
}

class MyCallback implements Callback{

	public void onCompletion(RecordMetadata metaData, Exception e) {
		if(e != null){
			e.printStackTrace();
		} else{
			System.out.println(metaData.partition()+","+metaData.offset());
		}
	}
	
}
