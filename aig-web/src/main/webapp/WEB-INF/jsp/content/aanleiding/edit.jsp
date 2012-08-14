<%@ include file="/WEB-INF/jsp/layout/include.jsp"%>

<html>
<head>


<%-- rloman: de set tag klopt hier toch ?? --%>
<c:set var="aanleidingInstance" value="${session.aanleidingInstance}" />
<c:set var="aanleidingInstanceList"
	value="${session.aanleidingInstanceList}" />


<script src="js/FOL3/foldoutTemplateOB.js" type="text/javascript"></script>
<script src="js/webstraat/webstraat.js" type="text/javascript"></script>
<script src="js/webstraat/tabular.js" type="text/javascript"></script>
<script type="text/javascript">
	function terug() {
		document.forms["selecterenMeldingForm"].submit();
	}
	function beeindigen() {
		document.forms["beeindig_form"].submit();
	}
</script>
<title><spring:message
		code="title.behandelen.aanleiding.raadplegen" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="layout" content="main" />

</head>
<form:form
	<body>
	<h4>
		<spring:message code="title.behandelen.aanleiding.raadplegen" />
	</h4>

	<jsp:include page="selectiecriteria" />
	<jsp:include page="basisdata.jsp" />


	<%-- hier include ik nu de voor dit scherm (edit.jsp) specifieke zaken. Lijkt mooi hergebruik van jsp mogelijk te maken.
	Schermindeling wellicht iets anders dan scherm/detailontwerp. --%>

	<form:form name="beeindig_form" "action="aanleiding/beeindig.html">
	
	<%-- post de key. rloman:klopt dit ? --%>
	<input type="hidden" name="melding.betrokkene.belastingJaar" value="${aanleidingInstance.melding.betrokkene.belastingJaar}" />
	<input type="hidden" name="melding.betrokkene.burgerservicenummer" value="${aanleidingInstance.melding.betrokkene.burgerservicenummer}" />
	<input type="hidden" name="melding.berichtkenmerkAig" value="${aanleidingInstance.melding.berichtkenmerkAig}" />
	
	<table border="0" cellpadding="4" cellspacing="3">
		<tbody>

			<tr title="De reden van de beëindiging van de aanleiding.">
				<td align="left" width="257" valign="top"><b><spring:message
							code="title.aanleiding.reden.einde" />
				</b></td>
				<td align="left" width="421"><input type="radio"
					name="redenEinde" value="delta">
				<spring:message code="title.aanleiding.nieuw.aig.delta" /><br /> <input
					type="radio" name="redenEinde" value="intrekking">
				<spring:message code="title.aanleiding.intrekking" /><br />
					<div style="vertical-align: text-top">
						<input type="radio" name="redenEinde" value="anders">
						<spring:message code="title.aanleiding.anders" />
						<textarea name="redenEindeAnders" cols="40" rows="3" style="vertical-align: text-top;">
							<spring:message code="title.aanleiding.vul.reden" />
							</spring:message>
						</textarea>
					</div>
				</td>
			</tr>
		</tbody>
	</table>

	<b style="color: red;">Betrokken en actueel AIG verschillen van
		elkaar.</b>
	<br /> Be&euml;indig deze aanleiding met het actuele AIG:
	<input type="radio" name="beeindigDezeAanleidingMetActueleAig" value="true">Ja
	<input type="radio" name="beeindigDezeAanleidingMetActueleAig" value="false">Nee


</form:form>

	<%-- einde van specifiek voor edit.jsp --%>

	<jsp:include page="gegevenslopendonderzoek.jsp" />
	<jsp:include page="aanleidingen_bij_het_onderzoek.jsp" />
	<jsp:include page="betrokken_aig.jsp" />

<div align="right">
		<table border="0">
			<tbody>
				<tr>
					<td><input type="button" onclick="terug();"
						value="Annuleren"> <input onclick="beeindingen();"
						type="button" name="beeindigen" value="Be&euml;indigen"
						title="Be&euml;indig de aanleiding."></td>
				</tr>
				<tr style="font-size: x-small" align="right">
					<td>scherm-id: UC04-S3</td>
				</tr>
			</tbody>
		</table>
	</div>

</body>
</html>