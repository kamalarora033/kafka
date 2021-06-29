package com.kafka.prac.producers.partitioner;

import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.record.InvalidRecordException;
import org.apache.kafka.common.utils.Utils;

public class CustomPartitioner implements Partitioner {

	private String specificSensor;

	public void configure(Map<String, ?> config) {
		specificSensor = config.get("sensor.custom.partition").toString();
	}

	public void close() {
	}

	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
		List<PartitionInfo> partitionsForTopic = cluster.partitionsForTopic(topic);
		int totalPartition = partitionsForTopic.size();
		int reservedPartition = (int) Math.abs(totalPartition * 0.3);
		int partition = 0;
		if (keyBytes == null || !(key instanceof String)) {
			throw new InvalidRecordException("All message should have sensor name as key");
		} else if (key.toString().equals(specificSensor)) {
			partition = Utils.toPositive(Utils.murmur2(valueBytes)) % reservedPartition;
		} else {
			partition = Utils.toPositive(Utils.murmur2(keyBytes)) % (totalPartition - reservedPartition)
					+ reservedPartition;
		}

		System.out.println("key " + key.toString() + ", partition " + partition);
		return partition;
	}

}
