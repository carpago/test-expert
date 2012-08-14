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
	
	function toonLijst() {
		document.getElementById("toonResultaat").style.display = "inline";
		document.getElementById("toonLeegResultaat").style.display = "none";
	}
	function toonDetails() {
		document.forms["raadplegenAanleidingForm"].submit();
	}
	function toonMenu() {
		document.forms["menuForm"].submit();
	}
</script>
<title><spring:message code="title.behandelen.aanleiding" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
	<h4>
		<spring:message code="title.behandelen.aanleiding.selecteren" />
	</h4>

	<fieldset>
		<legend align="left">
			<spring:message code="title.selectiecriteria" />
		</legend>
		<form:form commandName="aanleiding" action="aanleiding/list.html"
			method="POST">
			<table border="0" cellpadding="4" cellspacing="3">
				<tbody>
					<tr
						title="BSN: Het nummer waaronder de natuurlijk persoon is ingeschreven bij de gemeente. Sofinummer: het nummer waaronder de natuurlijk persoon is geregistreerd bij de belastingdienst.">
						<td align="left" width="136"><b><spring:message
									code="label.burgerservicenummer" /> </b>
						</td>
						<td align="left" width="171"><input type="text"
							name="melding.betrokkene.burgerservicenummer" size="20">
						</td>
					</tr>
					<tr title="Het belastingjaar waarop de melding betrekking heeft.">
						<td align="left" width="47"><b><spring:message
									code="label.belastingJaar" /> </b>
						</td>
						<td align="left" width="262"><input type="text"
							name="melding.betrokkene.belastingJaar" size="20" value="2008">
						</td>
					</tr>
					<tr
						title="Kenmerk dat het bericht uniek maakt bij de administratie van derden. Maakt uniek bij derden administratie dus hoeft niet uniek te zijn binnen onze administratie.">
						<td align="left" width="171"><b><spring:message
									code="title.berichtkenmerk.derden" /> </b>
						</td>
						<td align="left" width="262"><input type="text"
							name="melding.berichtkenmerkDerden" size="32""></td>
					</tr>
				</tbody>
			</table>
		</form:form>
	</fieldset>


	<div id="toonLeegResultaat" style="display: inline">
		<fieldset>
			<legend align="left"
				title="<spring:message code='title.behandelen.aanleiding' />">
				<spring:message code="title.aanleidingen.aanwezig" />
				${aanleidingInstanceList.size}
			</legend>
			<table border="0" cellpadding="4" cellspacing="3" width="825">
				<thead>
					<tr>
						<td
							title="Datum en tijdstip registratie van de aanleiding in AIG."><b><spring:message
									code="title.aanleiding.begin" /> </b>
						</td>
						<td title="De datum waarop de gebeurtenis heeft plaatsgevonden."><b><spring:message
									code="title.aanleiding.plaatsgevonden" /> </b>
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
	<div id="toonResultaat" style="display: none">
		<fieldset>
			<legend align="left"
				title="<spring:message code='title.behandelen.aanleiding' />">
				<spring:message code="title.aanleidingen.aanwezig" />
				${aanleidingInstanceList.size}
			</legend>
			<table border="0" cellpadding="4" cellspacing="3" width="825">
				<thead>
					<tr>
						<td
							title="Datum en tijdstip registratie van de aanleiding in AIG."><b><spring:message
									code="title.aanleiding.begin" /> </b>
						</td>
						<td title="De datum waarop de gebeurtenis heeft plaatsgevonden."><b><spring:message
									code="title.aanleiding.plaatsgevonden" /> </b>
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

					<c:forEach items="${aanleidingInstanceList}" var="aanleiding">
						<tr id="rij1" class="selectie"
							onclick="toonDetails('${aanleiding.melding.betrokkene.burgerservicenummer}', '${aanleiding.melding.betrokkene.belastingJaar}', '${aanleiding.melding.berichtkenmerkAig}');">
							<td>${aanleiding.registratieTijdstipBegin}</td>
							<td>${aanleiding.melding.datumPlaatsGevonden}</td>
							<td>${aanleiding.melding.betrokkene.burgerservicenummer}</td>
							<td>${aanleiding.melding.betrokkene.belastingJaar}</td>
							<td>${aanleiding.melding.status}</td>
						</tr>
					</c:forEach>

				</tbody>
			</table>
		</fieldset>
	</div>
	<form:form name="actie" action="show.html" method="post">
		<input type="hidden" id="burgerservicenummer" name="melding.betrokkene.burgerservicenummer" />
		<input type="hidden" id="belastingJaar"	name="melding.betrokkene.belastingJaar" />
		<input type="hidden" id="berichtkenmerkAig" name="melding.berichtkenmerkAig" />
	</form:form>
</body>
</html>