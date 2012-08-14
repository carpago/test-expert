<!--
/* <documentation about="ABOUT js/FOL3/formElementsTemplateOB.js" type="GENERAL">
	<summary>This file is specific javascript file for the template page "TEMPLATE-OB-formelements.html" in FOL3. 
		Structure of this file:         
		1. Implementation of namespace FOL3.FormElementsTemplate
		2. FOL3.FormElementsTemplate.init: start javascript app
		3. Specific functions
	</summary>
	<namespace>FOL3.FormElementsTemplate</namespace>
</documentation> */
FOL3.FormElementsTemplate = {};

/* global variables 
... */


/* <documentation about="FOL3.FormElementsTemplate.init" type="init function">
	<summary>This function containes all calls made to functions within the template "TEMPLATE-OB-formelements.html"
		It is initialized on page load (Lib.addEvent(window, "load", FOL3.FormElementsTemplate.init);).	
	</summary>
	<namespace>FOL3.FormElementsTemplate</namespace>
</documentation> */
FOL3.FormElementsTemplate.init = function() {
	try {
		//add calculations OB
		FOL3.FormElementsTemplate.addCalculations();
		
		//add autosuggest
		FOL3.FormElementsTemplate.addAutosuggest();		
		
		//toggle sofinr/personeels nr
		FOL3.FormElementsTemplate.addToggling();		
	} 
	catch (ex){ Lib.errHandler(ex); }	
}

/* <documentation about="FOL3.FormElementsTemplate.addCalculations" type="function">
	<summary>Adds caluculations to the form. </summary>
	<namespace>FOL3.FormElementsTemplate</namespace>
</documentation> */
FOL3.FormElementsTemplate.addCalculations = function() {
	//Omzet hoog tarief
	if (Lib.elementsExists("omzetBedragHoogTarief", "omzetBelastingHoogTarief", "percentageHoogTarief")) {
		document.getElementById("omzetBelastingHoogTarief").onkeyup = function () {  FOL3.FormElementsTemplate.calculateTurnover(this, document.getElementById("omzetBedragHoogTarief"), document.getElementById("percentageHoogTarief").value); }; 
		//add events to repository
		Lib.eventCache.add(document.getElementById("omzetBelastingHoogTarief"), "onkeyup",function () {  FOL3.FormElementsTemplate.calculateTurnover(this, document.getElementById("omzetBedragHoogTarief"), document.getElementById("percentageHoogTarief").value); }, false); 	
	}
	
	//Omzet laag tarief
	if (Lib.elementsExists("omzetBedragLaagTarief", "omzetBelastingLaagTarief", "percentageLaagTarief")) {
		document.getElementById("omzetBelastingLaagTarief").onkeyup = function () {  FOL3.FormElementsTemplate.calculateTurnover(this, document.getElementById("omzetBedragLaagTarief"), document.getElementById("percentageLaagTarief").value); }; 
		//add events to repository
		Lib.eventCache.add(document.getElementById("omzetBelastingLaagTarief"), "onkeyup",function () {  FOL3.FormElementsTemplate.calculateTurnover(this, document.getElementById("omzetBedragLaagTarief"), document.getElementById("percentageLaagTarief").value); }, false); 	
	}
	
	//Omzet overig tarief
	if (Lib.elementsExists("omzetBedragOverigTarief", "omzetBelastingOverigTarief", "percentageOverigTarief")) {
		document.getElementById("omzetBelastingOverigTarief").onkeyup = function () {  FOL3.FormElementsTemplate.calculateTurnover(this, document.getElementById("omzetBedragOverigTarief"), document.getElementById("percentageOverigTarief").value); }; 
		//add events to repository
		Lib.eventCache.add(document.getElementById("omzetBelastingOverigTarief"), "onkeyup",function () {  FOL3.FormElementsTemplate.calculateTurnover(this, document.getElementById("omzetBedragOverigTarief"), document.getElementById("percentageOverigTarief").value); }, false); 	
	}
}

/* <documentation about="FOL3.FormElementsTemplate.calculateTurnover" type="function">
	<summary>Calculates turnover and sets value of the turnoverField</summary>
	<namespace>FOL3.FormElementsTemplate</namespace>
	<param type="object" descr="tax input field (HTML element)">taxField</param>
	<param type="object" descr="turnover inputfield (HTML Element)">turnoverField</param>
	<param type="string" descr="percentage used for calculation">percentage</param>
</documentation> */
FOL3.FormElementsTemplate.calculateTurnover = function (taxField, turnoverField, percentage) {
	try	{
		percentage = parseFloat(percentage);
	
		/* 	check tax is a integer
     	   all errors are caught server-side: only calculate turnover if input is valid
			result is rounded */
		if(Lib.Validation.isInteger(taxField.value)) {
			if(percentage==0) { turnoverField.value = taxField.value; }
			else {
				var tax = parseFloat(taxField.value);
				turnoverField.value = Math.round( ( tax / percentage ) * 100 );
			}
		}
		else {
			turnoverField.value = ""; 
			taxField.value = ""; 
		}
	} 
	catch (ex){ Lib.errHandler(ex); }	
}


