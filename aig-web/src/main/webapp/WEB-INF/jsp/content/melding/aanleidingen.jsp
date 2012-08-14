<fieldset
	title="Gegevens van het onderzoek waarbij de melding gevoegd wordt.">
	<legend align="left">
		<spring:message code="title.aanleidingen.bij.het.onderzoek" />
	</legend>
	<table style="border: 0; cellpadding: 4; cellspacing: 3" width="865">
		<thead>
			<tr>
				<th width="27"></th>
				<th title="Datum waarop het onderzoek gestart is." width="131"
					align="left"><b><spring:message code="title.begin" />
				</b>
				</th>
				<th
					title="De user-id van de medewerker of programma naam die het onderzoek heeft gestart c.q. beëindigd heeft."
					width="135" align="left"><b><spring:message
							code="title.aanleiding.plaatsgevonden" />
				</b>
				</th>
				<th title="Keten waarin het onderzoek plaatsvindt." width="443"
					align="left"><b><spring:message code="title.soort.melding" />
				</b>
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${aanleidingInstanceList}"
				var="aanleiding">

				<tr>
					<td></td>
					<td style="vertical-align: top" width="148">${aanleiding.registratieTijdstipBegin}</td>
					<td width="99">${aanleiding.melding.datumPlaatsGevonden}</td>
					<td width="268">${aanleiding.melding.status}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</fieldset>