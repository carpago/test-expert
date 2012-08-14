<fieldset title="Gegevens van het onderzoek."><legend align="left"><spring:message code="title.details.onderzoek" /></legend>
<table style="border: 0; cellpadding: 4; cellspacing: 3" width="533">
	<thead>
		<tr>
			<th title="Datum waarop het onderzoek gestart is." align="left" width="262"><b><spring:message code="title.start" /></b></th>
			<td width="148">${onderzoekInstance.datumIngang}</td>
		</tr>
		<tr>
			<th title="Datum waarop het authentiek inkomen gegeven uit het onderzoek komt." align="left" width="262"><b><spring:message code="label.einde" /></b></th>
			<td width="148">${onderzoekInstance.datumEinde}</td>
		</tr>
		<tr>
			<th title="Keten waarin het onderzoek plaatsvindt." align="left" width="262"><b><spring:message code="title.keten" /></b></th>
			<td width="268">${onderzoekInstance.ketenVanOnderzoek}</td>
		</tr>
		<tr>
			<th title="" align="left" width="262"><b><spring:message code="title.belastingJaar"</b></th>
			<td width="148">${onderzoekInstance.betrokkene.belastingJaar}</td>
		</tr>
	</thead>
</table>
</fieldset>