/* <documentation about="FOL3.FormElementsTemplate.addToggling" type="function">
	<summary>Adds enabling/disabling of sofinummer/personeelsnummer field</summary>
	<namespace>FOL3.FormElementsTemplate</namespace>
</documentation> */
FOL3.FormElementsTemplate.addToggling = function() {
	try {
		if (Lib.elementsExists("sofinummer", "personeelsnummer")) {
			document.getElementById("sofinummer").value= "";
			document.getElementById("personeelsnummer").value= "";
			
			document.getElementById("sofinummer").onkeyup = function () {  FOL3.FormElementsTemplate.toggleActiveField(this, document.getElementById("personeelsnummer")); }; 
			document.getElementById("personeelsnummer").onkeyup = function () {  FOL3.FormElementsTemplate.toggleActiveField(this, document.getElementById("sofinummer")); }; 
			
			//add events to repository
			Lib.eventCache.add(document.getElementById("sofinummer").onkeyup, "onkeyup", function () {  FOL3.FormElementsTemplate.toggleActiveField(this, document.getElementById("personeelsnummer")); }, false); 	
			Lib.eventCache.add(document.getElementById("personeelsnummer").onkeyup, "onkeyup", function () {  FOL3.FormElementsTemplate.toggleActiveField(this, document.getElementById("sofinummer")); }, false); 	
		}
	} 
	catch (ex){ Lib.errHandler(ex); }	
}


/* <documentation about="FOL3.FormElementsTemplate.toggleActiveField" type="function">
	<summary>Enabling/disabling of sofinummer/personeelsnummer field; function is used in FOL3.FormElementsTemplate.addToggling</summary>
	<namespace>FOL3.FormElementsTemplate</namespace>
	<param type="object" descr="Active field (HTML element)">activeField</param>
	<param type="object" descr="Field to disable (HTML element)">disableField</param>
</documentation> */
FOL3.FormElementsTemplate.toggleActiveField = function (activeField, disableField) {
	if (activeField.value.length != 0) {
		disableField.value =  "";
		if(disableField.className.indexOf("read-only") == -1) {
			disableField.className = disableField.className + " read-only";
			disableField.readOnly = true;
		}
	} 
	else {
		disableField.className = disableField.className.replace(/ read-only/, "");
		disableField.readOnly = false;
	}
}

/* <documentation about="FOL3.FormElementsTemplate.addAutosuggest" type="function">
	<summary>Adds autopsuggest feature to input fields with class name 'autosuggest'. Data for this field is read from div 'autosuggest-values'.
		Lib.AutoSuggest.add is called for the actual adding of the autosuggest.</summary>
	<namespace>FOL3.FormElementsTemplate</namespace>
</documentation> */
FOL3.FormElementsTemplate.addAutosuggest  = function () {
	var autosuggestTextBoxes = Lib.getElementsByClassName("autosuggest", "input");
	
	for(var i=0; i< autosuggestTextBoxes.length; i++) {
	  	var autosuggestValuesPanel = Lib.getNextElement(autosuggestTextBoxes[i]);
		
		var listItems = autosuggestValuesPanel.getElementsByTagName("li");
		var data = new Array();
		for(var j=0; j< listItems.length; j++) {
			data[j] = listItems[j].innerHTML;
		}
		
		//remove values panel
		autosuggestValuesPanel.parentNode.removeChild(autosuggestValuesPanel);
		
		//Call Lib.AutoSuggest.add = function (autoSuggestFieldId, data, numerOfItems, onfocus, filterData) 
		Lib.AutoSuggest.add(autosuggestTextBoxes[i].id, data, 4,  true, true);		
	 }	
}

/* <documentation about="FOL3.FormElementsTemplate.init" type="FUNCTION CALL">
	<summary>Calling Lib.addEvent: Add FOL3.FormElementsTemplate.init as eventhandler on window onload event</summary>
</documentation> */
Lib.addEvent(window, "load", FOL3.FormElementsTemplate.init);
Lib.eventCache.add(window, "load", FOL3.FormElementsTemplate.init, false); 	
//-->
