package com.kafka.prac.rebalancer;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.spi.CalendarNameProvider;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.kafka.prac.custom.serializer.Supplier;

public class SerializedProducer {
	public static void main(String[] args) {
		SerializedProducer producer = new SerializedProducer();
		try {
			producer.sendMessage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendMessage() throws InterruptedException, ExecutionException {
		String topic = "testTopicNew";
		Properties props = new Properties();
		props.setProperty("bootstrap.servers", "localhost:9092,localhost:9093");
		props.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "com.kafka.prac.custom.serializer.StringSerializer");
		String msg;
		Producer<String, String> producer = new KafkaProducer<String, String>(props);
		Random r = new Random();
		Calendar cal = Calendar.getInstance();
		cal.set(2016, 1, 1);

		try {
			while (true) {
				for (int i = 0; i < 100; i++) {
					msg = cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.DATE) + ","
							+ r.nextInt(1000);
					producer.send(new ProducerRecord<String, String>(topic, 0, "key", msg));
					msg = cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.DATE) + ","
							+ r.nextInt(1000);
					producer.send(new ProducerRecord<String, String>(topic, 1, "key", msg));
				}
				cal.add(Calendar.DATE, 1);
				System.out.println("data sent for " + cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + "-"
						+ cal.get(Calendar.DATE));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			producer.close();
		}
	}

}
