<%@ include file="/WEB-INF/jsp/layout/include.jsp"%>

<html>
<head>

<%-- rloman: de set tag klopt hier toch ?? --%>
<c:set var="aanleidingInstance" value="${session.aanleidingInstance}" />
<c:set var="aanleidingInstanceList" value="${session.aanleidingInstanceList}" />


<script src="js/FOL3/foldoutTemplateOB.js" type="text/javascript"></script>
<script src="js/webstraat/webstraat.js" type="text/javascript"></script>
<script src="js/webstraat/tabular.js" type="text/javascript"></script>
<script type="text/javascript">
	function selecteren() {
		document.forms["selecterenForm"].submit();
	}
	function bewerken() {
		document.forms["raadplegenForm"].submit();
	}
</script>
<title><spring:message code="title.behandelen.aanleiding.beeindigen" />
</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="layout" content="main" />

</head>
<body>
	<h4><spring:message code="title.behandelen.aanleiding.beeindigen" /></h4>
	<fieldset>
		<legend align="left"><spring:message code="title.selectiecriteria" /></legend>
		<table border="0" cellpadding="4" cellspacing="3">
			<tbody>
				<tr>
					<td align="left" width="141"
						title="BSN: Het nummer waaronder de natuurlijk persoon is ingeschreven bij de gemeente. Sofinummer: het nummer waaronder de natuurlijk persoon is geregistreerd bij de belastingdienst."><b>BSN/sofinummer</b>
					</td>
					<td align="left" width="104"
						title="BSN: Het nummer waaronder de natuurlijk persoon is ingeschreven bij de gemeente. Sofinummer: het nummer waaronder de natuurlijk persoon is geregistreerd bij de belastingdienst.">1111.22.333</td>
					<td align="left" width="184"
						title="Geeft aan in welke fase van de afhandeling de melding zich bevind."><b></b>
					</td>
					<td align="left" width="368"
						title="Geeft aan in welke fase van de afhandeling de melding zich bevind."></td>

				</tr>
				<tr>
					<td align="left" width="141"
						title="Het belastingjaar waarop de melding betrekking heeft."><b><spring:message code="title.belastingJaar" /></b>
					</td>
					<td title="Het belastingjaar waarop de melding betrekking heeft."
						align="left" width="104">${aanleidingInstance.belastingJaar}</td>

					<td align="left" width="184"
						title="Kenmerk dat het bericht uniek maakt in de administratie van derden."><b><spring:message code="title.berichtkenmerk.derden" /></b>
					</td>
					<td align="left" width="368"
						title="Kenmerk dat het bericht uniek maakt in de administratie van derden.">${aanleidingInstance.berichtkenmerkDerden}</td>
				</tr>
			</tbody>
		</table>
	</fieldset>
	<fieldset>
		<legend align="left"><spring:message code="title.aanleiding.details" /></legend>
		<table border="0" cellpadding="4" cellspacing="3">
			<tbody>
				<tr
					title="Het tijdstip waarop de aanleiding is geregistreerd in de administratie AIG.">
					<td align="left" width="257"><b><spring:message code="title.aanleiding.begin" /></b>
					</td>
					<td align="left" width="421">${aanleidingInstance.registratieTijdstipBegin}</td>
				</tr>
				<tr
					title="Het tijdstip waarop de afhandeling bij de aanleiding is geregistereerd in de administratie AIG.">
					<td align="left" width="257"><b><spring:message code="title.aanleiding.einde" /></b>
					</td>
					<td align="left" width="421">${aanleidingInstance.registratieTijdstipEinde}</td>
				</tr>
				<tr
					title="De dag waarop er geen rechtsmiddelen meer open staan om de waarden van het AIG te betwisten en daarmee definitief is geworden.">
					<td align="left" width="257"><b><spring:message code="title.aanleiding.onherroeplijk" /></b>
					</td>
					<td align="left" width="421">${aanleidingInstance.datumOnherroepelijk}</td>
				</tr>
				<tr title="De reden van de beëindiging van de aanleiding.">
					<td align="left" width="257" valign="top"><b><spring:message code="title.aanleiding.reden.einde" /></b>
					</td>
					<td align="left" width="421"><input type="radio"
						name="reden_einde" value="delta"><spring:message code="title.aanleiding.nieuw.aig.delta" /><br /> <input
						type="radio" name="reden_einde" value="intrekking"><spring:message code="title.aanleiding.intrekking" /><br />
						<div style="vertical-align: text-top">
							<input type="radio" name="reden_einde" value="tekst"><spring:message code="title.aanleiding.anders" />
							<textarea cols="40" rows="3" style="vertical-align: text-top;"><spring:message code="title.aanleiding.vul.reden" /></spring:message></textarea>
						</div></td>
				</tr>
				<tr
					title="BSN: Het nummer waaronder de natuurlijk persoon is ingeschreven bij de gemeente. Sofinummer: het nummer waaronder de natuurlijk persoon is geregistreerd bij de belastingdienst.">
					<td align="left" width="257"><b><spring:message code="label.burgerservicenummer" /></b>
					</td>
					<td align="left" width="421">${aanleidingInstance.melding.betrokkene.burgerservicenummer}</td>
				</tr>
				<tr title="Het belastingjaar waarop de melding betrekking heeft.">
					<td align="left" width="257"><b><spring:message code="title.belastingJaar" /></b>
					</td>
					<td align="left" width="421">${aanleidingInstance.melding.betrokkene.belastingJaar}</td>
				</tr>
				<tr>
					<td align="left" width="257"><b><spring:message code="title.soort.melding" /></b>
					</td>
					<!--  rloman: soortMelding moet nog ergens in domeinmodel worden opgehaald. Nu nog niet gemaakt. -->
					<td align="left" width="421">${aanleidingInstance.melding.soortMelding}</td>
				</tr>
				<tr
					title="Geeft aan in welke fase van de afhandeling de melding zich bevind.
			STATUS = STM01 nog te beoordelen, STM02 beoordeling heeft geleid tot registratie aanleiding, STM03 beoordeling heeft geleid tot handmatige afhandeling, STM04 beoordeling heeft geleid niet verwerkbaar, STM05 wacht op starten nieuw onderzoek, STM99 overig">
					<td align="left" width="257"><b><spring:message code="title.status" /></b>
					</td>
					<td align="left" width="421">beoordeling heeft geleid tot
						handmatige afhandeling</td>
				</tr>
				<tr
					title="Kenmerk dat het bericht uniek maakt in de administratie van derden.">
					<td align="left" width="257"><b>Berichtkenmerk derden</b>
					</td>
					<td align="left" width="421">12345678901234567890123456789012</td>
				</tr>
				<tr title="Het bedrag uit het oorspronkelijke bericht.">
					<td align="left" width="257"><b>Waarde</b>
					</td>
					<td align="left" width="421">&euro;&nbsp;9.999.999.999.999</td>
				</tr>
				<tr title="De dag waarop de gebeurtenis heeft plaatsgevonden.">
					<td align="left" width="257"><b>Datum plaatsgevonden</b>
					</td>
					<td align="left" width="421">09-04-2010</td>
				</tr>
				<tr title="Het tijdstip waarop de melding in AIG is geregistreerd.">
					<td align="left" width="257"><b>Registratie AIG</b>
					</td>
					<td align="left" width="421">01-02-2011 03:33:54</td>
				</tr>
				<tr>
					<td align="left" width="257"><b>Verkorte meldingnaam</b>
					</td>
					<td align="left" width="421">bezwaar</td>
				</tr>
				<tr title="Het proces dat de melding heeft voortgebracht.">
					<td align="left" width="257"><b>Fiscale bron</b>
					</td>
					<td align="left" title=" (IB of IB-signaal of BJL of AIG)"
						width="421">IB</td>
				</tr>
			</tbody>
		</table>
	</fieldset>
	<fieldset
		title="Gegevens van het onderzoek bij de getoonde aanleiding.">
		<legend align="left">Betrokken onderzoek</legend>
		<table style="border: 0; cellpadding: 4; cellspacing: 3" width="865">
			<thead>
				<tr>
					<th title="Datum waarop het onderzoek gestart is." width="131"
						align="left"><b>Start</b>
					</th>
					<th
						title="De user-id van de medewerker of programma naam die het onderzoek heeft gestart c.q. beëindigd heeft."
						width="135" align="left"><b>Medewerker</b>
					</th>
					<th title="Keten waarin het onderzoek plaatsvindt." width="443"
						align="left"><b>Keten</b>
					</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td style="vertical-align: top" width="148">01-10-2008</td>
					<td width="99">jansm01</td>
					<td width="268">Aanslag</td>
				</tr>
			</tbody>
		</table>
	</fieldset>
	<fieldset>
		<legend align="left">Overige aanleidingen bij onderzoek</legend>
		<table border="0" cellpadding="4" cellspacing="3" width="825">
			<thead>
				<tr>
					<td title="Datum en tijdstip registratie van de aanleiding in AIG."><b>Begin</b>
					</td>
					<td title="Datum waarop de gebeurtenis heeft plaatsgevonden."><b>Plaatsgevonden</b>
					</td>
					<!--
				<td align="left"  title="BSN: Het nummer waaronder de natuurlijk persoon is ingeschreven bij de gemeente
	 Sofinummer: het nummer waaronder de natuurlijk persoon is geregistreerd bij de belastingdienst." ><b>BSN/sofinummer</b></td>
				<td align="left" title="Het belastingjaar waarop de melding betrekking heeft"><b>Jaar</b></td>
				-->
					<td align="left"><b>Soort melding</b>
					</td>
				</tr>
			</thead>
			<tbody>
				<tr id="rij1">
					<td>02-04-2009 13:31:59</td>
					<td>02-01-2009</td>
					<!--
				<td align="left" >1234.12.123</td>
				<td align="left" >2008</td>
				-->
					<td align="left"
						title="hoger beroep tegen uitspraak op beroep tegen AIG">hoger
						beroep tegen uitspraak op bero...</td>
				</tr>
				<tr id="rij2">
					<td>02-05-2009 13:31:59</td>
					<td>02-02-2009</td>
					<!--
				<td align="left" >1234.56.789</td>
				<td align="left" >2008</td>
				-->
					<td align="left">bezwaar BJL</td>
				</tr>
				<tr id="rij3">
					<td>02-06-2009 13:31:59</td>
					<td>02-08-2009</td>
					<!--
				<td align="left" >1234.12.123</td>
				<td align="left" >2008</td>
				-->
					<td align="left">intrekking bezwaar BJL</td>
				</tr>
				<tr id="rij4">
					<td>02-08-2009 13:31:59</td>
					<td>02-10-2009</td>
					<!--
				<td align="left">1234.12.123</td>
				<td align="left">2008</td>
				-->
					<td align="left">ambtshalve vermindering</td>
				</tr>
			</tbody>
		</table>
	</fieldset>
	<fieldset title="AIG bij begin van de aanleiding.">
		<legend align="left">Betrokken AIG</legend>
		<table border="0" cellpadding="4" cellspacing="3" width="756">
			<tbody>
				<tr>
					<th align="left" width="249"><b>Periode</b>
					</th>
					<th align="left" width="116"><b>Registratie</b>
					</th>
					<th align="right"><b>AIG-Waarde</b>
					</th>
					<th align="left"><b>Grondslag</b>
					</th>
					<th align="left"><b>Aard bepaling</b>
					</th>
				</tr>
				<tr
					title="Actuele AIG-waarde waarmee voor deze melding een onderzoek is gestart.">
					<td align="left" width="249">02-03-2008 / 01-05-2008</td>
					<td align="left" width="116">09-04-2010</td>
					<td align="right">&euro; 66.666</td>
					<td align="left">verzamelinkome...</td>
					<td align="left">ontslaan van v...</td>
				</tr>
			</tbody>
		</table>
	</fieldset>
	<fieldset>
		<legend align="left" title="Actuele waarde van het AIG.">Actueel
			AIG</legend>
		<table border="0" cellpadding="4" cellspacing="3" width="756">
			<tbody>
				<tr>
					<th align="left" width="249"
						title="Periode waarin het AIG geldig is (van ? tot ?)"><b>Periode</b>
					</th>
					<th align="left" width="116"
						title="De datum en het tijdstip waarop de AIG waarde geregistreerd is."><b>Registratie</b>
					</th>
					<th align="right"
						title="De waarde van het Authentiek InkomensGegeven"><b>AIG-Waarde</b>
					</th>
					<th align="left"><b>Grondslag</b>
					</th>
					<th align="left"><b>Aard bepaling</b>
					</th>
				</tr>
				<tr title="Actueel AIG.">
					<td align="left" width="246">01-05-2008 / einde belastingjaar</td>
					<td align="left" width="126">31-04-2010</td>
					<td align="right">&euro; 70.000</td>
					<td align="left">verzamelinkome...</td>
					<td align="left">ontslaan van v...</td>
				</tr>
			</tbody>
		</table>
		<b style="color: red;">Betrokken en actueel AIG verschillen van
			elkaar.</b><br /> Be&euml;indig deze aanleiding met het actuele AIG: <input
			type="radio" name="AIG" value="ja">Ja <input type="radio"
			name="AIG" value="nee">Nee
	</fieldset>

	<div align="right">
		<table border="0">
			<tbody>
				<tr>
					<td><input type="button" onclick="selecteren();"
						value="Annuleren"> <input onclick="bewerken();"
						type="submit" name="be&euml;indigen" value="Be&euml;indigen"
						title="Be&euml;indig de aanleiding."></td>
				</tr>
				<tr style="font-size: x-small" align="right">
					<td>scherm-id: UC04-S3</td>
				</tr>
			</tbody>
		</table>
	</div>
	<form action="04 Raadplegen aanleiding.html" method="post"
		id="selecterenForm" /></form>
	<form action="04 Selecteren aanleiding.html" method="post"
		id="raadplegenForm" /></form>
</body>
</html>