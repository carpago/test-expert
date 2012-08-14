<%@ include file="/WEB-INF/jsp/layout/include.jsp"%>

<html>
<head>
<script src="js/FOL3/foldoutTemplateOB.js" type="text/javascript"></script>
<script src="js/webstraat/webstraat.js" type="text/javascript"></script>
<script src="js/webstraat/tabular.js" type="text/javascript"></script>
<script type="text/javascript">
function annuleren()
{
document.forms["menuForm"].submit();
}
function opslaan()
{
document.forms["registrerenMeldingForm"].submit();

}
</script>
<title><spring:message code="title.registreren.melding" /></title>
<meta name="layout" content="main" />
</head>
<body>
<h4><spring:message code="title.registreren.melding" /></h4>
<form:form commandName="melding" method="post" action="registreer.html">
<fieldset><legend align="left"><spring:message code="title.details.melding" /></legend>
<table border="0" cellpadding="4" cellspacing="3" width="721">
	<tbody>
		<tr title="<spring:message code="title.burgerservicenummer" />">
			<td align="left" width="324"><b><spring:message code="title.burgerservicenummer" /></b></td>
			<td align="left" width="404"><input type="text" name="betrokkene.burgerservicenummer" maxlength="9" size="11"></td>
		</tr>
		<tr title="Invoer: maximaal 4 cijfers">
			<td align="left" width="324"><b><spring:message code="title.belastingJaar" /></b></td>
			<td align="left" width="404"><input type="text" name="betrokkene.belastingJaar"
				maxlength="4" size="6"></td>
		</tr>
		<tr title="De dag waarop de gebeurtenis heeft plaatsgevonden. Invoer: 10 karakters (dd-mm-jjee)">
			<td align="left" width="324"><b><spring:message code="title.datum.plaatsgevonden" /></b></td>
			<td align="left" width="404"><input type="text" name="datumPlaatsGevonden"
				maxlength="10" size="12"></td>
		</tr>
		<tr title="MA01   terugmelding
MA02   bezwaar AIG
MA03   bezwaar tegen aanslag IH
MA04   bezwaar BJL
MA05   bezwaar defenitieve aanslag
MA06   intrekking terugmelding
MA07   verzoek vermindering AIG
MA08   verzoek vermindering IB
MA09   ambtshalve vermindering
MA10   beroep tegen uitspraak op bezwaar tegen AIG
MA11   hoger beroep tegen uitspraak op beroep tegen AIG
MA12   beroep tegen uitspraak op bezwaar tegen aanslag IH
MA13   hoger beroep tegen de uitspraak op beroep (VI)
MA14   hoger beroep tegen de uitspraak op beroep BJL
MA15   beroep tegen uitspraak op bezwaar tegen BJL
MA16   hoger beroep (cassatie) tegen de uitspraak op hoger beroep AIG
MA17   hoger beroep (cassatie) tegen de uitspraak op hoger beroep IB
MA18   hoger beroep (cassatie) tegen de uitspraak op hoger beroep BJL
MA19   eigen initiatief van de belastingdienst om het AIG aan te passen (bv boekenonderzoek)
MA20   verzoek navordering IB
MA21    eigen initiatief van de belastingdienst om de aaslag (en het VI) aan te passen (bv boekenonderzoek)
MA22   eigen initiatief van de belastingdienst om het BJL aan te passen (bv boekenonderzoek)
MA23   ambtshalve navordering
MA24   intrekking bezwaar AIG
MA25   intrekking beroep AIG
MA26    intrekking hoger beroep AIG
MA27   intrekking hoger beroep (cassatie) AIG
MA28   intrekking bezwaar BJL
MA29   intrekking beroep BJL
MA30   intrekking hoger beroep BJL
MA31   intrekking hoger beroep (cassatie) BJL
MA32   intrekking bezwaar IB
MA33   intrekking beroep IB
MA34   intrekking hoger beroep IB
MA35    intrekking hoger beroep (cassatie) IB 
		">
			<td align="left" width="324"><b><spring:message code="title.soort.melding" /></b></td>
			<td align="left" width="404"><select
				name="oorspronkelijkeMeldingnaam">
				
				<%-- lege waarde is 'kies een waarde' dus niet wijzigen hieronder --%>
				<option value="">kies een waarde</option>
				
				<option value="b">terugmelding</option>
				<option value="b">bezwaar AIG</option>
				<option value="b">bezwaar tegen aanslag IH</option>
				<option value="b">bezwaar BJL</option>
				<option value="b">bezwaar defenitieve aanslag</option>
				<option value="b">intrekking terugmelding</option>
				<option value="b">verzoek vermindering AIG</option>
				<option value="b">verzoek vermindering IB</option>
				<option value="b">ambtshalve vermindering</option>
				<option value="b">beroep tegen uitspraak op bezwaar tegen AIG</option>
				<option value="b">hoger beroep tegen uitspraak op beroep tegen AIG</option>
				<option value="b">beroep tegen uitspraak op bezwaar tegen aanslag IH</option>
				<option value="b">hoger beroep tegen de uitspraak op beroep (VI)</option>
				<option value="e">hoger beroep tegen de uitspraak op beroep BJL</option>
			</select></td>
		</tr>
		<tr title="Kenmerk dat het bericht uniek maakt bij de administratie van derden. Invoer: maximaal 32 tekens">
			<td align="left" width="324"><b><spring:message code="title.berichtkenmerk.derden" /></b></td>
			<td align="left" width="404"><input type="text" name="berichtkenmerkDerden"
			 maxlength="32"	size="34"></td>
		</tr>
		<tr  title="Het betwijfelde bedrag uit het oorspronkelijke bericht. Invoer: maximaal 13 cijfers">
			<td align="left" width="324"><b><spring:message code="title.waarde" /></b></td>
			<td align="left" width="404">&euro;<input type="text" name="waarde"
				maxlength="13" size="16"></td>
		</tr>
	</tbody>
</table>
</fieldset>

<table border="0" width="100%">
	<tbody>
		<tr>
			<td align="right">
			</td>
		</tr>
	</tbody>
</table>
<div align="right">
	<table border="0">
		<tbody>
			<tr>
				<td>
				<!--  rloman:  wie heeft dit waarom bedacht ? Onzin!Althans de annnuleren knop.  -->
					<input id="button_annuleren" type="submit" onclick="document.location.href='http://localhost:9080';return false;" name="annulerenKnop" value="Annuleren">
					<input id="button_submit" type="submit" name="opslaanKnop" value="Opslaan">
				</td>
			</tr>
			<tr style="font-size: x-small"><td>scherm-id: UC02-S1</td></tr>
		</tbody>
	</table>
</div>

<input type="hidden" name="herkomst" value="Web" />
<input type="hidden" name="medewerker" value="${pageContext.request.userPrincipal.name}" />
</form:form>
</body>
</html>