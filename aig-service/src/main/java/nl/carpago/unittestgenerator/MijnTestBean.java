package nl.carpago.unittestgenerator;

import org.springframework.beans.factory.annotation.Autowired;

import nl.belastingdienst.aig.melding.Melding;

public class MijnTestBean {

	@Autowired(required=true)
	public Melding melding;
	

}
