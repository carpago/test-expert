<%@ include file="/WEB-INF/jsp/layout/include.jsp"%>

<html>
<head>
<script src="js/FOL3/foldoutTemplateOB.js" type="text/javascript"></script>
<script src="js/webstraat/webstraat.js" type="text/javascript"></script>
<script src="js/webstraat/tabular.js" type="text/javascript"></script>

<title>:: Selecteren AIG ::</title>
<meta name="layout" content="main" />

<script language="JavaScript">
	function toonDetails(jaar, bsn, datumIngang, datumTijdGeregistreerd) {
		document.getElementById("belastingJaar1").value = jaar;
		document.getElementById("burgerservicenummer1").value = bsn;
		document.getElementById("datumIngang").value = datumIngang;
		document.getElementById("datumTijdGeregistreerd").value = datumTijdGeregistreerd;
		document.forms["submitForm"].submit();
	}

	function bepaalHoogteVanDeLijst() {
		var hoogte = 100;
		if (navigator.appName=="Microsoft Internet Explorer") {
			hoogte = document.body.clientHeight - 300;
		}
		if (navigator.appName=="Netscape") {
			hoogte = window.innerHeight - 300;
		}
		/* minimale grootte van de lijst instellen */
		if (hoogte <= 100) {
			hoogte = 100;
			}
		return hoogte;
	}
</script>

</head>
<body onLoad="document.forms.waarde.burgerservicenummer.focus();">
<div class="content-header">
	<b style="color: white;"><fmt:message key="label.header1" /> &nbsp; - &nbsp; <fmt:message key="label.header2" /></b>
</div>	
<c:if test="${!empty requestScope.opdracht}">
<div class="padding1">
<div id="tabular1">
<fieldset><legend>Opdrachtgegevens</legend>
<br/>
<table>
<tr>
	<th width="25%" title="<spring:message code="title.burgerservicenummer" />"><spring:message code="label.burgerservicenummer" /></th>
	<td width="33%"><brs:formatSofinummer tekst="${requestScope.opdracht.parameterLijst.BRI_BSN}"/></td>
	<th title="Belastingjaar">Jaar</th>
	<td >${requestScope.opdracht.parameterLijst.BRI_BELASTINGJAAR}</td>
	
</tr>
<tr>
	<th>Naam</th><td colspan="3" title="${requestScope.opdracht.naamKlant}"><brs:formatTekstToMax tekst="${requestScope.opdracht.naamKlant}" maxLengte="30" /></td>
</tr>
</table>
</fieldset>
</div>
</div>
</c:if>
<br />
<table width="100%">
	<tr>
		<td>
			<h4 class="form-header">Selecteer AIG-Waarde</h4>
		</td>
	</tr>
</table>
<c:if test="${empty requestScope.opdracht}">
	<table width="100%">
		<tr>
			<td>
			<form:form commandName="waarde" method="post" action="list.html">
				<div class="padding1">
				<div id="tabular1">
				<fieldset><legend>Selectie criteria</legend> <br />
				<table class="zoekForm">
					<tr class="prop" title=<spring:message code="title.burgerservicenummer" />>
						<th width="30%" valign="top" class="name">
							<label for="burgerservicenummer"><spring:message code="label.burgerservicenummer" /></label>
						</th>
						<td width="20%"  valign="top" style="text-align: right">
							<input type="text" id="burgerservicenummer" name="betrokkene.burgerservicenummer" maxlength="11" class="inputTijdvak" value="${waarde.betrokkene.burgerservicenummer}" tabindex="101" />
						</td>
						<td width="50%" ><form:errors path="betrokkene.burgerservicenummer" />&nbsp;</td>
					</tr>
					<tr class="prop" title=<spring:message code="title.belastingJaar" />>
						<th valign="top" class="name">
							<label for="belastingJaar"><spring:message code="label.belastingJaar" /></label>
						</th>
						<td valign="top" style="text-align: right">
							<input value="${waarde.betrokkene.belastingJaar}" type="text" id="belastingJaar"
							name="betrokkene.belastingJaar" maxlength="4" class="inputTijdvak" tabindex="102" />
							
						</td>
						<td><form:errors path="betrokkene.belastingJaar" />&nbsp;</td>
					</tr>
					<tr>
						<th>&nbsp;</th>
						<td style="text-align: right"><input class="" type="submit" name="zoekbutton" value="Zoek" tabindex="103" /></td>
						<td>&nbsp;</td>
					</tr>
				</table>
				</fieldset>
				</div>
				</div>
			</form:form></td>
		</tr>
	</table>
