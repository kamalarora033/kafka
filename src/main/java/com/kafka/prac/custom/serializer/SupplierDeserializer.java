package com.kafka.prac.custom.serializer;

import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

public class SupplierDeserializer implements Deserializer<Supplier> {

	public void configure(Map<String, ?> configs, boolean isKey) {
	}

	public Supplier deserialize(String topic, byte[] data) {
		String encoding = "UTF-8";
		if (data == null) {
			System.out.println("Received null data for deserialize");
			return null;
		}
		try {
			ByteBuffer buffer = ByteBuffer.wrap(data);
			int supplierId = buffer.getInt();
			int sizeOfName = buffer.getInt();
			byte[] nameBytes = new byte[sizeOfName];
			String supplierName = new String(nameBytes, encoding);
			int sizeOfDate = buffer.getInt();
//			byte[] dateBytes = new byte[sizeOfDate];
//			buffer.get(dateBytes);
//			String date = new String(dateBytes, encoding);
//			SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
			return new Supplier(supplierId, supplierName);
		} catch (Exception e) {
			throw new SerializationException("Unable to deserialize");
		}
	}

	public void close() {
	}

}
