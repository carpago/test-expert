package nl.carpago.testexpert;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nl.foo.AccidentalPerson;
import nl.foo.Announcement;
import nl.foo.MessageDAO;

import org.easymock.EasyMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class Fixtures {
	@Bean
	public int number() {
		return 3;
	}
	
	@Bean
	public String string() {
		return "3";
	}
	
	@Bean
	public Announcement melding() {
		Announcement melding = new Announcement();
		melding.setMessageDigest(this.berichtkenmerkAig());
		melding.setDateAccident(new Date(112,7,15));
		melding.setValue(3);
		melding.setAccidentalPerson(this.betrokkene());
		
		return melding;
	}
	
	@Bean
	public int een() {
		
		return 1;
	}
	
	@Bean
	public List lijst() {
		return new ArrayList();
	}
	
	
	@Bean
	public int twee() {
		return 2;
	}
	
	@Bean
	public int drie() {
		return 3;
	}
	
	@Bean
	public String berichtkenmerkAig() {
		String result = "aig02";
		
		return result;
	}
	
	@Bean
	public String anderBerichtkenmerkAig() {
		String result = "aig02";
		
		return result;
	}
	
	@Bean
	public String voornaam() {
		
		String result = "Raymond";
		
		return result;
	}
	
	@Bean
	public String andereVoornaam() {
		
		String result = "Raymond";
		
		return result;
	}
	
	@Bean
	public AccidentalPerson betrokkene() {
		AccidentalPerson betrokkene = new AccidentalPerson(127797592, (short) 2012);
		betrokkene.setSocialSecurityNumber(127797592);
		betrokkene.setYear((short) 2012);
		
		return betrokkene;
	}
	

	@Bean
	public AccidentalPerson andereBetrokkene() {
		AccidentalPerson betrokkene = new AccidentalPerson(127797592, (short) 2012);
		betrokkene.setSocialSecurityNumber(127797592);
		betrokkene.setYear((short) 2012);
		
		return betrokkene;
	}
	
	@Bean
	public MijnTstBean mijnTestBean() {
		return new MijnTstBean();
	}
	
	@Bean
	public ManageMessageService onderhoudenMeldingServiceImpl() {
		ManageMessageServiceImpl meldingService = new ManageMessageServiceImpl();
		meldingService.setMeldingDao(this.meldingDao());
		
		return meldingService;
	}
	
	@Bean
	public MessageDAO meldingDao() {
		return EasyMock.createMock(MessageDAO.class);
	}
	
	@Bean
	public Method methode() {
		Method method = null;
		try {
			method = ManageMessageServiceImpl.class.getMethod("geefMelding", AccidentalPerson.class, String.class);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return method;
	}
	
	@Bean
	public String[] methodeInAnnotations() {
		String[] result = {"andereBetrokkene","anderBerichtkenmerkAig"};
		
		return result;
	}
	
	@Bean
	public String methodeOutAnnotations() {
		String result = "melding";
		
		return result;
	}
	
	@Bean
	public boolean onwaar() {
		return false;
	}
}
