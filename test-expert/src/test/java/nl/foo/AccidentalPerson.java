package nl.foo;

import java.io.Serializable;

import nl.carpago.testexpert.annotation.Valid;

public class AccidentalPerson implements Serializable {

	@Valid(in="2011", out="2012")
	private Short year;

	@Valid(in="127797592", out="127797592")
	private Integer socialSecurityNumber;

	private Integer someValue;

	private String someIndication;
	
	public AccidentalPerson() {}

	/**
	 * @param socialSecurityNumber
	 * @param year
	 */
	public AccidentalPerson(Integer socialSecurityNumber, Short aYear) {
		this.socialSecurityNumber = socialSecurityNumber;
		this.year = aYear;
	}
	
	/**
	 * @return Retourneert year.
	 */
	public Short getYear() {
		return this.year;
	}

	/**
	 * @return Retourneert socialSecurityNumber.
	 */
	public Integer getSocialSecurityNumber() {
		return this.socialSecurityNumber;
	}

	/**
	 * @return Retourneert someValue.
	 */
	public Integer getSomeValue() {
		return this.someValue;
	}

	/**
	 * @return Retourneert someIndication.
	 */
	public String getSomeIndication() {
		return this.someIndication;
	}

	/**
	 * @param year
	 *            wordt in year gezet. Veldnaam en type in XSD: Belastingjaar, positiveInteger 4
	 */
	public void setYear(Short year) {
		this.year = year;
	}

	/**
	 * @param socialSecurityNumber
	 *            wordt in socialSecurityNumber gezet.
	 */
	public void setSocialSecurityNumber(Integer socialSecurityNumber) {
		this.socialSecurityNumber = socialSecurityNumber;
	}

	/**
	 * @param someValue
	 *            wordt in someValue gezet.
	 */
	public void setSomeValue(Integer validSomeValue) {
		this.someValue = validSomeValue;
	}

	/**
	 * @param someIndication
	 *            wordt in someIndication gezet.
	 */
	public void setSomeIndication(String someIndication) {
		this.someIndication = someIndication;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((year == null) ? 0 : year.hashCode());
		result = prime * result + ((socialSecurityNumber == null) ? 0 : socialSecurityNumber.hashCode());
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
		AccidentalPerson other = (AccidentalPerson) obj;
		if (year == null) {
			if (other.year != null)
				return false;
		} else if (!year.equals(other.year))
			return false;
		if (socialSecurityNumber == null) {
			if (other.socialSecurityNumber != null)
				return false;
		} else if (!socialSecurityNumber.equals(other.socialSecurityNumber))
			return false;
		return true;
	}
}
