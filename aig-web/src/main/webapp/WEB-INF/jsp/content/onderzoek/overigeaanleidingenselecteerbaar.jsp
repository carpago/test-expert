<fieldset id="overigeAanleidingenSelecteerbaar"
	style="display: inline; width: 97%;"
	title="Aanleidingen bij het onderzoek.">
	<legend align="left">
		<spring:message code="title.aanleidingen.bij.het.onderzoek" />
	</legend>
	<table border="0" cellpadding="4" cellspacing="3" width="865">
		<thead>
			<tr>
				<td title="Datum en tijdstip registratie van de aanleiding in AIG."><b><spring:message
							code="label.begin" /> </b>
				</td>
				<td title="Datum en tijdstip registratie van de aanleiding in AIG."><b><spring:message
							code="label.einde" /> </b>
				</td>
				<td title="Datum waarop de gebeurtenis heeft plaatsgevonden."><b><spring:message
							code="title.aanleiding.plaatsgevonden" /> </b>
				</td>
				<td align="left"><b><spring:message
							code="title.soort.melding" /> </b>
				</td>
			</tr>
		</thead>
		<tbody title="Geselecteerde aanleiding">

			<%-- de JavaScript methode 'selecteerAanleiding staat in de parent script 'show.jsp'. --%>
			<c:forEach items="${onderzoekInstance.aanleidingList}"
				var="aanleidingInstance">
				<tr id="rij1" bgcolor="#EEEEEE" onclick="selecteerAanleiding(${aanleidingInstance.asJson});">
					<td>${aanleidingInstance.registratieTijdstipBegin}</td>
					<td>${aanleidingInstance.registratieTijdstipEinde}</td>
					<td>${aanleidingInstance.melding.datumPlaatsGevonden}</td>
					<td align="left"
						title="hoger beroep tegen uitspraak op beroep tegen AIG">${aanleidingInstance.melding.status}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</fieldset>