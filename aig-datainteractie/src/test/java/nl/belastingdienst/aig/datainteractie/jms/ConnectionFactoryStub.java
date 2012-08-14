/*
 *
 *  ---------------------------------------------------------------------------------------------------------
 *              Titel: ConnectionFactoryStub.java
 *             Auteur: vriej39
 *    Creatietijdstip: 15 jun 2011 11:38:02
 *          Copyright: (c) 2011 Belastingdienst / Centrum voor Applicatieontwikkeling en Onderhoud,
 *                     All Rights Reserved.
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
package nl.belastingdienst.aig.datainteractie.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

/**
 * Stub implementatie voor een JMS ConnectionFactory.
 * 
 * @author vriej39
 */
public class ConnectionFactoryStub implements ConnectionFactory {

	/**
	 * {@inheritDoc}
	 */
	public Connection createConnection() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public Connection createConnection(String userName, String password) throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

}
