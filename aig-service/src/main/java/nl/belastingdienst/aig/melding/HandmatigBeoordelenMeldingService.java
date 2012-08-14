package nl.belastingdienst.aig.melding;

import nl.belastingdienst.aig.aanleiding.Aanleiding;



/** interface voor het implementeren van UC03 - Handmatig Beoordelen Melding
 * 
 * @author Raymond Loman
 *
 */
public interface HandmatigBeoordelenMeldingService {
	
	String handmatigAfhandelenMelding(Melding deAfTeHandelenMelding, Actie actie);
	
	String beeindigAanleiding(Aanleiding deTeBeeindigeinAanleiding);
	


}
