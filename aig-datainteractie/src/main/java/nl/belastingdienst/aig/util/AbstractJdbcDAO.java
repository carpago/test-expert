package nl.belastingdienst.aig.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * Basisklasse voor DAO's die gebruik maken van JdbcTemplate.
 * <p>
 * Heeft properties en bijbehorende setters en getters voor vijf query Strings (insert, update, delete, select en selectAll).
 * <p>
 * Bevat select en delete methoden voor ophalen en verwijderen van objecten obv sleutel.
 * <p>
 * Is een RowMapper: de methode mapRow() dient te zorgen voor omzetting van 1 enkele regel van de gegeven ResultSet naar een domein
 * object.
 * 
 * @author vriej39
 */
public abstract class AbstractJdbcDAO extends JdbcDaoSupport implements RowMapper {

	private static final long NANOS_PER_MILLI = 1000000;

	private String querySelectAll;

	/**
	 * @param rs
	 * @param colName
	 * @return opgegeven kolom in resultset als gevraagd type
	 * @throws SQLException
	 */
	protected Boolean getBoolean(final ResultSet rs, final String colName) throws SQLException {
		Boolean result = null;
		final int i = rs.getInt(colName);
		if (!rs.wasNull()) {
			result = new Boolean(i != 0);
		}
		return result;
	}

	/**
	 * @param rs
	 * @param colName
	 * @return opgegeven kolom in resultset als gevraagd type
	 * @throws SQLException
	 */
	protected Double getDouble(final ResultSet rs, final String colName) throws SQLException {
		Double result = null;
		final double d = rs.getDouble(colName);
		if (!rs.wasNull()) {
			result = new Double(d);
		}

		return result;
	}

	/**
	 * @param rs
	 * @param colName
	 * @return opgegeven kolom in resultset als gevraagd type
	 * @throws SQLException
	 */
	protected int getInt(final ResultSet rs, final String colName) throws SQLException {
		return rs.getInt(colName);
	}

	/**
	 * @param rs
	 * @param colName
	 * @return opgegeven kolom in resultset als gevraagd type
	 * @throws SQLException
	 */
	protected Integer getInteger(final ResultSet rs, final String colName) throws SQLException {
		Integer result = null;
		final int i = rs.getInt(colName);
		if (!rs.wasNull()) {
			result = new Integer(i);
		}
		return result;
	}

	/**
	 * @param rs
	 * @param colName
	 * @return opgegeven kolom in resultset als gevraagd type
	 * @throws SQLException
	 */
	protected Timestamp getJavaUtilTimestamp(final ResultSet rs, final String colName) throws SQLException {
		final Timestamp result = rs.getTimestamp(colName);

		return result;
	}

	/**
	 * @param rs
	 * @param colName
	 * @return opgegeven kolom in resultset als gevraagd type
	 * @throws SQLException
	 */
	protected Long getLong(final ResultSet rs, final String colName) throws SQLException {
		Long result = null;
		final long l = rs.getLong(colName);
		if (!rs.wasNull()) {
			result = new Long(l);
		}
		return result;
	}

	/**
	 * @return String
	 */
	public String getQuerySelectAll() {
		return this.querySelectAll;
	}

	/**
	 * @param rs
	 * @param colName
	 * @return opgegeven kolom in resultset als gevraagd type
	 * @throws SQLException
	 */
	protected Short getShort(final ResultSet rs, final String colName) throws SQLException {
		Short result = null;
		final short s = rs.getShort(colName);
		if (!rs.wasNull()) {
			result = new Short(s);
		}
		return result;
	}

	/**
	 * @param rs
	 * @param colName
	 * @return opgegeven kolom in resultset als gevraagd type
	 * @throws SQLException
	 */
	protected java.sql.Date getSqlDate(final ResultSet rs, final String colName) throws SQLException {
		final java.sql.Date sqlDate = rs.getDate(colName);

		return sqlDate;
	}

	/**
	 * @param rs
	 * @param colName
	 * @return opgegeven kolom in resultset als gevraagd type
	 * @throws SQLException
	 */
	protected String getString(final ResultSet rs, final String colName) throws SQLException {
		return rs.getString(colName);
	}

	/**
	 * @param rs
	 * @param colName
	 * @return opgegeven kolom in resultset als gevraagd type
	 * @throws SQLException
	 */
	protected Date getTimestamp(final ResultSet rs, final String colName) throws SQLException {
		Date result = null;
		final Timestamp tsp = rs.getTimestamp(colName);
		if (tsp != null) {
			if (System.getProperty("java.version")
				.startsWith("1.3.")) {
				// timeStamp.getTime geeft het tijdstip afgerond in hele
				// seconden
				// Hierbij moeten dus nog de milliseconden opgeteld worden,
				// anders gaan deze verloren.
				// De milliseconden zitten verstopt in timeStamp.getNanos.
				// Deze geeft echter de nanoseconden welke dus naar
				// milliseconden geconverteerd moeten worden
				result = new Date(tsp.getTime()
						+ tsp.getNanos()
						/ AbstractJdbcDAO.NANOS_PER_MILLI);
			} else {
				// Vanaf Java versie 1.4 is deze bug opgelost. (Is alleen nog
				// niet getest!)
				result = new Date(tsp.getTime());
			}
		}
		return result;
	}

	/**
	 * @param rs
	 * @param colName
	 * @return opgegeven kolom in resultset als gevraagd type
	 * @throws SQLException
	 */
	protected java.util.Date getUtilDate(final ResultSet rs, final String colName) throws SQLException {
		java.util.Date result = null;
		final java.sql.Date sqlDate = this.getSqlDate(rs, colName);
		if (sqlDate != null) {
			result = new java.util.Date(sqlDate.getTime());
		}
		return result;
	}

	/**
	 * @param string
	 */
	public void setQuerySelectAll(final String string) {
		this.querySelectAll = string;
	}
}
