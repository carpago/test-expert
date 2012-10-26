package nl.carpago.testexpert;

import nl.belastingdienst.Melding;

import org.springframework.beans.factory.annotation.Autowired;


public class MijnTestBean {

	@Autowired(required=true)
	public Melding melding;
	

}
