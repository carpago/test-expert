<%@ include file="/WEB-INF/jsp/layout/include.jsp"%>

<html>
<head>
<script src="js/FOL3/foldoutTemplateOB.js" type="text/javascript"></script>
<script src="js/webstraat/webstraat.js" type="text/javascript"></script>
<script src="js/webstraat/tabular.js" type="text/javascript"></script>



<script type="text/javascript">
function toonDetails(burgerservicenummer, belastingJaar, berichtkenmerkAig) {
	document.getElementById("burgerservicenummer").value = burgerservicenummer;
	document.getElementById("belastingJaar").value = belastingJaar;
	document.getElementById("berichtkenmerkAig").value = berichtkenmerkAig;

	document.forms["actie"].submit();
}
</script>

<title><spring:message
		code="title.beoordelen.melding.selecteren" />
</title>
<meta name="layout" content="main" />
</head>
<body>
	<h4>
		<spring:message code="title.beoordelen.melding.selecteren" />
	</h4>
	<fieldset>
		<legend align="left">
			<spring:message code="title.selectiecriteria" />
		</legend>
		<form:form commandName="melding" action="list.html"
			method="POST">
			<table border="0" cellpadding="4" cellspacing="3">
				<tbody>
					<tr
						title="BSN: Het nummer waaronder de natuurlijk persoon is ingeschreven bij de gemeente. Sofinummer: het nummer waaronder de natuurlijk persoon is geregistreerd bij de belastingdienst.">
						<td align="left" width="136"><b><spring:message
									code="label.burgerservicenummer" /> </b>
						</td>
						<td align="left" width="171"><input type="text"
							name="betrokkene.burgerservicenummer" size="20">
						</td>
					</tr>
					<tr title="Het belastingjaar waarop de melding betrekking heeft.">
						<td align="left" width="47"><b><spring:message
									code="label.belastingJaar" /> </b>
						</td>
						<td align="left" width="262"><input type="text"
							name="betrokkene.belastingJaar" size="20" value="2008">
						</td>
					</tr>
					<tr
						title="Kenmerk dat het bericht uniek maakt bij de administratie van derden. Maakt uniek bij derden administratie dus hoeft niet uniek te zijn binnen onze administratie.">
						<td align="left" width="171"><b><spring:message
									code="title.berichtkenmerk.derden" /> </b>
						</td>
						<td align="left" width="262"><input type="text"
							name="berichtkenmerkDerden" size="32""></td>
					</tr>
					<tr
						title="Geeft aan in welke fase van de afhandeling de melding zich bevind.">
						<td align="left" width="171"><b><spring:message
									code="title.status" /> </b>
						</td>
						<td align="left" width="262"><select name="status">
								<option value="STM03">
									<spring:message code="title.beoordeling.melding.handmatig" />
								</option>
								<option value="STM05">
									<spring:message
										code="title.beoordeling.melding.wachten.onderzoek" />
								</option>
								<option value=""></option>
						</select>
						</td>
					</tr>
					<tr
						title="Pas na klikken op *Zoeken* verschijnt onderstaande lijst">
						<td><input type="submit" name="Zoeken" value="Zoeken">
						</td>
					</tr>
				</tbody>
			</table>
		</form:form>
	</fieldset>
	<div id="toonLeegResultaat" style="display: inline">
		<fieldset>
			<legend align="left" title="Aantal meldingen in de lijst.">
				<spring:message code="title.aanwezige.meldingen" />
				: ${meldingInstanceList.size}
			</legend>
			<table border="0" cellpadding="4" cellspacing="3" width="825">
				<thead>
					<tr>
						<td title="De dag waarop de gebeurtenis heeft plaatsgevonden."><b><spring:message
									code="title.datum.plaatsgevonden" /> </b>
						</td>
						<td align="left"
							title="BSN: Het nummer waaronder de natuurlijk persoon is ingeschreven bij de gemeente
		 Sofinummer: het nummer waaronder de natuurlijk persoon is geregistreerd bij de belastingdienst."><b><spring:message
									code="label.burgerservicenummer" /> </b>
						</td>
						<td align="left"
							title="Het belastingjaar waarop de melding betrekking heeft"><b><spring:message
									code="label.belastingJaar" /> </b>
						</td>
						<td align="left"><b><spring:message
									code="title.soort.melding" /> </b>
						</td>
					</tr>
				</thead>
			</table>
		</fieldset>
	</div>
	<div id="toonResultaat" style="display: block">
		<fieldset>
			<legend align="left" title="Aantal meldingen in de lijst.">
				<spring:message code="title.aanwezige.meldingen" />
				: ${meldingInstanceList.size}
			</legend>
			<table border="0" cellpadding="4" cellspacing="3" width="825">
				<thead>
					<tr>
						<td title="De dag waarop de gebeurtenis heeft plaatsgevonden."><b><spring:message
									code="title.datum.plaatsgevonden" /> </b>
						</td>
						<td align="left"
							title="BSN: Het nummer waaronder de natuurlijk persoon is ingeschreven bij de gemeente
	 Sofinummer: het nummer waaronder de natuurlijk persoon is geregistreerd bij de belastingdienst."><b><spring:message
									code="label.burgerservicenummer" /> </b>
						</td>
						<td align="left"
							title="Het belastingjaar waarop de melding betrekking heeft"><b><spring:message
									code="label.belastingJaar" /> </b>
						</td>
						<td align="left"><b><spring:message
									code="title.soort.melding" /> </b>
						</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${meldingInstanceList}" var="melding">
						<tr id="rij1" class="selectie"
							onclick="toonDetails('${melding.betrokkene.burgerservicenummer}', '${melding.betrokkene.belastingJaar}', '${melding.berichtkenmerkAig}');">
							<td>${melding.datumPlaatsGevonden}</td>
							<td>${melding.betrokkene.burgerservicenummer}</td>
							<td>${melding.betrokkene.belastingJaar}</td>
							<td>${melding.status}</td>
							<%--klopt deze property rloman ??? --%>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</fieldset>
	</div>
	<form:form name="actie" action="show.html" method="post">
		<input type="hidden" id="burgerservicenummer"
			name="betrokkene.burgerservicenummer" />
		<input type="hidden" id="belastingJaar"
			name="betrokkene.belastingJaar" />
		<input type="hidden" id="berichtkenmerkAig" name="berichtkenmerkAig" />
	</form:form>
</body>
</html>