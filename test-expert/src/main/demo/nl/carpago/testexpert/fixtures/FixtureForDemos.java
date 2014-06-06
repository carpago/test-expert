package nl.carpago.testexpert.fixtures;
import nl.carpago.testexpert.Customer;

import org.springframework.context.annotation.Bean;


public class FixtureForDemos {

	@Bean
	public String simpleDemo() {
		
		return "simpleDemo";
	}
	
	@Bean
	public Customer customer() {
		Customer john = new Customer();
		john.setSalary(10000f);
		john.setAge(45);
		
		return john;
	}

	@Bean
	public String John() {
		
		return "John";
	}
	
}
