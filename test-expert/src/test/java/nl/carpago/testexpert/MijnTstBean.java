package nl.carpago.testexpert;

import nl.foo.Announcement;

import org.springframework.beans.factory.annotation.Autowired;


public class MijnTstBean {

	@Autowired(required=true)
	public Announcement melding;
	

}
