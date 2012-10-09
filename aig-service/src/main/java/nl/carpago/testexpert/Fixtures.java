package nl.carpago.testexpert;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nl.belastingdienst.aig.aanleiding.Aanleiding;
import nl.belastingdienst.aig.betrokkene.Betrokkene;
import nl.belastingdienst.aig.melding.Melding;
import nl.belastingdienst.aig.melding.MeldingDAO;
import nl.belastingdienst.aig.melding.OnderhoudenMeldingService;
import nl.belastingdienst.aig.melding.OnderhoudenMeldingServiceImpl;

import org.easymock.EasyMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // is dit wel nodig ???

public class Fixtures {
	
	@Bean
	public Melding melding() {
		Melding melding = new Melding();
		melding.setBerichtkenmerkAig(this.berichtkenmerkAig());
		melding.setDatumPlaatsGevonden(new Date(112,7,15));
		melding.setWaarde(3);
		melding.setBetrokkene(this.betrokkene());
		
		return melding;
	}
	
	@Bean
	public Aanleiding aanleiding() {
		
		Aanleiding aanleiding = new Aanleiding();
		
		return aanleiding;
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
	public Betrokkene betrokkene() {
		Betrokkene betrokkene = new Betrokkene(127797592, (short) 2012);
		betrokkene.setBurgerservicenummer(127797592);
		betrokkene.setBelastingJaar((short) 2012);
		
		return betrokkene;
	}
	

	@Bean
	public Betrokkene andereBetrokkene() {
		Betrokkene betrokkene = new Betrokkene(127797592, (short) 2012);
		betrokkene.setBurgerservicenummer(127797592);
		betrokkene.setBelastingJaar((short) 2012);
		
		return betrokkene;
	}
	
	@Bean
	public MijnTestBean mijnTestBean() {
		return new MijnTestBean();
	}
	
	@Bean
	public OnderhoudenMeldingService onderhoudenMeldingServiceImpl() {
		OnderhoudenMeldingServiceImpl meldingService = new OnderhoudenMeldingServiceImpl();
		meldingService.setMeldingDao(this.meldingDao());
		
		return meldingService;
	}
	
	@Bean
	public MeldingDAO meldingDao() {
		return EasyMock.createMock(MeldingDAO.class);
	}
	
	@Bean
	public Method methode() {
		Method method = null;
		try {
			method = OnderhoudenMeldingServiceImpl.class.getMethod("geefMelding", Betrokkene.class, String.class);
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
