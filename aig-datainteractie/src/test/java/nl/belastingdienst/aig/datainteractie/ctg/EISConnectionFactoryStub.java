/*
 *
 *  ---------------------------------------------------------------------------------------------------------
 *              Titel: ECIConnectionFactoryStub.java
 *             Auteur: vriej39
 *    Creatietijdstip: 15 jun 2011 11:41:13
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
package nl.belastingdienst.aig.datainteractie.ctg;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.resource.ResourceException;
import javax.resource.cci.Connection;
import javax.resource.cci.ConnectionFactory;
import javax.resource.cci.ConnectionSpec;
import javax.resource.cci.RecordFactory;
import javax.resource.cci.ResourceAdapterMetaData;

/**
 * Stub implementatie voor een EIS ConnectionFactory.
 * 
 * @author vriej39
 */
public class EISConnectionFactoryStub implements ConnectionFactory {

	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	public Connection getConnection() throws ResourceException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public Connection getConnection(ConnectionSpec arg0) throws ResourceException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public ResourceAdapterMetaData getMetaData() throws ResourceException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public RecordFactory getRecordFactory() throws ResourceException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setReference(Reference reference) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 */
	public Reference getReference() throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

}
