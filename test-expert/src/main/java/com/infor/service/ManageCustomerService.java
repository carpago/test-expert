package com.infor.service;

import nl.carpago.testexpert.annotation.CreateUnittest;

import com.infor.domain.Customer;

public class ManageCustomerService {
	
	//private Map <String, Customer> customers;
	
	private CustomerCollection customers;
	
	@CreateUnittest(in={"id"}, out="customer")
	public Customer getCustomer(int customerId) {
		Customer result = new Customer(1, "Raymond");
		
		return result;
	}
	
	@CreateUnittest(in={"id"}, out="customer")
	public Customer getCustomerFromColllection(int id) {
		Customer result = customers.getCustomer(id);
		
		return result;
	}
	
	public CustomerCollection getCustomers()
	{
		return customers;
	}
		
}