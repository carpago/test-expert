<fieldset>
	<legend id="legendGeselecteerd" align="left"><spring:message code="title.onderzoek.details.aanleiding" /></legend>
	<legend id="legendGerelateerd" style="display: none;" align="left"><spring:message code="title.onderzoek.details.aanleiding" /></legend>
	<table border="0" cellpadding="4" cellspacing="3">
		<tbody>
			<tr title="Het tijdstip waarop de aanleiding is geregistreerd in de administratie AIG.">
				<td align="left" width="257"><b><spring:message code="label.begin" /></b></td>
				<td id="registratieTijdstipBegin" align="left" width="572">${eersteAanleiding.registratieTijdstipBegin}</td>
			</tr>
			<tr title="Het tijdstip waarop de afhandeling bij de aanleiding is geregistereerd in de administratie AIG.">
				<td align="left" width="257"><b><spring:message code="label.einde" /></b></td>
				<td id="registratieTijdstipEinde" align="left" width="572">${eersteAanleiding.registratieTijdstipEinde}</td>
			</tr>
			<tr title="De dag waarop er geen rechtsmiddelen meer open staan om de waarden van het AIG te betwisten en daarmee definitief is geworden.">
				<td align="left" width="257"><b><spring:message code="title.aanleiding.onherroeplijk" /></b></td>
				<td id="datumOnherroepelijk" align="left" width="572">${eersteAanleiding.datumOnherroepelijk}</td>
			</tr>
			<tr title="De reden van de beëindiging van de aanleiding.">
				<td align="left" width="257" style="vertical-align: top"><b><spring:message code="title.aanleiding.reden.einde" /></b></td>
				<td id="redenEinde" align="left" width="572">${eersteAanleiding.redenEinde}</td>
			</tr>
			<tr>
				<td align="left" width="257"><b><spring:message code="title.soort.melding" /></b></td>
				<td id="melding.oorspronkelijkeMeldingNaam" align="left" width="572">${eersteAanleiding.melding.oorspronkelijkeMeldingNaam}</td>
			</tr>
			<tr title="Geeft aan in welke fase van de afhandeling de melding zich bevind.
				STATUS = STM01 nog te beoordelen, STM02 beoordeling heeft geleid tot registratie aanleiding, STM03 beoordeling heeft geleid tot handmatige afhandeling, STM04 beoordeling heeft geleid niet verwerkbaar, STM05 wacht op starten nieuw onderzoek, STM99 overig">
				<td align="left" width="257"><b><spring:message code="title.status" /></b></td>
				<td id="melding.status" align="left" width="572">${eersteAanleiding.melding.status}</td>
			</tr>
			<tr title="Kenmerk dat het bericht uniek maakt in de administratie van derden.">
				<td align="left" width="257"><b><spring:message code="title.berichtkenmerk.derden" /></b></td>
				<td id="melding.berichtkenmerkDerden" nalign="left" width="572">${eersteAanleiding.melding.berichtkenmerkDerden}</td>
			</tr>
			<tr title="Het bedrag uit het oorspronkelijke bericht.">
				<td align="left" width="257"><b><spring:message code="title.waarde" /></b></td>
				<td id="melding.waarde" align="left" width="572">&euro;&nbsp;${eesteAanleiding.melding.waarde}</td>
			</tr>
			<tr title="De dag waarop de gebeurtenis heeft plaatsgevonden.">
				<td align="left" width="257"><b><spring:message code="title.datum.plaatsgevonden" /></b></td>
				<td id="melding.datumPlaatsGevonden" align="left" width="572">${eersteAanleiding.melding.datumPlaatsGevonden}</td>
			</tr>
			<tr title="Het tijdstip waarop de melding in AIG is geregistreerd.">
				<td align="left" width="257"><b><spring:message code=":label.datumtijd.registratie.aig" /></b></td>
				<td id="melding.datumTijdRegistratie" align="left" width="572">${eersteAanleiding.melding.datumTijdRegistratie}</td>
			</tr>
			<tr>
				<td align="left" width="257"><b><spring:message code="title.verkorte.meldingnaam" /></b></td>
				<td id="melding.verkorteMeldingNaam" align="left" width="572">${eersteAanleiding.melding.verkorteMeldingNaam}</td>
			</tr>
			<tr title="Het proces dat de melding heeft voortgebracht.">
				<td align="left" width="257"><b><spring:message code="title.fiscale.bron" /></b></td>
				<td id="melding.fiscaleBron" align="left" title=" (IB of IB-signaal of BJL of AIG)" width="572">${eersteAanleiding.melding.fiscaleBron}</td>
			</tr>
		</tbody>
	</table>
	
	<jsp:include page="betrokken_aig.jsp" />
	
	
<fieldset>