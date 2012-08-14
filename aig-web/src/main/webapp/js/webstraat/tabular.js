<!--
/*
 * Webstraat javascript library voor tabellen. Deze lib zorgt voor de highlight balk in de mouse over event.
 * 
 */
WEBSTRAAT.tabular = {};
	

WEBSTRAAT.tabular.init = function() {
	var i, j, iTable;

	// look for tabular data tables
	var tables = Lib.getElementsByClassName('highlight-tabular', 'table',document );
	for (iTable=0; iTable < tables.length; iTable++) {

		// row highlight to help reading data
		var tbody = tables[iTable].getElementsByTagName('tbody');
		for(i=0; i<tbody.length;i++) {
			var trs = tbody[i].getElementsByTagName('tr');
			for (j=0;j<trs.length;j++) {
				WEBSTRAAT.tabular.attachRowEvents(trs[j]);
			}
		}
	}
}
	
WEBSTRAAT.tabular.attachRowEvents = function(tr) {
		tr.onmouseover = WEBSTRAAT.tabular.mouseover_datarow;
		tr.onmouseout  = WEBSTRAAT.tabular.mouseout_datarow;
}

WEBSTRAAT.tabular.mouseover_datarow = function() { 
	WEBSTRAAT.css.add(this,'hover'); 
}
WEBSTRAAT.tabular.mouseout_datarow = function() { 
	WEBSTRAAT.css.remove(this,'hover'); 
}
	
Lib.addEvent(window, "load", WEBSTRAAT.tabular.init);
Lib.eventCache.add(window, "load", WEBSTRAAT.tabular.init, false);
//-->