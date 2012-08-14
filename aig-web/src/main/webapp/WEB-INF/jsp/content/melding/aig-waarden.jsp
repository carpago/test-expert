<fieldset>
		<legend align="left" title="Historie van AIG voor bsn/belastingjaar.">
			<spring:message code="title.aig.waarden" />
		</legend>
		<table border="0" cellpadding="4" cellspacing="3" width="756">
			<tbody>
				<tr>
					<th align="left"></th>
					<th align="left"
						title="Periode waarin het AIG geldig is (van ? tot ?)"><b><spring:message
								code="label.periode" />
					</b></th>
					<th align="left"
						title="De datum en het tijdstip waarop de AIG waarde geregistreerd is."><b><spring:message
								code="label.datumtijd.registratie" />
					</b></th>
					<th align="right"
						title="De waarde van het Authentiek InkomensGegeven"><b><spring:message
								code="label.AIG-Waarde" />
					</b></th>
					<th align="left" title=""><b><spring:message
								code="label.grondslag" />
					</b>
					</th>
					<th align="left"><b><spring:message
								code="label.aardbepaling" />
					</b>
					</th>
				</tr>
				
				<%--  hier loop voor afdrukken van alle AIG-waarden uit 'waardeInstanceList'  --%>
				
				<c:forEach items="${waardeInstanceList}"  var="waardeInstance" >
				<%-- rloman: nog even uitzoeken hoe ik onderscheid maak tussen actueel en vorige waarden uit onderzoek. --%>
					<tr
						title="Actuele AIG-waarde waarmee deze melding wordt gevoegd bij het getoonde onderzoek.">
						<td align="left">X</td>
						<td align="left">${waarde.datumIngang} / ${waarde.datumEinde}</td>
						<td align="left">${waarde.datumTijdGeregistreerd}</td>
						<td align="right">&euro;${waarde.waarde}</td>
						<td align="left">${waarde.grondslagCode}</td>
						<td align="left">${waarde.aardBepalingCode}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</fieldset>