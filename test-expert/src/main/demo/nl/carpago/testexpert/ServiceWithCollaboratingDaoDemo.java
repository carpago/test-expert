package nl.carpago.testexpert;

import nl.carpago.testexpert.annotation.CreateUnittest;

public class ServiceWithCollaboratingDaoDemo {

	private CustomerDAO customerDao;
	
	@CreateUnittest(in="John", out="customer")
	public Customer getCustomer(String firstName) {
		Customer result = customerDao.getCustomer(firstName);
		
		return result;
	}
}
