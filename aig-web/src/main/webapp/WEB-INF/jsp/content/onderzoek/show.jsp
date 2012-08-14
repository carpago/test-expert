<%@ include file="/WEB-INF/jsp/layout/include.jsp"%>

<html>
<head>


<script src="js/FOL3/foldoutTemplateOB.js" type="text/javascript"></script>
<script src="js/webstraat/webstraat.js" type="text/javascript"></script>
<script src="js/webstraat/tabular.js" type="text/javascript"></script>
<script type="text/javascript">
	/* aanleiding is een JSON object (zie verder Aanleiding.getAsJson en http://json.org) */
	function selecteerAanleiding(aanleiding) {
		//preconditie: aanleigins is een geldig json object.
		
		document.getElementById("registratieTijdstipBegin").innerText = aanleiding.registratieTijdstipBegin;
		document.getElementById("registratieTijdstipEinde").innerText = aanleiding.registratieTijdstipEinde;
		document.getElementById("datumOnherroepelijk").innerText = aanleiding.datumOnherroepelijk;
		document.getElementById("redenEinde").innerText = aanleiding.redenEinde;

		document.getElementById("melding.oorspronkelijkeMeldingNaam").innerText = aanleiding.melding.oorspronkelijkeMeldingNaam;
		document.getElementById("melding.berichtkenmerkDerden").innerText = aanleiding.melding.berichtkenmerkDerden;
		document.getElementById("melding.waarde").innerText = aanleiding.melding.waarde;
		document.getElementById("melding.datumPlaatsGevonden").innerText = aanleiding.melding.datumPlaatsGevonden;
		document.getElementById("melding.datumTijdRegistratie").innerText = aanleiding.melding.datumTijdRegistratie;
		document.getElementById("melding.verkorteMeldingNaam").innerText = aanleiding.melding.verkorteMeldingNaam;
		document.getElementById("melding.fiscaleBron").innerText = aanleiding.melding.fiscaleBron;

		document.getElementById("aigWaarde1.datumIngangDatumEinde").innerText = aanleiding.aigWaarde1.datumIngangDatumEinde;
		document.getElementById("aigWaarde1.datumTijdGeregistreerd").innerText = aanleiding.aigWaarde1.datumTijdGeregistreerd;
		document.getElementById("aigWaarde1.waarde").innerText = aanleiding.aigWaarde1.waarde;
		
		/* rloman: dit lijkt nog een omschrijving te moeten worden */
		document.getElementById("aigWaarde1.grondslagCode").innerText = aanleiding.aigWaarde1.grondslagCode;
		document.getElementById("aigWaarde1.aardBepalingCode").innerText = aanleiding.aigWaarde1.datumTijdGeregistreerd;
		
		//postconditie: de items van de aanleiding moeten nu via javascript zijn geset.
	}

	function selecteren() {
		document.forms["selecterenForm"].submit();
	}
	function raadplegen() {
		document.forms["raadplegenDetailsAanleidingForm"].submit();
	}
	function raadpleegGerelateerdeAanleiding() {
		document.getElementById("gaNaarSelecteren").style.display = "none";
		document.getElementById("gaNaarRaadplegen").style.display = "inline";
		document.getElementById("gaNaarBewerken").style.display = "none";
		document.getElementById("rplGeselecteerdeAanleiding").style.display = "none";
		document.getElementById("rplGerelateerdeAanleiding").style.display = "inline";
		document.getElementById("overigeAanleidingenSelecteerbaar").style.display = "none";
		document.getElementById("overigeAanleidingenRaadpleegbaar").style.display = "inline";
		window.scrollTo(0, 0);
	}
	function raadpleegGeselecteerdeAanleiding() {
		document.getElementById("gaNaarSelecteren").style.display = "inline";
		document.getElementById("gaNaarRaadplegen").style.display = "none";
		document.getElementById("gaNaarBewerken").style.display = "inline";
		document.getElementById("rplGeselecteerdeAanleiding").style.display = "inline";
		document.getElementById("rplGerelateerdeAanleiding").style.display = "none";
		document.getElementById("overigeAanleidingenSelecteerbaar").style.display = "inline";
		document.getElementById("overigeAanleidingenRaadpleegbaar").style.display = "none";
		window.scrollTo(0, 0);
	}
</script>
<title><spring:message code="title.raadplegen.onderzoek" />
</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="layout" content="main" />
</head>
<body>
	<a name="top"></a>
	<h4 id="rplGeselecteerdeAanleiding" style="display: inline;">
		<spring:message code="title.raadplegen.onderzoek" />
	</h4>
	<h4 id="rplGerelateerdeAanleiding" style="display: none;">
		<spring:message
			code="title.behandelen.onderzoeken.raadplegen.aanleiding" />
	</h4>

	<jsp:include page="selectiecriteria.jsp" />
	<jsp:include page="detailsvanhetonderzoek.jsp" />
	<jsp:include page="overigeaanleidingenselecteerbaar.jsp" />
	<jsp:include page="detailsvandegeselecteerdeaanleiding" />

	<div align="right">
		<table border="0">
			<tbody>
				<tr>
					<td><input id="gaNaarSelecteren" style="display: inline"
						type="button" onclick="selecteren();" value="Terug"
						title="Terug naar selectiescherm"></td>
				</tr>
				<tr style="font-size: x-small" align="right">
					<td>scherm-id: UC05-S2</td>
				</tr>
			</tbody>
		</table>
	</div>
	<form action="onderzoek/list.html" method="post" id="selecterenForm" /></form>
	<form action="05 Raadplegen details aanleiding.html" method="post"
		id="raadplegenDetailsAanleidingForm" /></form>
</body>
</html>