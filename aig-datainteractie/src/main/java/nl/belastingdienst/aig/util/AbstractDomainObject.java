package nl.belastingdienst.aig.util;

import java.io.Serializable;

/**
 * Basisklasse voor klassen/objecten uit het domeinmodel.
 * 
 * @author vriej39
 */
public abstract class AbstractDomainObject implements Serializable {

	/**
	 * Commentaar voor &lt;code&gt;serialVersionUID&lt;/code&gt;
	 */
	private static final long serialVersionUID = 3552448555112441550L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public abstract boolean equals(Object arg0);

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public abstract int hashCode();

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ParseUtil.toString(this);
	}

}
