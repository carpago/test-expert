package nl.belastingdienst.aig.melding;

import java.io.Serializable;

import junit.framework.TestCase;
import nl.belastingdienst.aig.betrokkene.Betrokkene;
import nl.belastingdienst.aig.dao.MeldingDAO;
import nl.carpago.testexpert.Fixtures;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.thoughtworks.xstream.XStream;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={Fixtures.class})

public class OnderhoudenMeldingServiceImplMetSpringTest extends TestCase { 

	// class under test
	@Autowired
	private OnderhoudenMeldingServiceImpl onderhoudenMeldingServiceImpl;

	// collaborating classes
	@Autowired
	private MeldingDAO meldingDao;
	
	
	//fixture classes
	@Autowired
	private Melding melding;
	
	@Autowired
	private Betrokkene betrokkene;
	
	@Autowired
	private String berichtkenmerkAig;
	
	@Autowired
	private String voornaam;
	
	

	@Before
	public void setUp() {

		// this.onderhoudenMeldingServiceImpl.setMeldingDao(this.meldingDao);

	}
	
	public static boolean equals(Object lhs, Object rhs) {
		XStream xstream = new XStream();
		
		String thisXML = xstream.toXML(lhs);
		String otherXML = xstream.toXML(rhs);
		
		return thisXML.equals(otherXML);
				
		
	}
	
	public Object cloneMe(Serializable serializable) {
		
		//byte[] bytesArray = SerializationUtils.serialize(o);
		//Object result = SerializationUtils.deserialize(bytesArray);
		Serializable result = (Serializable) org.apache.commons.lang.SerializationUtils.clone(serializable);
		
		assertTrue(serializable != result);
		
		
		
		return result;
	}

	@Test
	public void testGeefMelding(){ 
		
		// appears to be true
		//System.out.println(this.meldingDao == onderhoudenMeldingServiceImpl.getMeldingDao());
		
		//Melding localMelding = melding;//(Melding) this.cloneMe(melding);

		
		
		EasyMock.expect(getMeldingDao().geefMelding(betrokkene, berichtkenmerkAig, voornaam)).andReturn((Melding) this.cloneMe(melding));
		EasyMock.replay(getMeldingDao());
		
		Melding resultFromMethod = onderhoudenMeldingServiceImpl.geefMelding(betrokkene, berichtkenmerkAig);
		
		//!!! bruikbaar! ook met dieper geneste objecten zoals een betrokken zijn bjaar in melding
		assertTrue(EqualsBuilder.reflectionEquals(melding, resultFromMethod));
		
		// !!! bruikbaar!   ook met dieper geneste objecten zoals een betrokkene zijn bjaar in melding
		// assertTrue(this.equals(melding,  resultFromMethod));
		//EasyMock.same(melding); ??? wat is dit ?
		
		
		
	}

	public MeldingDAO getMeldingDao(){
		return this.meldingDao;
	}

} 

