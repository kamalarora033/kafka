package com.kafka.prac.rebalancer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import com.kafka.prac.custom.serializer.Supplier;

public class Rebalancer implements ConsumerRebalanceListener {
	KafkaConsumer<String, Supplier> consumer;
	private Map<TopicPartition, OffsetAndMetadata> currentOffset = new HashMap<TopicPartition, OffsetAndMetadata>();

	public void putOffsetData(String topicName, int partition, long offset) {
		currentOffset.put(new TopicPartition(topicName, partition), new OffsetAndMetadata(offset, "commit"));
	}

	public Map<TopicPartition, OffsetAndMetadata> getOffsetData() {
		return currentOffset;
	}

	public Rebalancer(KafkaConsumer<String, Supplier> consumer) {
		this.consumer = consumer;
	}

	public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
		System.out.println("partitions which are revoked");
		for (TopicPartition partition : partitions) {
			System.out.println("partition: " + partition.partition());
		}

		System.out.println("partitions committed");
		for (TopicPartition partition : currentOffset.keySet()) {
			System.out.println("partition: " + partition.partition());
		}
		consumer.commitSync(currentOffset);
		currentOffset.clear();
	}

	public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
		System.out.println("In onPartitionAssigned");
		for (TopicPartition partition : partitions) {
			System.out.println("topic: " + partition.topic() + ", partition: " + partition.partition());
		}
	}

}
