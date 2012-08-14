/*
 *
 *  ----------------------------------------------------------------------------------------------------
 *              Ident: Belastingdienst - B/CICT
 *            Creator: lomar00
 *          Copyright: (c) 2010 De Belastingdienst / Centrum voor ICT,  All Rights Reserved.
 *  ----------------------------------------------------------------------------------------------------
 *                                              |   Unpublished work. This computer program includes
 *     De Belastingdienst                       |   Confidential, Properietary Information and is a
 *     Postbus 9050                             |   trade Secret of the Belastingdienst. No part of
 *     7300 GM  Apeldoorn                       |   this file may be reproduced or transmitted in any
 *     The Netherlands                          |   form or by any means, electronic or mechanical,
 *     http://belastingdienst.nl/               |   for the purpose, without the express written
 *                                              |   permission of the copyright holder.
 *  ----------------------------------------------------------------------------------------------------
 *
 */

package nl.belastingdienst.aig;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import nl.belastingdienst.aig.aanleiding.AanleidingBeeindigControllerTest;
import nl.belastingdienst.aig.aanleiding.AanleidingListControllerTest;
import nl.belastingdienst.aig.aanleiding.AanleidingShowControllerTest;
import nl.belastingdienst.aig.melding.MeldingActieControllerTest;
import nl.belastingdienst.aig.melding.MeldingActieSaveControllerTest;
import nl.belastingdienst.aig.melding.MeldingListControllerTest;
import nl.belastingdienst.aig.melding.MeldingShowControllerTest;
import nl.belastingdienst.aig.melding.OnderhoudenMeldingServiceImplTest;
import nl.belastingdienst.aig.melding.RegistreerMeldingControllerTest;
import nl.belastingdienst.aig.melding.RegistreerMeldingServiceJMSImplTestOrigineel;
import nl.belastingdienst.aig.onderzoek.OnderzoekListControllerTest;
import nl.belastingdienst.aig.onderzoek.OnderzoekShowControllerTest;
import nl.belastingdienst.aig.waarde.WaardeListControllerTest;
import nl.belastingdienst.aig.waarde.WaardeShowControllerTest;

public class AigSuite extends TestCase {
	
	public static Test suite() {
		TestSuite suite = new TestSuite("AIG all tests");
		
		suite.addTestSuite(WaardeListControllerTest.class);
		suite.addTestSuite(WaardeShowControllerTest.class);
		suite.addTestSuite(RegistreerMeldingControllerTest.class);
		suite.addTestSuite(MeldingListControllerTest.class);
		suite.addTestSuite(MeldingShowControllerTest.class);
		suite.addTestSuite(MeldingActieControllerTest.class);
		suite.addTestSuite(MeldingActieSaveControllerTest.class);
		suite.addTestSuite(AanleidingListControllerTest.class);
		suite.addTestSuite(AanleidingShowControllerTest.class);
		suite.addTestSuite(AanleidingBeeindigControllerTest.class);
		suite.addTestSuite(OnderzoekListControllerTest.class);
		suite.addTestSuite(OnderzoekShowControllerTest.class);
		suite.addTestSuite(RegistreerMeldingServiceJMSImplTestOrigineel.class);
		suite.addTestSuite(OnderhoudenMeldingServiceImplTest.class);
		
		return suite;
	}

}
