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
				<td id="aigWaarde1.datumIngangDatumEinde" style="vertical-align: top" width="148">${eersteAanleiding.aigWaarde1.datumIngang} &nbsp; / &nbsp; ${eersteAanleiding.aigWaarde1.datumEinde }</td>
				<td id="aigWaarde1.datumTijdGeregistreerd" width="99">${eersteAanleiding.aigWaarde1.datumTijdGeregistreerd}</td>
				<td id="aigWaarde1.waarde" width="268">${eersteAanleiding.aigWaarde1.waarde}</td>
				<td id="aigWaarde1.grondslagCode" width="268">${eersteAanleiding.aigWaarde1.grondslagCode}</td>
				<td id="aigWaarde1.aardBepalingCode" width="268">${eersteAanleiding.aigWaarde1.aardBepalingCode}</td>
			</tr>
		</tbody>
	</table>
</fieldset>