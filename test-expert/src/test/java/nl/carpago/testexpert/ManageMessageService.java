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
package nl.carpago.testexpert;

import java.util.List;

import nl.foo.AccidentalPerson;
import nl.foo.Announcement;


/**
 * Business service interface voor het onderhouden van Announcement(en).
 * 
 * @author Raymond Loman
 */
public interface ManageMessageService {

	List<Announcement> getMessagesWithStateManual() ;

	List<Announcement> findAllByExample(Announcement message);
	
	Announcement getMessage(AccidentalPerson accidentalPerson, String messageDigest);
	
	int add(int a, int b);
}
