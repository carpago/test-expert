<!--
/* <documentation about="ABOUT js/FOL3/help.js" type="GENERAL">
	<summary>This file is the javascript file for the help page in FOL3.
		Structure of this file:         
		1. Implementation of namespace FOL3.Help
		2. Definition global variables
		3. FOL3.Help.init(): start javascript app
		4. Specific functions
	</summary>
	<namespace>FOL3.Help</namespace>
</documentation> */
FOL3.Help = {};

/* global variables */
// FOL3.Help.globalVariable = ...

/* <documentation about="FOL3.Help.init" type="init function">
	<summary>This function containes all calls made to functions used on the help page in FOL3.
		It is initialised on page load Lib.addEvent(window, "load", FOL3.Help.init);
	</summary>
	<namespace>FOL3.Help</namespace>
</documentation> */
FOL3.Help.init = function() {
	try {
		/* <documentation about="Initialize FOL3.Help.addToggleHelp();" type="FUNCTION CALL">
			<summary>Adds panels to help</summary>
		</documentation> */
		FOL3.Help.addToggleHelp();
		
		/* <documentation about="Initialize FOL3.Help.addWindowClose();" type="FUNCTION CALL">
			<summary>Adds close window button to page</summary>
		</documentation> */
		FOL3.Help.addWindowClose();
	} 
	catch (ex){ Lib.errHandler(ex); }	
}

/* <documentation about="FOL3.Help.addToggleHelp" type="function">
	<summary>Adds links for toggling help panels</summary>
	<namespace>Lib.Help</namespace>
</documentation> */
FOL3.Help.addToggleHelp = function () {
	//if(Lib.elementsExists("helpList")) {
		var h5s = document.getElementsByTagName("h5");
		for(var i = 0; i<h5s.length; i++)
		{
			var a = document.createElement("a");
			a.innerHTML = h5s[i].innerHTML;
			a.href = "#";
			a.div =  h5s[i].parentNode.getElementsByTagName("div")[0];
			a.onclick = function () { FOL3.Help.toggleHelp(this); };
			Lib.eventCache.add(a, "onclick", function () { FOL3.Help.toggleHelp(this); }, false); 	
			
			h5s[i].innerHTML = "";
			h5s[i].appendChild(a);
		}		
	//}
}

/* <documentation about="FOL3.Help.toggleHelp" type="function">
	<summary>Hides and shows help panels</summary>
	<namespace>Lib.Help</namespace>
	<param type="object" descr="A reference to the hyperlink (HTML elemnt).">hyperlink</param>
</documentation> */
FOL3.Help.toggleHelp = function (hyperlink) {
	try {
		if(hyperlink.div.className == "hide") { hyperlink.div.className = ""; }
		else { hyperlink.div.className = "hide"; }
	}
	catch (ex){ Lib.errHandler(ex); }	
}

/* <documentation about="FOL3.Help.addWindowClose" type="function">
	<summary>Adds 'window close link' and closing functionality.</summary>
	<namespace>Lib.Help</namespace>
</documentation> */
FOL3.Help.addWindowClose = function () {
	var fieldsets = document.getElementsByTagName("fieldset");
	for( var i=0; i<fieldsets.length; i++) {
		if(fieldsets[i].className != "buttons") { continue; } 
		var a = document.createElement("a");
		a.innerHTML = "Sluiten";
		a.href = "#";
		a.onclick = function () { window.close() };	
		Lib.eventCache.add(a, "onclick", function () { window.close() }, false); 	
		fieldsets[i].appendChild(a);
	}
}

/* <documentation about="Initialize FOL3.Help.init" type="FUNCTION CALL">
	<summary>Calling Lib.addEvent: Add FOL3.Help.init as eventhandler on window onload event</summary>
</documentation> */
Lib.addEvent(window, "load", FOL3.Help.init);
Lib.eventCache.add(window, "load", FOL3.Help.init, false); 	
//-->
