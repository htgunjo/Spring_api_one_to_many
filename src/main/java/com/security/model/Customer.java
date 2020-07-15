package com.security.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customer_list")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "customer_fullName")
	private String customer_fullName;
	
	@Column(name = "customer_info")
	private String customer_info;

	public Customer() {
	}

	public Customer(long id, String customer_fullName, String customer_info) {
		this.id = id;
		this.customer_fullName = customer_fullName;
		this.customer_info = customer_info;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCustomer_fullName() {
		return customer_fullName;
	}

	public void setCustomer_fullName(String customer_fullName) {
		this.customer_fullName = customer_fullName;
	}

	public String getCustomer_info() {
		return customer_info;
	}

	public void setCustomer_info(String customer_info) {
		this.customer_info = customer_info;
	}

	@Override
	public String toString() {
		return "Customer{" + "id=" + id + ", customer_fullName='" + customer_fullName + '\'' + ", customer_info='"
				+ customer_info + '\'' + '}';
	}
}