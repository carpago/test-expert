<fieldset><legend align="left"><spring:message code="title.selectiecriteria" /></legend>
<table border="0" cellpadding="4" cellspacing="3">
	<tbody>
		<tr>
			<td align="left" width="141" title="BSN: Het nummer waaronder de natuurlijk persoon is ingeschreven bij de gemeente. Sofinummer: het nummer waaronder de natuurlijk persoon is geregistreerd bij de belastingdienst."><b><spring:message code="label.burgerservicenummer" /></b></td>
			<td align="left" width="104" title="BSN: Het nummer waaronder de natuurlijk persoon is ingeschreven bij de gemeente. Sofinummer: het nummer waaronder de natuurlijk persoon is geregistreerd bij de belastingdienst.">${melding.betrokkene.burgerservicenummer}</td>
			<td align="left" width="184" title="Geeft aan in welke fase van de afhandeling de melding zich bevind."><b><spring:message code="title.status" /></b></td>
			<td align="left" width="368" title="Geeft aan in welke fase van de afhandeling de melding zich bevind.">${melding.status}</td> <!-- rloman:klopt dit ?? -->
			
		</tr>
		<tr>
			<td align="left" width="141" title="Het belastingjaar waarop de melding betrekking heeft."><b><spring:message code="label.belastingJaar" /></b></td><td title="Het belastingjaar waarop de melding betrekking heeft." align="left" width="104">${melding.betrokkene.belastingJaar}</td>
			
			<td align="left" width="184" title="Kenmerk dat het bericht uniek maakt in de administratie van derden."><b><spring:message code="title.berichtkenmerk.derden" /></b></td>
			<td align="left" width="368" title="Kenmerk dat het bericht uniek maakt in de administratie van derden.">${melding.berichtkenmerkDerden}</td>
		</tr>
	</tbody>
</table>
</fieldset>