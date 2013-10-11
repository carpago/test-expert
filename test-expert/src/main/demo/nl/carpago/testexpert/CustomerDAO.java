package nl.carpago.testexpert;

import nl.carpago.testexpert.annotation.Expect;

public interface CustomerDAO {
	
	@Expect(in="John", out="customer")
	Customer getCustomer(String firstName);
	
	Customer getCustomer(int age);

}
