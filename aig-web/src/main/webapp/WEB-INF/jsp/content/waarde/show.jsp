<%@ include file="/WEB-INF/jsp/layout/include.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />
<title>Toon AIG-waarde</title>
</head>
<body onLoad="document.forms.waarde.terugKnop.focus();">


	<div class="content-header">
		<b style="color: white;"><fmt:message key="label.header1" />
			&nbsp; - &nbsp; <fmt:message key="label.header2" />
		</b>
	</div>

	<c:if test="${empty requestScope.opdracht}">
		<div class="padding1">
			<div id="tabular1">
				<fieldset>
					<legend>Selectiecriteria</legend>
					<br />
					<table>
						<tr>
							<th width="25%"
								title="<spring:message code="title.burgerservicenummer" />">
								<spring:message code="label.burgerservicenummer" /></th>
							<td width="33%"
								title="<spring:message code="title.burgerservicenummer" />">
								<brs:formatSofinummer
									tekst="${waardeInstance.burgerservicenummer}" /></td>
							<th title="Belastingjaar" width="19%">Jaar</th>
							<td title="Belastingjaar" width="20%">${waardeInstance.belastingJaar}</td>
						</tr>
					</table>
				</fieldset>
			</div>
		</div>
	</c:if>

	<c:if test="${!empty requestScope.opdracht}">
		<div class="padding1">
			<div id="tabular1">
				<fieldset>
					<legend>Opdrachtgegevens</legend>
					<br />
					<table>
						<tr>
							<th width="25%"
								title="<spring:message code="title.burgerservicenummer" />">
								<spring:message code="label.burgerservicenummer" /></th>
							<td width="33%"
								title="<spring:message code="title.burgerservicenummer" />"><brs:formatSofinummer
									tekst="${requestScope.opdracht.parameterLijst.BRI_BSN}" />
							</td>
							<th title="Belastingjaar">Jaar</th>
							<td>${requestScope.opdracht.parameterLijst.BRI_BELASTINGJAAR}</td>
						</tr>
						<tr>
							<th>Naam</th>
							<td colspan="3" title="${requestScope.opdracht.naamKlant}">
								<brs:formatTekstToMax tekst="${requestScope.opdracht.naamKlant}"
									maxLengte="30" /></td>
						</tr>
					</table>
				</fieldset>
			</div>
		</div>
	</c:if>

	<table width="100%">
		<tr>
			<td><br />
			<br />
				<h4 class="form-header">Raadpleeg AIG-Waarde</h4>
				<div class="padding1">
					<div id="tabular1">
						<fieldset>
							<legend>Details van AIG-Waarde</legend>
							<br />
							<div class="body">
								<table id="contentTable">
									<tbody>
										<tr class="prop"
											title='<spring:message code="title.AIG-Waarde" />'>
											<th valign="top" id="contentCell" class="name" width="43%"><spring:message
													code="label.AIG-Waarde" />
											</th>
											<td valign="top" id="contentCell" class="value" width="489">&euro;&nbsp;<fmt:formatNumber
													value="${waardeInstance.aigWaarde}"
													pattern="###,###,###,###" />
											</td>
										</tr>
										<tr>
											<th>&nbsp;</th>
											<td></td>
										</tr>
										<tr class="prop"
											title='<spring:message code="title.registratieDatum.aig" />'>
											<th valign="top" id="contentCell" class="name" width="43%"><spring:message
													code="label.registratieDatum.aig" />
											</th>
											<td valign="top" id="contentCell" class="value" width="527"><fmt:formatDate
													pattern="dd-MM-yyyy HH:mm:ss"
													value="${waardeInstance.datumtijdRegistratie}" />
											</td>
										</tr>
										<tr class="prop"
											title='<spring:message code="title.periode.geldigheid.aig" />'>
											<th valign="top" id="contentCell" class="name" width="367"><spring:message
													code="label.periode" />
											</th>
											<td valign="top" id="contentCell" class="value" width="489">
												van <fmt:formatDate pattern="dd-MM-yyyy"
													value="${waardeInstance.datumBeginGeldigheid}" />
												&nbsp;tot&nbsp; <c:if
													test="${!empty waardeInstance.datumEindGeldigheid}">
													<fmt:formatDate pattern="dd-MM-yyyy"
														value="${waardeInstance.datumEindGeldigheid}" />
												</c:if> <c:if test="${empty waardeInstance.datumEindGeldigheid}">
		                        	einde belastingjaar
		                        </c:if></td>
										</tr>
										<tr class="prop"
											title='<spring:message code="title.vervalDatum.aig" />'>
											<th valign="top" id="contentCell" class="name" width="43%"><spring:message
													code="label.vervalDatum" />
											</th>
											<td valign="top" id="contentCell" class="value" width="527"><fmt:formatDate
													pattern="dd-MM-yyyy"
													value="${waardeInstance.datumVervallen}" />
											</td>
										</tr>
										<tr class="prop"
											title='<spring:message code="title.grondslag" />'>
											<th valign="top" id="contentCell" class="name" width="43%"><spring:message
													code="label.grondslag" />
											</th>
											<td valign="top" id="contentCell" class="value" width="527">
												<c:if test="${(waardeInstance.raakvlak != 'LEEG')}">
                        		${waardeInstance.raakvlak} -
                        	</c:if> ${waardeInstance.codeGrondslag}</td>
										</tr>
										<tr class="prop"
											title='<spring:message code="title.aardbepaling" />'>
											<th valign="top" id="contentCell" class="name" width="43%"><spring:message
													code="label.aardbepaling" />
											</th>
											<td valign="top" id="contentCell" class="value" width="527">${waardeInstance.codeAardBronbepaling}</td>
										</tr>
									</tbody>
								</table>
							</div>
						</fieldset>
					</div>
				</div></td>
		</tr>
	</table>
	<br />
	<jsp:include page="gegevensonderzoek.jsp />"

	<fieldset class="buttons">
		<span class="right"> <form:form commandName="waarde"
				method="post" action="list.html">
				<input type="hidden" name="belastingJaar"
					style="visibility: hidden;" value="${waardeInstance.belastingJaar}" />
				<input type="hidden" name="burgerservicenummer"
					style="visibility: hidden;"
					value="${waardeInstance.burgerservicenummer}" />
				<input type="submit" id="terugKnop"
					value="<spring:message code="knop.terug"/>" />
			</form:form> </span>
	</fieldset>
	<h5 align="right" style="font-size: 7pt" title="Schermnummer">AIG_UC01_S1</h5>
</body>
</html>
