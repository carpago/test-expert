import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.resource.ResourceException;
import javax.resource.cci.ConnectionFactory;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import nl.belastingdienst.aig.dao.CtgBeoordelenMeldingDaoImpl;
import nl.belastingdienst.aig.dao.HandmatigBeoordelenMeldingDAO;
import nl.belastingdienst.aig.dao.HandmatigBeoordelenMeldingDAOCtgImpl;
import nl.belastingdienst.aig.dao.data.CtgDFHMIRSOutput;

/*
 *
 *  ---------------------------------------------------------------------------------------------------------
 *              Ident: Belastingdienst - B/CAO 
 *            Creator: lomar00
 *          Copyright: (c) 2011 Belastingdienst / Centrum voor Applicatieontwikkeling en Onderhoud,
 *                         All Rights Reserved.
 *  ---------------------------------------------------------------------------------------------------------
 *                                              |   Unpublished work. This computer program includes
 *     De Belastingdienst                       |   Confidential, Properietary Information and is a
 *     Postbus 9050                             |   trade Secret of the Belastingdienst. No part of
 *     7300 GM  Apeldoorn                       |   this file may be reproduced or transmitted in any
 *     The Netherlands                          |   form or by any means, electronic or mechanical,
 *     http://belastingdienst.nl/               |   for the purpose, without the express written
 *                                              |   permission of the copyright holder.
 *  ---------------------------------------------------------------------------------------------------------
 *
 */

/**
 * TODO lomar00: beschrijf deze klasse !
 * 
 * @author lomar00
 */
public class CtgHandmatigBeoordelenIntegrationTest extends TestCase {

	HandmatigBeoordelenMeldingDAO beoordelenMeldingDAO = null;
	CtgBeoordelenMeldingDaoImpl ctgBeoordelenMelding = null;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		this.beoordelenMeldingDAO = new HandmatigBeoordelenMeldingDAOCtgImpl();
		Properties props = new Properties();
		props.put(Context.PROVIDER_URL, "iiop://localhost:2809");
		props.put(Context.INITIAL_CONTEXT_FACTORY, "com.ibm.ws.naming.util.WsnInitCtxFactory");

		Context initCtx = new InitialContext(props);

		ConnectionFactory newConnectionFactory = (ConnectionFactory) initCtx.lookup("eis/CTGFactory");
		this.ctgBeoordelenMelding = new CtgBeoordelenMeldingDaoImpl();

		this.ctgBeoordelenMelding.setConnectionFactory(newConnectionFactory);
		((HandmatigBeoordelenMeldingDAOCtgImpl) beoordelenMeldingDAO).setCtgBeoordelenMeldingDaoImpl(ctgBeoordelenMelding);
	}

	@Test
	public void test() {
		CtgDFHMIRSOutput inOutput = new CtgDFHMIRSOutput();
//		inOutput.setLinkage__interface("<?xml version=\"1.0\" encoding=\"UTF-8\"?><AIG_HandmatigBeoordelenMelding xsi:noNamespaceSchemaLocation=\"AIG9022I%20-%20AIG_HandmatigBeoordelenMelding.xsd\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><Web2zOS><BSN>111111110</BSN><Belastingjaar>2009</Belastingjaar><BerichtkenmerkAIG>aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa</BerichtkenmerkAIG><OorspronkelijkeMeldingnaam>aaaaa</OorspronkelijkeMeldingnaam><ActieGebruiker>1</ActieGebruiker></Web2zOS></AIG_HandmatigBeoordelenMelding>");
		inOutput.setLinkage__interface("<?xml version='1.0' encoding='UTF-8'?><AIG_HandmatigBeoordelenMelding><Web2zOS><BSN>100000001</BSN><Belastingjaar>2011</Belastingjaar><BerichtkenmerkAIG>TEST1</BerichtkenmerkAIG><OorspronkelijkeMeldingnaam>MA06</OorspronkelijkeMeldingnaam><ActieGebruiker>1</ActieGebruiker><StatusMelding>Stoppen</StatusMelding><RedenNietVerwerkbaar>testen of actie 1 werkt</RedenNietVerwerkbaar><DatumIngangAIW1>2001-01-01</DatumIngangAIW1><DatumtijdGeregistreerdAIW1>2001-01-01-20.00.00.000000</DatumtijdGeregistreerdAIW1><DatumIngangAIW2>2001-01-01</DatumIngangAIW2><DatumtijdGeregistreerdAIW2>2001-01-01-20.00.00.000000</DatumtijdGeregistreerdAIW2><DatumIngangAIO>2001-01-01</DatumIngangAIO><RedenEinde>nvt</RedenEinde><Medewerker>WEGDR00</Medewerker></Web2zOS></AIG_HandmatigBeoordelenMelding>");
		
		try {
			inOutput = this.ctgBeoordelenMelding.insert(inOutput);
		} catch (ResourceException e) {
			// TODO lomar00: Automatisch gegenereerd catch block.
			throw new RuntimeException("Oeps, vergeten deze fout af te handelen: " + e, e);
		}
		
		
	}

}
