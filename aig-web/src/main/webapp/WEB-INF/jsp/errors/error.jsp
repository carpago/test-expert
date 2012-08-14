<%@ include file="/WEB-INF/jsp/layout/include.jsp"%>
<%@ page isErrorPage="true" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="nl" lang="nl">
<head>
	<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
	<title>Applicatiefout: Er heeft zich een ernstige fout voorgedaan!</title>
	<!-- STYLESHEET for screen and print -->
	<link rel="stylesheet" href="<c:url value='/theme/FOL3/default.css'/>" type="text/css" />
	<link rel="stylesheet" href="<c:url value='/theme/webstraat/webstraat.css'/>" type="text/css" />
	<!--[if IE 7]>
    	<link rel="stylesheet" type="text/css" href="<c:url value='/theme/FOL3/ie7-correction.css'/>" />
	<![endif]--> 

	<!--[if lt IE 7]>
		<link rel="stylesheet" href="<c:url value='/theme/FOL3/ie-corrections.css'/>" type="text/css" />	
	<![endif]-->
	<!--  link rel="stylesheet" href="<c:url value='/theme/FOL3/red.css'/>" type="text/css" / -->

	<script src="<c:url value='/js/library.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/js/autosuggest.js'/>" type="text/javascript"></script>	
<!-- 	<script src="<c:url value='/js/validation.js'/>" type="text/javascript"></script> -->


</head>

<body>
<!--================================ Serial navigation -->
<p class="text-only">
	<a href="#content-part"><fmt:message key="label.direct.naar.inhoud" /></a>
	<a name="top"></a>
</p>
<!--================================ End of Serial navigation -->
<hr class="text-only"/>
<!-- *************************************************************
START HEADER PART
****************************************************************** -->
<div id="header">
	<h1><fmt:message key="label.header1" /></h1>
	<h2><fmt:message key="label.header2" /></h2>	
</div>
<!-- *************************************************************
END HEADER PART
****************************************************************** -->

<hr class="text-only"/>

<div id="content-layer">
	<table id="contentTable">
	<tr>
		<td id="contentCell">
		<h4>In AIG heeft zich een ernstige fout voorgedaan!</h4>
		
		<br /><br /><br />

		
		Meldt u dit a.u.b. bij de ServiceDesk (tel. 555) onder vermelding van:

		<br />
		<ol class="error">

		<jsp:useBean id="today" class="java.util.Date"/>
		<li>Tijdstip waarop de fout optrad (<fmt:formatDate value="${today}" pattern="dd-MM-yyyy, HH:mm:ss" />).</li>
		<li>De functie die u in het AIG programma uitvoerde.</li>
		<li>Het BSN-nummer waarvoor de opdracht behandeld wordt.</li>
		<li>Onderstaande technische foutmelding:</li>
		<li>${pageContext.exception.message}</li>
		</ol>
		<ol class="error">
		Mail a.u.b. een schermprint naar de ServiceDesk <br />
		(Alt + Print Screen => Ctrl + V in nieuw bericht) <br />
		met als onderwerp het storingsnummer die u hebt gekregen. <br />
		</ol>
		</td>
	</tr>
</table>

</div>
</body>
</html>

