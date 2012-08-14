<!--
/* <documentation about="ABOUT js/FOL3/FormValidationTemplateOB.js" type="GENERAL">
	<summary>This file is specific javascript file for the (template) page 'TEMPLATE-OB-foldouts.html' in FOL3. 
		Structure of this file:         
		1. Implementation of namespace
		2. Definition global variables
		3. FOL3.FormValidationTemplate.init: start javascript app
		4. Specific functions
		5. Call Lib.addEvent(window, "load", FOL3.FormValidationTemplate.init);
	</summary>
	<namespace>FOL3.FormValidationTemplate</namespace>
</documentation> */
FOL3.FormValidationTemplate = {};

/* <documentation about="Global variables" type="Global variables">
	<summary>link texts for fold out:
		- FOL3.FormValidationTemplate.linkText
	</summary>
	<namespace>FOL3.FormValidationTemplate</namespace>
</documentation> */
FOL3.FormValidationTemplate.linkText = "panel toevoegen";

/* <documentation about="FOL3.FormValidationTemplate.init" type="init function">
	<summary>This function containes all calls made to functions used within the template 'TEMPLATE-OB-foldouts.html' .
		It is initialized on page load (Lib.addEvent(window, 'load', FOL3.FormValidationTemplate.init);).	
	</summary>
	<namespace>FOL3.FormValidationTemplate</namespace>
</documentation> */
FOL3.FormValidationTemplate.init = function() {
	try {
		/* <documentation about="Initialize Lib.addFoldOutPanels" type="FUNCTION CALL">
			<summary>Add fold out functionality to panel with id = foldOutA</summary>
		</documentation> */
		Lib.addFoldOutPanels("foldOutA", FOL3.FormValidationTemplate.linkText, "remove-panel right240");		
	} 
	catch (ex){ Lib.errHandler(ex); }	
}


/* <documentation about="FOL3.FormValidationTemplate.init" type="FUNCTION CALL">
	<summary>Calling Lib.addEvent: Add FOL3.FormValidationTemplate.init as eventhandler on window onload event</summary>
</documentation> */
Lib.addEvent(window, "load", FOL3.FormValidationTemplate.init);
Lib.eventCache.add(window, "load", FOL3.FormValidationTemplate.init, false); 	
//-->
