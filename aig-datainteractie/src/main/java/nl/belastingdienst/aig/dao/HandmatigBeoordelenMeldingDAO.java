package nl.belastingdienst.aig.dao;

import java.util.List;

import nl.belastingdienst.aig.betrokkene.Betrokkene;
import nl.belastingdienst.aig.melding.Melding;

public interface HandmatigBeoordelenMeldingDAO {
	
	/**
	 * Deze methode levert alle meldingen waarvan de status op handmatige afhandeling staat.
	 * @return List<Melding>
	 */
	List<Melding> geefMeldingenMetStatusHandmatigeAfhandeling();
	
	
	
	/**
	 * 
	 * @param melding
	 * @return List<Melding> met alle meldingen by example gelijk zijn aan parameter melding:
	 * 	- betrokkene.belastingJaar
	 *  - betrokkene.burgerservicenummer
	 *  - berichtkenmerkDerden
	 *  - status
	 */
	List<Melding> findAllByExample(Melding melding);
	
	/*Betrokkene moet belastingjaar en burgerservicenummer bevatten zodat sleutel gevuld is */
	Melding geefMelding(Betrokkene betrokkene, String berichtkenmerkAig);
	
	String handmatigAfhandelenMelding(String meldingAsXML);


}
