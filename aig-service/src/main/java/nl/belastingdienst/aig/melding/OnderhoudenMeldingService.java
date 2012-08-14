/*
 * OnderhoudenBurgerservicenummerGegevensService.java 23-mei-2007
 *
 *-----------------------------------------------------------------------------------
 *     Ident: Sector Ontwikkeling - B/CICT
 *    Author: Raymond Loman
 * Copyright: (c) 2007 De Belastingdienst / Centrum voor ICT,  All Rights Reserved.
 *-----------------------------------------------------------------------------------
 * De Belastingdienst           |  Unpublished work. This computer program includes
 * Postbus 9050                 |  Confidential, Properietary Information and is a
 * 7300 GM Apeldoorn            |  trade Secret of the Belastingdienst. No part of
 * The Netherlands              |  this file may be reproduced or transmitted in any
 *                              |  form or by any means, electronic or mechanical,
 *                              |  for the purpose, without the express written
 *                              |  permission of the copyright holder.
 *-----------------------------------------------------------------------------------
 */
package nl.belastingdienst.aig.melding;

import java.util.List;

import nl.belastingdienst.aig.betrokkene.Betrokkene;
import nl.carpago.unittestgenerator.annotation.Valid;

/**
 * Business service interface voor het onderhouden van Melding(en).
 * 
 * @author Raymond Loman
 */
public interface OnderhoudenMeldingService {

	List<Melding> geefMeldingenMetStatusHandmatigeAfhandeling() ;

	List<Melding> findAllByExample(Melding melding);
	
	Melding geefMelding(Betrokkene betrokkene, String berichtkenmerkAig);
	
	int telOp(int a, int b);
}
