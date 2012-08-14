<fieldset
		title="Gegevens van het onderzoek waarbij de melding gevoegd wordt.">
		<legend align="left"><spring:message code="title.betrokken.onderzoek" /></legend>
		<table style="border: 0; cellpadding: 4; cellspacing: 3" width="865">
			<thead>
				<tr>
					<th width="27"></th>
					<th title="Datum waarop het onderzoek gestart is." width="131"
						align="left"><b><spring:message code="title.start" /></b></th>
					<th
						title="De user-id van de medewerker of programma naam die het onderzoek heeft gestart c.q. beëindigd heeft."
						width="135" align="left"><b><spring:message code="title.medewerker" /></b></th>
					<th title="Keten waarin het onderzoek plaatsvindt." width="443"
						align="left"><b><spring:message code="title.keten" /></b></th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td></td>
					<td style="vertical-align: top" width="148">${aanleiding.onderzoek.datumIngang}</td>
					<td width="99">${aanleiding.onderzoek.medewerker}</td>
					<td width="268">${aanleiding.onderzoek.ketenVanOnderzoek}</td>
				</tr>
			</tbody>
		</table>
	</fieldset>