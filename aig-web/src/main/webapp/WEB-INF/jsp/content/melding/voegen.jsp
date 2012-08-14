<%@ include file="/WEB-INF/jsp/layout/include.jsp"%>

<html>
<head>
<script src="js/FOL3/foldoutTemplateOB.js" type="text/javascript"></script>
<script src="js/webstraat/webstraat.js" type="text/javascript"></script>
<script src="js/webstraat/tabular.js" type="text/javascript"></script>
<script type="text/javascript">
	function opslaan() {
		document.forms["opslaanForm"].submit();
	}
	function annuleren() {
		document.forms["annulerenForm"].submit();
		return false;
	}
</script>
<title><spring:message code="title.beoordelen.melding.bewerken" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="layout" content="main" />
</head>
<body>
<h4><spring:message code="title.beoordelen.melding.voegen" /></h4>


<jsp:include page="selectiecriteria.jsp" />

<jsp:include page="basisdata.jsp" />

<jsp:include page="gegevenslopendonderzoek.jsp" />

<jsp:include page="aig-waarden.jsp" />

<div align="right">
<table border="0">
	<tbody>
		<tr>
			<td>
				<form:form action="melding/actie/save.html" method="post"
				id="opslaanForm">

				<form:hidden path="betrokkene.burgerservicenummer" value="${melding.betrokkene.burgerservicenummer}" />
				<form:hidden path="betrokkene.belastingJaar" value="${melding.betrokkene.belastingJaar}" />
				<form:hidden path="berichtkenmerkAig" value="${melding.berichtkenmerkAig}" />
				<form:hidden path="oorspronkelijkeMeldingNaam" value="${melding.oorspronkelijkeMeldingNaam}" />
				<form:hidden path="actie" value="voegen" />
				
				<input type="submit" onclick="annuleren();" name="annuleren" value="Annuleren"
				title="Terug naar raadplegen van de melding"> 
				
				<input type="submit" name="opslaan" value="Voegen" title="Opslaan melding / Afsluiten beoordeling." />
			</form:form></td>
		</tr>
		<tr style="font-size: x-small">
			<td>scherm-id: UC03-S3</td>
		</tr>
	</tbody>
</table>
</div>


<form action="03 Raadplegen melding.html" method="post" id="annulerenForm" /></form>
</body>
</html>