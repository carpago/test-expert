<fieldset><legend align="left"><spring:message code="title.details.melding" /></legend>

<table border="0" cellpadding="4" cellspacing="3">
	<tbody>
		<tr title="BSN: Het nummer waaronder de natuurlijk persoon is ingeschreven bij de gemeente. Sofinummer: het nummer waaronder de natuurlijk persoon is geregistreerd bij de belastingdienst.">
			<td align="left" width="257"><b><spring:message code="label.burgerservicenummer" /></b></td>
			<td align="left" width="421">${melding.betrokkene.burgerservicenummer}</td>
		</tr>
		<tr title="Het belastingjaar waarop de melding betrekking heeft.">
			<td align="left" width="257"><b><spring:message code="title.belastingJaar" /></b></td>
			<td align="left" width="421">${melding.betrokkene.belastingJaar}</td>
		</tr>
		<tr title="MA01   terugmelding
MA02   bezwaar AIG
MA03   bezwaar tegen aanslag IH
MA04   bezwaar BJL
MA05   bezwaar defenitieve aanslag
MA06   intrekking terugmelding
MA07   verzoek vermindering AIG
MA08   verzoek vermindering IB
MA09   ambtshalve vermindering
MA10   beroep tegen uitspraak op bezwaar tegen AIG
MA11   hoger beroep tegen uitspraak op beroep tegen AIG
MA12   beroep tegen uitspraak op bezwaar tegen aanslag IH
MA13   hoger beroep tegen de uitspraak op beroep (VI)
MA14   hoger beroep tegen de uitspraak op beroep BJL
MA15   beroep tegen uitspraak op bezwaar tegen BJL
MA16   hoger beroep (cassatie) tegen de uitspraak op hoger beroep AIG
MA17   hoger beroep (cassatie) tegen de uitspraak op hoger beroep IB
MA18   hoger beroep (cassatie) tegen de uitspraak op hoger beroep BJL
MA19   eigen initiatief van de belastingdienst om het AIG aan te passen (bv boekenonderzoek)
MA20   verzoek navordering IB
MA21    eigen initiatief van de belastingdienst om de aaslag (en het VI) aan te passen (bv boekenonderzoek)
MA22   eigen initiatief van de belastingdienst om het BJL aan te passen (bv boekenonderzoek)
MA23   ambtshalve navordering
MA24   intrekking bezwaar AIG
MA25   intrekking beroep AIG
MA26    intrekking hoger beroep AIG
MA27   intrekking hoger beroep (cassatie) AIG
MA28   intrekking bezwaar BJL
MA29   intrekking beroep BJL
MA30   intrekking hoger beroep BJL
MA31   intrekking hoger beroep (cassatie) BJL
MA32   intrekking bezwaar IB
MA33   intrekking beroep IB
MA34   intrekking hoger beroep IB
MA35    intrekking hoger beroep (cassatie) IB 
		">
			<td align="left" width="257"><b><spring:message code="title.soort.melding" /></b></td>
			<td align="left" width="421">${melding.soortMelding}<!--  rloman: dit knalt nu nog! --></td>
		</tr>
		<tr title="Geeft aan in welke fase van de afhandeling de melding zich bevind.
			STATUS = STM01 nog te beoordelen, STM02 beoordeling heeft geleid tot registratie aanleiding, STM03 beoordeling heeft geleid tot handmatige afhandeling, STM04 beoordeling heeft geleid niet verwerkbaar, STM05 wacht op starten nieuw onderzoek, STM99 overig">
			<td align="left" width="257"><b><spring:message code="title.status" /></b></td>
			<td align="left" width="421">${melding.status}</td>
		</tr>
		<tr title="Kenmerk dat het bericht uniek maakt in de administratie van derden.">
			<td align="left" width="257"><b><spring:message code="title.berichtkenmerk.derden" /></b></td>
			<td align="left" width="421">${melding.berichtkenmerkDerden}</td>
		</tr>
		<tr title="Het bedrag uit het oorspronkelijke bericht.">
			<td align="left" width="257"><b><spring:message code="title.waarde" /></b></td>
			<td align="left" width="421">&euro;&nbsp;${melding.waarde}</td>
		</tr>
		<tr title="De dag waarop de gebeurtenis heeft plaatsgevonden.">
			<td align="left" width="257"><b><spring:message code="title.datum.plaatsgevonden" /></b></td>
			<td align="left" width="421">${melding.datumPlaatsGevonden}</td>
		</tr>
		<tr title="Het tijdstip waarop de melding in AIG is geregistreerd.">
			<td align="left" width="257"><b><spring:message code="label.datumtijd.registratie.aig" /></b></td>
			<td align="left" width="421">${melding.datumTijdRegistratie}</td>
		</tr>
		<tr>
			<td align="left" width="257"><b><spring:message code="title.verkorte.meldingnaam" /></b></td>
			<td align="left" width="421">${melding.verkorteMeldingNaam}</td>
		</tr>
		<tr title="Het proces dat de melding heeft voortgebracht.">
			<td align="left" width="257"><b><spring:message code="title.fiscale.bron" /></b></td>
			<td align="left" title=" (IB of IB-signaal of BJL of AIG)" width="421">${melding.fiscaleBron}</td>
		</tr>
	</tbody>
</table>

</fieldset>
