package nl.belastingdienst.aig.dao;

import javax.resource.ResourceException;
import javax.resource.cci.Connection;
import javax.resource.cci.ConnectionFactory;
import javax.resource.cci.ConnectionSpec;
import javax.resource.cci.Interaction;
import javax.resource.cci.InteractionSpec;
import javax.resource.cci.Record;
import javax.resource.cci.ResourceAdapterMetaData;


/**
 * @j2c.connectionSpec class="com.ibm.connector2.cics.ECIConnectionSpec"
 * @generated
 */
public class CtgBeoordelenMeldingDaoImpl implements nl.belastingdienst.aig.dao.CtgBeoordelenMeldingDao {

	private ConnectionSpec typeLevelConnectionSpec;
	private InteractionSpec invokedInteractionSpec;
	private InteractionSpec interactionSpec;
	private ConnectionSpec connectionSpec;
	private Connection connection;
	private ConnectionFactory connectionFactory;
	
	
	/**
	 * @j2c.interactionSpec class="com.ibm.connector2.cics.ECIInteractionSpec"
	 * @j2c.interactionSpec-property name="functionName" value="AIG0310"
	 * @j2c.interactionSpec-property name="tranName" value="AI80"
	 * @generated
	 */
	public nl.belastingdienst.aig.dao.data.CtgDFHMIRSOutput insert(nl.belastingdienst.aig.dao.data.CtgDFHMIRSOutput arg)
			throws javax.resource.ResourceException {
				ConnectionSpec cs = getConnectionSpec();
				InteractionSpec is = interactionSpec;
				try {
					if (cs == null) {
						cs = getTypeLevelConnectionSpec();
					}
					if (is == null) {
						is = new com.ibm.connector2.cics.ECIInteractionSpec();
						((com.ibm.connector2.cics.ECIInteractionSpec) is).setFunctionName("AIG0310");
						((com.ibm.connector2.cics.ECIInteractionSpec) is).setTranName("AI80");
						((com.ibm.connector2.cics.ECIInteractionSpec) is).setTPNName("AI80");
					}
				} catch (Exception e) {
					throw new ResourceException(e.getMessage());
				}
				nl.belastingdienst.aig.dao.data.CtgDFHMIRSOutput output = new nl.belastingdienst.aig.dao.data.CtgDFHMIRSOutput();
				invoke(cs, is, arg, output);
				return output;
			}

	/**
	 * @generated
	 */
	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	/**
	 * @generated
	 */
	public void setConnectionFactory(ConnectionFactory newConnectionFactory) {
		connectionFactory = newConnectionFactory;
		connection = null;
	}

	/**
	 * @generated
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * @generated
	 */
	public void setConnection(Connection newConnection) {
		connection = newConnection;
	}

	/**
	 * @generated
	 */
	public ConnectionSpec getConnectionSpec() {
		return connectionSpec;
	}

	/**
	 * @generated
	 */
	public void setConnectionSpec(ConnectionSpec newConnectionSpec) {
		connectionSpec = newConnectionSpec;
	}

	/**
	 * @generated
	 */
	public InteractionSpec getInteractionSpec() {
		return invokedInteractionSpec != null ? invokedInteractionSpec : interactionSpec;
	}

	/**
	 * @generated
	 */
	public void setInteractionSpec(InteractionSpec newInteractionSpec) {
		interactionSpec = newInteractionSpec;
	}

	/**
	 * @generated
	 */
	protected void invoke(ConnectionSpec cs, InteractionSpec is, Record arg, Record output) throws ResourceException {
		Connection conn = getConnection();
		ConnectionFactory cf = getConnectionFactory();
		boolean localConnection = conn == null;
		if (localConnection
				&& cf != null) {
			if (cs != null)
				conn = cf.getConnection(cs);
			else
				conn = cf.getConnection();
		}
		if (conn != null) {
			Interaction interaction = conn.createInteraction();
			try {
				boolean executeInOut = true;
				if (cf != null) {
					ResourceAdapterMetaData raMD = cf.getMetaData();
					if (raMD != null)
						executeInOut = raMD.supportsExecuteWithInputAndOutputRecord();
				}
				if (executeInOut)
					interaction.execute(is, arg, output);
				else
					output = interaction.execute(is, arg);
			} finally {
				interaction.close();
				if (localConnection)
					conn.close();
				invokedInteractionSpec = is;
			}
		}
		return;
	}

	/**
	 * @generated
	 */
	protected void initializeBinding() throws ResourceException {
		typeLevelConnectionSpec = new com.ibm.connector2.cics.ECIConnectionSpec();
	}

	/**
	 * @generated
	 */
	public CtgBeoordelenMeldingDaoImpl() throws ResourceException {
		initializeBinding();
	}

	/**
	 * @generated
	 */
	private ConnectionSpec getTypeLevelConnectionSpec() {
		return typeLevelConnectionSpec;
	}
}