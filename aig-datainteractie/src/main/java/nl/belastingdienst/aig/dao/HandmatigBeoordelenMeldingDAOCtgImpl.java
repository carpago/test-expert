package nl.belastingdienst.aig.dao;

import java.util.List;

import javax.resource.ResourceException;

import nl.belastingdienst.aig.betrokkene.Betrokkene;
import nl.belastingdienst.aig.dao.data.CtgDFHMIRSOutput;
import nl.belastingdienst.aig.melding.Melding;

public class HandmatigBeoordelenMeldingDAOCtgImpl implements HandmatigBeoordelenMeldingDAO {
	private CtgBeoordelenMeldingDaoImpl ctgBeoordelenMeldingDaoImpl;

	public List<Melding> findAllByExample(Melding melding) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Melding> geefMeldingenMetStatusHandmatigeAfhandeling() {
		// TODO Auto-generated method stub
		return null;
	}

	public Melding geefMelding(Betrokkene betrokkene, String berichtkenmerkAig) {
		CtgDFHMIRSOutput inOutput = new CtgDFHMIRSOutput();
		inOutput.setLinkage__interface(berichtkenmerkAig);
		
		try {
			inOutput = this.ctgBeoordelenMeldingDaoImpl.insert(inOutput);
		} catch (ResourceException e) {
			// TODO lomar00: Automatisch gegenereerd catch block.
			throw new RuntimeException("Oeps, vergeten deze fout af te handelen: " + e, e);
		}
		
		String melding = inOutput.getLinkage__interface();
		
		return null;
	}
	
	
	

	public void setCtgBeoordelenMeldingDaoImpl(CtgBeoordelenMeldingDaoImpl ctgBeoordelenMeldingDaoImpl) {
		this.ctgBeoordelenMeldingDaoImpl = ctgBeoordelenMeldingDaoImpl;
	}

	/**
	 * {@inheritDoc}
	 */
	public String handmatigAfhandelenMelding(String meldingAsXML) {
		CtgDFHMIRSOutput out = new CtgDFHMIRSOutput();
		out.setLinkage__interface(meldingAsXML);
		
		try {
			this.ctgBeoordelenMeldingDaoImpl.insert(out);
		} catch (ResourceException e) {
			// TODO lomar00: Automatisch gegenereerd catch block.
			throw new RuntimeException("Oeps, vergeten deze fout af te handelen: " + e, e);
		}
		
		String resultVanBackend = out.getLinkage__interface();
		
		return resultVanBackend;
	}


}
