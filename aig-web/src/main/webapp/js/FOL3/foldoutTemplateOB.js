<!--
/* <documentation about="ABOUT js/FOL3/foldoutTemplateOB.js" type="GENERAL">
	<summary>This file is specific javascript file for the (template) page 'TEMPLATE-OB-foldouts.html' in FOL3. 
		Structure of this file:         
		1. Implementation of namespace
		2. Definition global variables
		3. FOL3.FoldOutTemplate.init: start javascript app
		4. Specific functions
		5. Call Lib.addEvent(window, "load", FOL3.FoldOutTemplate.init);
	</summary>
	<namespace>FOL3.FoldOutTemplate</namespace>
</documentation> */
FOL3.FoldOutTemplate = {};

/* <documentation about="Global variables" type="Global variables">
	<summary>link texts for fold out:
		- FOL3.FoldOutTemplate.linkText
	</summary>
	<namespace>FOL3.FoldOutTemplate</namespace>
</documentation> */
FOL3.FoldOutTemplate.linkText = "panel toevoegen";

/* <documentation about="FOL3.FoldOutTemplate.init" type="init function">
	<summary>This function containes all calls made to functions used within the template 'TEMPLATE-OB-foldouts.html' .
		It is initialized on page load (Lib.addEvent(window, 'load', FOL3.FoldOutTemplate.init);).	
	</summary>
	<namespace>FOL3.FoldOutTemplate</namespace>
</documentation> */
FOL3.FoldOutTemplate.init = function() {
	try {
		/* <documentation about="Initialize Lib.addFoldOutPanels" type="FUNCTION CALL">
			<summary>Add fold out functionality to panel with id = foldOutA</summary>
		</documentation> */
		Lib.addFoldOutPanels("foldOutA", FOL3.FoldOutTemplate.linkText, "remove-panel right240");
		
		/* <documentation about="Initialize FOL3.FoldOutTemplate.addClickRadioButton" type="FUNCTION CALL">
			<summary>Add fold out functionality to radiobuttons</summary>
		</documentation> */
		FOL3.FoldOutTemplate.addClickRadioButton();
		
		/* <documentation about="Initialize FOL3.FoldOutTemplate.addAutosuggest" type="FUNCTION CALL">
			<summary>Add autosuggest functionality</summary>
		</documentation> */
		FOL3.FoldOutTemplate.addAutosuggest();		
	} 
	catch (ex){ Lib.errHandler(ex); }	
}

/* <documentation about="FOL3.FoldOutTemplate.addClickRadioButton" type="function">
	<summary>Adds fold out functionality when radiobuttons are clicked (for radiobutton group 'bestemming' and 'betaling').</summary>
	<namespace>FOL3.FoldOutTemplate</namespace>
</documentation> */
FOL3.FoldOutTemplate.addClickRadioButton = function () {
	if (Lib.elementsExists("panelUitgevoerdWerk", "panelBetalingskenmerk", "panelAnderDepot", "loonheffingen", "omzetbelasting", "naheffingloon", "naheffingomzet", "anderdepot")) {
		var inputElements = document.getElementsByTagName("input");
		for(var i = 0; i<inputElements.length; i++) {
			if(inputElements[i].name == "bestemming" || inputElements[i].name == "betaling") {
				inputElements[i].onclick = function () { FOL3.FoldOutTemplate.togglePanel(this.id, this.name); };
				Lib.eventCache.add(inputElements[i], "onclick", function () { FOL3.FoldOutTemplate.togglePanel(this.id, this.name); }, false); 	
				
				if(inputElements[i].checked) { FOL3.FoldOutTemplate.togglePanel(inputElements[i].id, inputElements[i].name); }				
			}
		} 
	} 				
}

/* <documentation about="FOL3.FoldOutTemplate.togglePanel" type="function">
	<summary>Toggles (shows/hides) panels 'panelUitgevoerdWerk', 'panelBetalingskenmerk' and 'panelAnderDepot'.
		Controls ('input' fields) in a panel that is hidden are resetted with the function Lib.clearFormFields.</summary>
	<namespace>FOL3.FoldOutTemplate</namespace>
	<param type="string" descr="Id of radiobutton">elementId</param>
	<param type="string" descr="Name of radiobuttons (group)">groupName</param>
</documentation> */
FOL3.FoldOutTemplate.togglePanel = function (elementId, groupName) {
	var panelUitgevoerdWerk = document.getElementById("panelUitgevoerdWerk");
	
	if(groupName == "bestemming") {
		var panelAnderDepot = document.getElementById("panelAnderDepot");
		var panelBetalingskenmerk = document.getElementById("panelBetalingskenmerk");
		
		if(elementId == "anderdepot") {
			panelAnderDepot.className = "";
			panelBetalingskenmerk.className = "hide";
			
			//clear results in "panelBetalingskenmerk"
			Lib.clearFormFields(panelBetalingskenmerk.getElementsByTagName("input"));
		}
		else {
			panelAnderDepot.className = "hide";
			panelBetalingskenmerk.className = "";
			
			//clear results in "panelAnderDepot"
			Lib.clearFormFields(panelAnderDepot.getElementsByTagName("input"));
		}
		panelUitgevoerdWerk.className="hide"; //always hide panel 'uitgevoerd werk' in this stage
	}
	else if(groupName == "betaling") {
		if(elementId == "uitgevoerdwerk") {
			panelUitgevoerdWerk.className = "";
		}
		else {
			panelUitgevoerdWerk.className = "hide";
			
			//clear results in "panelUitgevoerdWerk"
			Lib.clearFormFields(panelUitgevoerdWerk.getElementsByTagName("input"));
		}
	}
}

/* <documentation about="FOL3.FoldOutTemplate.addAutosuggest" type="function">
	<summary>Adds autopsuggest feature to input fields with class name 'autosuggest'. Data for this field is read from div 'autosuggest-values'.
		Lib.AutoSuggest.add is called for the actual adding of the autosuggest.</summary>
	<namespace>FOL3.FoldOutTemplate</namespace>
</documentation> */
FOL3.FoldOutTemplate.addAutosuggest  = function () {
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


/* <documentation about="FOL3.FoldOutTemplate.init" type="FUNCTION CALL">
	<summary>Calling Lib.addEvent: Add FOL3.FoldOutTemplate.init as eventhandler on window onload event</summary>
</documentation> */
Lib.addEvent(window, "load", FOL3.FoldOutTemplate.init);
Lib.eventCache.add(window, "load", FOL3.FoldOutTemplate.init, false); 	
//-->
