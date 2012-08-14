<%@ include file="/WEB-INF/jsp/layout/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="nl" lang="nl">
<head>
	<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
	<title><fmt:message key="applicatie.titel" /></title>
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
	<script src="<c:url value='/js/validation.js'/>" type="text/javascript"></script>

	<script src="<c:url value='/js/FOL3/default.js'/>" type="text/javascript"></script>

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
	<hr class="text-only"/>
	
	<!-- *************************************************************
	START CONTENT PART
	****************************************************************** -->	
	<div id="content">
		<a name="content-part"></a>
		<tiles:insert name="content" />

		<div class="noshow"><a href="#top"><fmt:message key="label.naar.boven" /></a></div>
		
		<div class="noshow">
			<p>
				<strong><fmt:message key="label.let.op" /></strong><br/>
				<fmt:message key="label.footer1" />
			</p>
		</div>

	</div>
	<!-- *************************************************************
	END CONTENT PART
	****************************************************************** -->
</div>
</body>
</html>

