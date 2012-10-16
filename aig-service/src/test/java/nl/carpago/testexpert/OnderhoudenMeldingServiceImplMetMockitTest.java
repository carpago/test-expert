package nl.carpago.testexpert;

import java.util.List;

import mockit.Expectations;
import mockit.Mocked;
import nl.belastingdienst.aig.betrokkene.Betrokkene;
import nl.belastingdienst.aig.melding.Melding;
import nl.belastingdienst.aig.melding.OnderhoudenMeldingServiceImpl;
import nl.belastingdienst.aig.melding.PersoonDAO;
import nl.carpago.testexpert.AbstractTestExpert;
import nl.carpago.testexpert.Fixtures;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Fixtures.class })
public class OnderhoudenMeldingServiceImplMetMockitTest extends AbstractTestExpert {

	// fixtures
	@Autowired
	private Melding melding;
	@Autowired
	private String anderBerichtkenmerkAig;
	@Autowired
	private Betrokkene andereBetrokkene;
	@Autowired
	private String string;
	@Autowired
	private String voornaam;
	@Autowired
	private Integer number;

	// class under test
	private OnderhoudenMeldingServiceImpl onderhoudenMeldingServiceImpl;

	@Mocked
	private PersoonDAO persoonDao;

	@Before
	@SuppressWarnings("unchecked")
	public void setUp() {
		this.onderhoudenMeldingServiceImpl = new OnderhoudenMeldingServiceImpl();
		this.onderhoudenMeldingServiceImpl.setPersoonDao(persoonDao);

	}

	@Test
	public void testGetSofiForIntNumberMockit() {
		new Expectations() {
			{
				persoonDao.getSofi(3);
				forEachInvocation = new Object() {
					@SuppressWarnings("unused")
					String validate(int aNumber) {

						return "3";
					}
				};
			}
		};

		String resultFromService = this.onderhoudenMeldingServiceImpl.getNumber(3);

		Assert.assertEquals("3", resultFromService);
	}

	

	public PersoonDAO getPersoonDao() {
		return this.persoonDao;
	}

	
}