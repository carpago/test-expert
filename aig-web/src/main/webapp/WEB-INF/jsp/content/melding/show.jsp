<%@ include file="/WEB-INF/jsp/layout/include.jsp"%>

<html>
<head>
<script src="js/FOL3/foldoutTemplateOB.js" type="text/javascript"></script>
<script src="js/webstraat/webstraat.js" type="text/javascript"></script>
<script src="js/webstraat/tabular.js" type="text/javascript"></script>
<script type="text/javascript">
	function selecterenMelding() {
		document.forms["selecterenMeldingForm"].submit();
	}
	function bewerkenMelding() {
		document.forms["bewerkenMeldingForm"].submit();
	}
</script>
<title><spring:message
		code="title.beoordelen.melding.selecteren" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="layout" content="main" />

</head>
<body>
	<h4>
		<spring:message code="title.beoordelen.melding.raadplegen" />
	</h4>

	<jsp:include page="selectiecriteria" />

	<jsp:include page="basisdata.jsp" />
	
	<jsp:include page="gegevenslopendonderzoek.jsp" />  
	
	<jsp:include page="aanleidingen.jsp" />


	<form:form name="actie" action="actie.html" method="post">
		<input type="hidden" name="betrokkene.burgerservicenummer"
			value="${melding.betrokkene.burgerservicenummer}" />
		<input type="hidden" name="betrokkene.belastingJaar"
			value="${melding.betrokkene.belastingJaar}" />
		<input type="hidden" name="berichtkenmerkAig"
			value="${melding.berichtkenmerkAig}" />
		<fieldset>

			<legend align="left">
				<spring:message code="title.vervolactie" />
			</legend>
			<table style="border: 0; cellpadding: 4; cellspacing: 3">
				<tbody>
				
				<%-- Zie pagina 24 van ucd document. Onderzoek aanwezig disable starten, niet aanwezig disable voegen. --%>
					<tr>
						<td style="vertical-align: top"><b><spring:message
									code="title.kies.actie" /> </b></td>
									
									
									<%-- rloman: eventueel naar c:choose refactoren --%>
						<c:if test="${betrokkene.indicatieInOnderzoek=='J'}" >
							<td
							title="Voeg melding als aanleiding bij een lopend onderzoek of (als disabled) Er is geen onderzoek om bij te voegen."><input
							type="radio" name="actie" value="Voegen"> <spring:message
								code="title.voegen" /></td>
						</c:if>
						<c:if test="${betrokkene.indicatieOnderzoek=='N'}" >
							<td
							title="Voeg melding als aanleiding bij een lopend onderzoek of (als disabled) Er is geen onderzoek om bij te voegen."><input
							type="radio" name="actie" value="Voegen" readonly="true"> <spring:message
								code="title.voegen" /></td>
						</c:if>
						
					</tr>
					<tr>
						<td></td>
						<c:if test="${betrokkene.indicatieOnderzoek=='J'}" >
							<td
							title="Start nieuw onderzoek met deze melding als aanleiding. of (als disabled) Er loopt al een onderzoek."><input
							type="radio" name="actie" value="Starten" readonly="true"> <spring
								message code="title.starten" /></td>
						</c:if>
						<c:if test="${betrokkene.indicatieOnderzoek=='J'}" >
							<td
							title="Start nieuw onderzoek met deze melding als aanleiding. of (als disabled) Er loopt al een onderzoek."><input
							type="radio" name="actie" value="Starten"> <spring
								message code="title.starten" /></td>
						</c:if>
					</tr>
					<tr>
						<td></td>
						<td title="Melding moet later beoordeeld worden."><input
							type="radio" name="actie" value="Wachten"> <spring:message
								code="title.parkeren" /></td>
					<tr>
						<td></td>
						<td title="Melding behoeft geen verdere verwerking."><input
							type="radio" name="actie" value="Stoppen"> <spring:message
								code="title.stopppen" /></td>
					</tr>
				</tbody>
			</table>

		</fieldset>


		<div align="right">
			<table border="0">
				<tbody>
					<tr>
						<td><input type="button" onclick="selecterenMelding();"
							value="Terug"> <input type="submit" name="doorgaan"
							value="Doorgaan">
						</td>
					</tr>
					<tr style="font-size: x-small">
						<td>scherm-id: UC03-S2</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form:form>
</body>
</html>