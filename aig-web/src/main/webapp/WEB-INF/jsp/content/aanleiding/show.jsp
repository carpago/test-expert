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

	function selecterenMelding() {
		document.forms["selecterenMeldingForm"].submit();
	}
	function bewerkenMelding() {
		document.forms["bewerkenMeldingForm"].submit();
	}
</script>
<title><spring:message
		code="title.behandelen.aanleiding.raadplegen" />
</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="layout" content="main" />

</head>
<body>
	<h4>
		<spring:message code="title.behandelen.aanleiding.raadplegen" />
	</h4>

	<jsp:include page="selectiecriteria" />
	<jsp:include page="basisdata.jsp" />
	<jsp:include page="gegevenslopendonderzoek.jsp" />
	<jsp:include page="aanleidingen_bij_het_onderzoek.jsp" />
	<jsp:include page="betrokken_aig.jsp" />

<%-- Dit form hieronder verzorgt de actie als de ambtenaar op "Doorgaan" klikt --%>
	<form name="edit_actie" action="edit.jsp" method="POST">
		<div align="right">
			<table border="0">
				<tbody>
					<tr>
						<td><input type="button" value="Terug"> <input type="submit" name="doorgaan"
							value="Doorgaan"></td>
					</tr>
					<tr style="font-size: x-small">
						<td>scherm-id: UC03-S2</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
	
	<%-- Dit form hieronder verzorgt de actie als een gebruiker op een (alternatieve, andere) aanleiding in het scherm klikt --%>
	<form:form name="actie" action="show.html" method="POST">
		<input type="hidden" id="burgerservicenummer" name="melding.betrokkene.burgerservicenummer" />
		<input type="hidden" id="belastingJaar"			name="melding.betrokkene.belastingJaar" />
		<input type="hidden" id="berichtkenmerkAig" name="melding.berichtkenmerkAig" />
	</form:form>
	
</body>
</html>