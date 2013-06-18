package com.infor.service;

import nl.carpago.testexpert.annotation.Expect;

import com.infor.domain.Customer;

public class CustomerCollection {
	
	
	@Expect(in="id", out="customer")
	public Customer getCustomer(int id) {
		Customer result = new Customer(1, "Raymond");
		
		return result;
	}

}
