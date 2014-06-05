package nl.foo;

import java.util.Date;

import nl.carpago.testexpert.annotation.Valid;

public class Announcement{

	private static final long serialVersionUID = -6625896018812577552L;

	// start key
	private AccidentalPerson accidentalPerson;

	@Valid(in="aig01", out="aig02")
	private String messageDigest;
	// einde key
	private String messageDigestOtherParty;

	private Date dateAccident;

	private Date dateTimeRegistration;

	private String socialSecuritySource;

	private String employee;

	private String originFirstContactName;

	private String taskId;

	private String taskIdFurtherDefined;

	private String shortenedDescription;

	private Integer value;

	private String state;
	
	
	
	
	public Announcement() {
		this.accidentalPerson = new AccidentalPerson(1277, (short) 2012);
	}
	
	/**
	 * @return Retourneert messageDigest.
	 */
	public String getMessageDigest() {
		return this.messageDigest;
	}

	/**
	 * @return Retourneert messageDigestOtherParty.
	 */
	public String getMessageDigestOtherParty() {
		return this.messageDigestOtherParty;
	}

	/**
	 * @return Retourneert accidentalPerson.
	 */
	public AccidentalPerson getAccidentalPerson() {
		return this.accidentalPerson;
	}

	/**
	 * @return Retourneert dateAccident.
	 */
	public Date getDateAccident() {
		return this.dateAccident;
	}

	/**
	 * @return Retourneert dateTimeRegistration.
	 */
	public Date getDateTimeRegistration() {
		return this.dateTimeRegistration;
	}

	/**
	 * @return Retourneert socialSecuritySource.
	 */
	public String getSocialSecuritySource() {
		return this.socialSecuritySource;
	}

	/**
	 * @return Retourneert employee.
	 */
	public String getEmployee() {
		return this.employee;
	}

	/**
	 * @return Retourneert oorspronkelijkeMeldingnaam.
	 */
	public String getOriginalFirstContactName() {
		return this.originFirstContactName;
	}

	/**
	 * @return Retourneert taskId.
	 */
	public String getTaskId() {
		return this.taskId;
	}

	/**
	 * @return Retourneert taskIdFurtherDefined.
	 */
	public String getTaskIdFurtherDefined() {
		return this.taskIdFurtherDefined;
	}

	/**
	 * @return Retourneert shortenedDescription.
	 */
	public String getShortenedDescription() {
		return this.shortenedDescription;
	}

	/**
	 * @return Retourneert value.
	 */
	public Integer getValue() {
		return this.value;
	}

	/**
	 * @param messageDigest
	 *            wordt in messageDigest gezet.
	 */
	public void setMessageDigest(String messageDigest) {
		this.messageDigest = messageDigest;
	}

	/**
	 * @param messageDigestOtherParty
	 *            wordt in messageDigestOtherParty gezet. Veldnaam en type in XSD:
	 *            BerichtkenmerkDerden, string 32
	 */
	public void setMessageDigestOtherParty(String messageDigestOtherParty) {
		this.messageDigestOtherParty = messageDigestOtherParty;
	}

	/**
	 * @param accidentalPerson
	 *            wordt in accidentalPerson gezet.
	 */
	public void setAccidentalPerson(AccidentalPerson accidentalPerson) {
		this.accidentalPerson = accidentalPerson;
	}

	/**
	 * @param dateAccident
	 *            wordt in dateAccident gezet. Veldnaam en type in XSD:
	 *            DatumPlaatsGevonden, string met pattern
	 *            [2][0-9]{3}-[0-3][0-9]-[0-9]{2}
	 */
	public void setDateAccident(Date dateAccident) {
		this.dateAccident = dateAccident;
	}

	/**
	 * @param dateTimeRegistration
	 *            wordt in dateTimeRegistration gezet.
	 */
	public void setDateTimeRegistration(Date dateTimeRegistration) {
		this.dateTimeRegistration = dateTimeRegistration;
	}

	/**
	 * @param socialSecuritySource
	 *            wordt in socialSecuritySource gezet.
	 */
	public void setSocialSecuritySource(String socialSecuritySource) {
		this.socialSecuritySource = socialSecuritySource;
	}

	/**
	 * @param employee
	 *            wordt in employee gezet. Veldnaam en type in XSD:
	 *            Medewerker, string 7
	 */
	public void setEmployee(String employee) {
		this.employee = employee;
	}

	/**
	 * @param originalFirstContactName
	 *            wordt in oorspronkelijkeMeldingnaam gezet. Veldnaam en type in
	 *            XSD: OorspronkelijkeMeldingnaam, string 5
	 */
	public void setOriginalFirstContactName(String originalFirstContactName) {
		this.originFirstContactName = originalFirstContactName;
	}

	/**
	 * @param taskId
	 *            wordt in taskId gezet.
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	/**
	 * @param taskIdFurtherDefined
	 *            wordt in taskIdFurtherDefined gezet.
	 */
	public void setTaskIdFurtherDefined(String taskIdFurtherDefined) {
		this.taskIdFurtherDefined = taskIdFurtherDefined;
	}

	/**
	 * @param shortenedDescription
	 *            wordt in shortenedDescription gezet.
	 */
	public void setShortenedDescription(String shortenedDescription) {
		this.shortenedDescription = shortenedDescription;
	}

	/**
	 * @param value
	 *            wordt in value gezet. Veldnaam en type in XSD: Waarde,
	 *            integer 13
	 */
	public void setValue(Integer value) {
		this.value = value;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((messageDigest == null) ? 0 : messageDigest.hashCode());
		result = prime * result + ((accidentalPerson == null) ? 0 : accidentalPerson.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Announcement other = (Announcement) obj;
		if (messageDigest == null) {
			if (other.messageDigest != null)
				return false;
		} else if (!messageDigest.equals(other.messageDigest))
			return false;
		if (accidentalPerson == null) {
			if (other.accidentalPerson != null)
				return false;
		} else if (!accidentalPerson.equals(other.accidentalPerson))
			return false;
		return true;
	}
}