</c:if>
<div class="padding1">
<div id="tabular1">
<br />

<fieldset>
	<legend>Aanwezige AIG-Waarden</legend>
	<br />
	<c:if test="${!empty waardeInstanceList}">
		<script language="JavaScript">
			document.write('<div style="overflow:auto; overflow-x:hidden; height:');
			document.write(bepaalHoogteVanDeLijst());
			document.write('px;"/>');
		</script>
		<table width="100%" style="">
			<tr>
				<td>
				<table width="100%" class="highlight-tabular">
					<thead>
						<tr>
							<th colspan="2" title='<spring:message code="title.periode.geldigheid.aig" />'>
								<label title=<spring:message code="title.periode.geldigheid.aig" />>
								<spring:message	code="label.periode" /></label>
							</th>
							<th title='<spring:message code="title.registratieDatum.aig" />'>
								<label title='<spring:message code="title.registratieDatum.aig" />'>
									<spring:message	code="label.registratieDatum.aig" />
								</label>
							</th>
							<th title='<spring:message code="title.AIG-Waarde" />'>
								<label title='<spring:message code="title.AIG-Waarde" />'>
									<spring:message	code="label.AIG-Waarde" />
								</label>
							</th>
							<th style="padding-right: 30px; " title='<spring:message code="title.grondslag" />'>
								<label title='<spring:message code="title.grondslag" />'>
									<spring:message code="label.grondslag" />
								</label>
							</th>
							<th style="padding-right: 30px; " title='<spring:message code="title.aardbepaling" />'>
								<label title='<spring:message code="title.aardbepaling" />'>
									<spring:message code="label.aardbepaling" />
								</label>
							</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${waardeInstanceList}" var="aig">
							<tr class="wit" onclick="toonDetails('${aig.betrokkene.belastingJaar}', '${aig.betrokkene.burgerservicenummer}', '${aig.datumIngang}', '${aig.datumTijdGeregistreerd}');" style="cursor: pointer;">
								<td title="Begindatum periode" style="padding-right: 0px;"><fmt:formatDate pattern="dd-MM-yyyy" value="${aig.datumIngang}" /></td>
								<c:if test="${empty aig.datumEinde}">
									<td>/ einde belastingjaar</td>
								</c:if>
								<c:if test="${!empty aig.datumEinde}">
									<td title="Einddatum periode">/ <fmt:formatDate pattern="dd-MM-yyyy" value="${aig.datumEinde}" /></td>
								</c:if>
								<td><fmt:formatDate value="${aig.datumTijdGeregistreerd}" pattern="dd-MM-yyyy" /></td>
								<td style="text-align: right; padding-right: 20px">
									&euro; <fmt:formatNumber value="${aig.waarde}" pattern="###,###,###,###" />
								</td>
								<td title="${aig.grondslagCode}"><brs:formatTekstToMax tekst="${aig.grondslagCode}" maxLengte="15" /></td>
								<td title="${aig.aardBepalingCode}"><brs:formatTekstToMax tekst="${aig.aardBepalingCode}" maxLengte="15" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				</td>
			</tr>
		</table>
		<script language="JavaScript">
			document.write('</div>');
		</script>
	</c:if>
	<c:if test="${waardeInstanceList != null && empty waardeInstanceList}">
		<table width="100%">
			<tr>
				<td><br />Geen AIG-Waarden gevonden voor combinatie Sofinummer/BSN en Jaar.<br /></td>
			</tr>
		</table>
	</c:if>
</fieldset>
<h5 align="right" style="font-size: 7pt" title="Schermnummer">AIG_UC01_S2</h5>
</div>
</div>

<form action="show.html" method="post" id="submitForm"><input type="hidden" id="belastingJaar1" name="betrokkene.belastingJaar" />
<input type="hidden" id="burgerservicenummer1" name="betrokkene.burgerservicenummer" /> <input type="hidden" id="datumIngang" name="datumIngang" />
<input type="hidden" id="datumTijdGeregistreerd" name="datumTijdGeregistreerd" /></form>
</body>
</html>
