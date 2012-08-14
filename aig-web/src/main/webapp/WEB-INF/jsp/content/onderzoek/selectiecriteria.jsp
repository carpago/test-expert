<fieldset>
	<legend align="left">
		<spring:message code="title.selectiecriteria" />
	</legend>
	<table border="0" cellpadding="4" cellspacing="3">
		<tbody>
			<tr>
				<td align="left" width="141"
					title="BSN: Het nummer waaronder de natuurlijk persoon is ingeschreven bij de gemeente. Sofinummer: het nummer waaronder de natuurlijk persoon is geregistreerd bij de belastingdienst."><b><spring:message code="label.burgerservicenummer" /></b>
				</td>
				<td align="left" width="104"
					title="BSN: Het nummer waaronder de natuurlijk persoon is ingeschreven bij de gemeente. Sofinummer: het nummer waaronder de natuurlijk persoon is geregistreerd bij de belastingdienst.">${onderzoekInstance.betrokkene.burgerservicenummer}</td>
				<td align="left" width="141"
					title="Het belastingjaar waarop de melding betrekking heeft."><b><spring:message code="label.belastingJaar" /></b>
				</td>
				<td title="Het belastingjaar waarop de melding betrekking heeft."
					align="left" width="104">${onderzoekInstance.betrokkene.belastingJaars}</td>
			</tr>
		</tbody>
	</table>
</fieldset>