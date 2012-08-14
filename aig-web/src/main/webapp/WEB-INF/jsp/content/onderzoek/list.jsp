<%@ include file="/WEB-INF/jsp/layout/include.jsp"%>

<html>
<head>


<script src="js/FOL3/foldoutTemplateOB.js" type="text/javascript"></script>
<script src="js/webstraat/webstraat.js" type="text/javascript"></script>
<script src="js/webstraat/tabular.js" type="text/javascript"></script>
<script type="text/javascript">
	function toonLijst() {
		document.getElementById("toonResultaat").style.display = "inline";
		document.getElementById("toonLeegResultaat").style.display = "none";
	}

	function toonDetails(burgerservicenummer, belastingJaar, datumIngang) {
		document.getElementById("burgerservicenummer").value = burgerservicenummer;
		document.getElementById("belastingJaar").value = belastingJaar;
		document.getElementById("datumIngang").value = datumIngang;

		document.forms["actie"].submit();
	}

	function toonMenu() {
		document.forms["menuForm"].submit();
	}
</script>
<style type="text/css">
tr.melding:hover {
	background-color: yellow;
}
</style>
<title><spring:message code="title.selecteren.onderzoek" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="layout" content="main" />
</head>
<body>
	<h4>
		<spring:message code="title.raadplegen.onderzoek.selecteren=" />
	</h4>
	<fieldset>
		<legend align="left">
			<spring:message code="title.selectiecriteria" />
		</legend>
		<table border="0" cellpadding="4" cellspacing="3">
			<tbody>
				<tr
					title="BSN: Het nummer waaronder de natuurlijk persoon is ingeschreven bij de gemeente. Sofinummer: het nummer waaronder de natuurlijk persoon is geregistreerd bij de belastingdienst.">
					<td align="left" width="136"><b><spring:message
								code="label.burgerservicenummer" /> </b></td>
					<td align="left" width="696"><input type="text"
						name="betrokkene.burgerservicenummer" size="20"></td>
				</tr>
				<tr title="Het belastingjaar waarop het onderzoek betrekking heeft.">
					<td align="left" width="47"><b><spring:message
								code="label.belastingJaar" />
					</b></td>
					<td align="left" width="696"><input type="text"
						name="betrokkene.belastingJaar" size="20"></td>
				</tr>
			</tbody>
		</table>
		<div align="right">
			<table border="0">
				<tbody>
					<tr
						title="Pas na klikken op *Zoeken* verschijnt onderstaande lijst">
						<td><input type="submit" name="Zoeken" value="Zoeken">
						</td>
					</tr>
				</tbody>
			</table>
		</div>

	</fieldset>
	<div id="toonLeegResultaat" style="display: inline">
		<fieldset>
			<legend align="left" title="Aantal onderzoeken in de lijst.">
				<spring:message code="title.aanwezige.onderzoeken" />
				: ${onderzoekInstanceList.size}
			</legend>
			<table style="border: 0; cellpadding: 4; cellspacing: 3" width="865">
				<thead>
					<tr>
						<th title="Datum waarop het onderzoek gestart is." width="131"
							align="left"><b><spring:message code="title.start" />
						</b></th>
						<th
							title="Datum waarop het authentiek inkomen gegeven uit het onderzoek komt."
							width="131" align="left"><b><spring:message
									code="label.einde" />
						</b></th>
						<th title="Het belastingjaar waarop het AIG betrekking heeft."
							width="131" align="left"><b><spring:message
									code="label.belastingJaar"
						</b></th>
						<th title="Keten waarin het onderzoek plaatsvindt." width="443"
							align="left"><b><spring:message code="title.keten" />
						</b></th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</fieldset>
	</div>
	<div id="toonResultaat" style="display: none">
		<fieldset>
			<legend align="left" title="Aantal onderzoeken in de lijst.">
				<spring:message code="title.aanwezige.onderzoeken" />
				: ${onderzoekInstanceList.size}
			</legend>
			<table style="border: 0; cellpadding: 4; cellspacing: 3" width="865">
				<thead>
					<tr>
						<th title="Datum waarop het onderzoek gestart is." width="131"
							align="left"><b><spring:message code="title.start" />
						</b></th>
						<th
							title="Datum waarop het authentiek inkomen gegeven uit het onderzoek komt."
							width="131" align="left"><b><spring:message
									code="label.einde" />
						</b></th>
						<th title="Het belastingjaar waarop het AIG betrekking heeft."
							width="131" align="left"><b><spring:message
									code="label.belastingJaar"
						</b></th>
						<th title="Keten waarin het onderzoek plaatsvindt." width="443"
							align="left"><b><spring:message code="title.keten" />
						</b></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${onderzoekInstanceList}" var="onderzoekInstance">
						<tr id="rij1" class="selectie"
							onclick="toonDetails('${onderzoekInstance.betrokkene.burgerservicenummer}', '${onderzoekInstance.betrokkene.belastingJaar}', '${onderzoekInstance.datumIngang}');">
							<td width="148">${onderzoekInstance.datumIngang}</td>
							<td width="148">${onderzoekInstance.datumEinde}</td>
							<td width="148">${onderzoekInstance.betrokkene.belastingJaar}</td>
							<td width="268">${onderzoekInstance.ketenVanOnderzoek}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</fieldset>
	</div>
	<div align="right">
		<table border="0">
			<tbody>
				<tr>
					<!--			<td align="right"><input type="submit" onclick="toonMenu();"-->
					<!--				name="Menu" value="Menu"></td>-->
				</tr>
				<tr style="font-size: x-small">
					<td>scherm-id: UC05-S1</td>
				</tr>
			</tbody>
		</table>
	</div>

	<form:form name="actie" action="show.html" method="post">
		<input type="hidden" id="burgerservicenummer" name="betrokkene.burgerservicenummer" />
		<input type="hidden" id="belastingJaar" name="betrokkene.belastingJaar" />
		<input type="hidden" id="datumIngang" name="datumIngang" />
	</form:form>
</body>
</html>