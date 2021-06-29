package com.kafka.prac.custom.serializer;

import java.nio.ByteBuffer;
import java.util.Map;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

public class SupplierSerializer implements Serializer<Supplier> {

	private String encoding = "UTF-8";

	public void configure(Map<String, ?> configs, boolean isKey) {
	}

	public byte[] serialize(String topic, Supplier data) {
		int sizeOfName;
		int sizeOfDate;
		byte[] serializedName;
		byte[] serializedDate;

		try {
			if (data == null) {
				return null;
			}
			serializedName = data.getSupplierName().getBytes(encoding);
			serializedDate = data.getSupplierStartDate().toString().getBytes(encoding);
			sizeOfName = serializedName.length;
			sizeOfDate = serializedDate.length;
			ByteBuffer buffer = ByteBuffer.allocate(4 + 4 + 4 + sizeOfDate + sizeOfName);
			buffer.putInt(data.getSupplierId());
			buffer.putInt(sizeOfName);
			buffer.put(serializedName);
			buffer.putInt(sizeOfDate);
			buffer.put(serializedDate);
			return buffer.array();
			
		} catch (Exception e) {
			throw new SerializationException("Error while serialize supplier object");
		}
	}

	public void close() {
	}

}
