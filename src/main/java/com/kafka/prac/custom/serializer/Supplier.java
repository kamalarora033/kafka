package com.kafka.prac.custom.serializer;

import java.util.Date;

public class Supplier {
	private int supplierId;
	private String supplierName;
	private Date supplierStartDate;

	public Supplier(int id, String name, Date startDate) {
		this.supplierId = id;
		this.supplierName = name;
		this.supplierStartDate = startDate;
	}

	public Supplier(int id, String name) {
		this.supplierId = id;
		this.supplierName = name;
	}

	public int getSupplierId() {
		return supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public Date getSupplierStartDate() {
		return supplierStartDate;
	}

}
