<fieldset
	title="Gegevens van het onderzoek waarbij de melding gevoegd wordt.">
	<legend align="left">
		<spring:message code="title.betrokken.aig" />
	</legend>
	<table style="border: 0; cellpadding: 4; cellspacing: 3" width="865">
		<thead>
			<tr>
				<th width="27"></th>
				<th title="Datum waarop het onderzoek gestart is." width="131"
					align="left"><b><spring:message code="label.periode" />
				</b>
				</th>
				<th title="Datum waarop het onderzoek gestart is." width="131"
					align="left"><b><spring:message
							code="label.datumtijd.registratie" />
				</b>
				</th>
				<th
					title="De user-id van de medewerker of programma naam die het onderzoek heeft gestart c.q. beëindigd heeft."
					width="135" align="left"><b><spring:message
							code="label.AIG-Waarde" />
				</b>
				</th>
				<th title="Keten waarin het onderzoek plaatsvindt." width="443"
					align="left"><b><spring:message code="label.grondslag" />
				</b>
				</th>
				<th title="Keten waarin het onderzoek plaatsvindt." width="443"
					align="left"><b><spring:message code="label.aardbepaling" />
				</b>
				</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td></td>
				<td style="vertical-align: top" width="148">${aanleiding.aigWaarde1.datumIngang} &nbsp; / &nbsp; ${aanleiding.aigWaarde1.datumEinde }</td>
				<td width="99">${aanleiding.aigWaarde1.datumTijdGeregistreerd}</td>
				<td width="268">${aanleiding.aigWaarde1.waarde}</td>
				<td width="268">${aanleiding.aigWaarde1.grondslagCode}</td>
				<td width="268">${aanleiding.aigWaarde1.aardBepalingCode}</td>
			</tr>
		</tbody>
	</table>
</fieldset>