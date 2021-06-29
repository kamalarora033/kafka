package com.kafka.prac.custom.serializer;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class ConsumerSample {
	public static void main(String[] args) {
		ConsumerSample consumer = new ConsumerSample();
		consumer.consume();
	}

	public void consume() {
		Properties props = new Properties();
		InputStream is = null;
		String topic = "testTopicNew";
		Consumer<String, Supplier> consumer = null;
		try {
			is = this.getClass().getResourceAsStream("/consumer.properties");
			props.load(is);
			consumer = new KafkaConsumer<String, Supplier>(props);
			consumer.subscribe(Arrays.asList(topic));
			while (true) {
				ConsumerRecords<String, Supplier> cRecords = consumer.poll(100);
				for (ConsumerRecord<String, Supplier> record : cRecords) {
					System.out.println(
							"ID: " + record.value().getSupplierId() + ", Name: " + record.value().getSupplierName()
									+ ", StartDate: " + record.value().getSupplierStartDate());
				}
				consumer.commitAsync();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			consumer.commitSync();
			consumer.close();
		}
	}

}
