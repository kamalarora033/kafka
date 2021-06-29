package com.kafka.prac.producers;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class ProducerCustomPartition {
	public static void main(String[] args) {
		ProducerCustomPartition partition = new ProducerCustomPartition();
		partition.sendMessage();
	}

	public void sendMessage() {
		String topic = "testTopicNew";
		Properties props = new Properties();
		props.setProperty("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
		props.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.setProperty("partitioner.class", "com.kafka.prac.producers.partitioner.CustomPartitioner");
		props.setProperty("sensor.custom.partition", "TSS");

		Producer<String, String> producer = new KafkaProducer<String, String>(props);

		for (int i = 0; i < 10; i++) {
			producer.send(new ProducerRecord<String, String>(topic, "SSP" + i, "500" + i));
		}
		for (int j = 0; j < 10; j++) {
			producer.send(new ProducerRecord<String, String>(topic, "TSS", "500" + j));
		}

		producer.close();
	}

}
