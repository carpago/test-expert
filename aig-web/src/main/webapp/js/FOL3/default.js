<!--
/* <documentation about="ABOUT js/FOL3/default.js" type="GENERAL">
	<summary>This file is the default javascript file for FOL3: it contains functions, calls and variables for all FOL3 pages
		Structure of this file:         
		1. Implementation of namespace FOL3 
		2. Definition global variables
		3. FOL3.init(): start javascript app
		4. Specific functions
		5. Call  FOL3.init
	</summary>
	<namespace>FOL3</namespace>
</documentation> */
var FOL3 = {};

/* global variables go here */
// FOL3.globalVariable = ...

/* <documentation about="FOL3.init" type="init function">
	<summary>This function containes all calls made to functions used on every page of FOL3.
		It is initialized on page load (Lib.addEvent(window, "load", FOL3.init);).		
	</summary>
	<namespace>FOL3</namespace>
</documentation> */
FOL3.init = function() {
	/* <documentation about="Lib.debug" type="Set variable">
		<summary>For debug mode set Lib.debug to true here; else set to false. If Lib-debug=false; all javascript errors will be suppressed
		</summary>
		<namespace>FOL3</namespace>
	</documentation> */
	Lib.debug = true;
	//alert ("page is initialized");  //=Main.debugAlert
	try {	
		/* <documentation about="Initialize FOL3.positionLogo();" type="FUNCTION CALL">
			<summary>Positions logo on left side</summary>
		</documentation> */
		FOL3.positionLogo();	
		
		/* <documentation about="Initialize FOL3.positionButtons();" type="FUNCTION CALL">
			<summary>Positions buttons at bottom of screen</summary>
		</documentation> */
		FOL3.positionButtons();	
		
		/* <documentation about="Initialize Lib.addHelp();" type="FUNCTION CALL">
			<summary>Adds help panels</summary>
		</documentation> */
		Lib.addHelp();	
		
		/* <documentation about="Initialize Lib.addOpenInLinksNewWindow();" type="FUNCTION CALL">
			<summary>Adds 'open link in new window' to hyperlinks with type='new window'</summary>
		</documentation> */
		Lib.addOpenInLinksNewWindow();	
		
		/* <documentation about="Initialize Lib.disableFormFields();" type="FUNCTION CALL">
			<summary>Disables form fields alt='read-only'</summary>
		</documentation> */
		Lib.disableFormFields();
		
		/* <documentation about="Add FOL3.positionLogo() to window.onresize and window.onscroll event" type="ADD EVENTHANDLER">
			<summary>Positions logo on left side on resize and on scroll</summary>
		</documentation> */
		window.onresize = function() { FOL3.positionLogo(); FOL3.positionButtons(); };
		window.onscroll = function() { FOL3.positionLogo();  };
		//add events to repository
		Lib.eventCache.add(window, "onresize", function() { FOL3.positionLogo(); FOL3.positionButtons(); }, false); 		
		Lib.eventCache.add(window, "onscroll", function() { FOL3.positionLogo(); }, false); 	
		
	} 
	catch (ex){ Lib.errHandler(ex); }	
}

/* <documentation about="FOL3.positionLogo" type="init function">
	<summary>Positions Belastingdienst logo at the bottom of the screen.</summary>
	<namespace>FOL3</namespace>
</documentation> */
FOL3.positionLogo = function () {
	try {
		if(Lib.elementsExists("navigation", "numberAndLogo")) {	
			var navigation = document.getElementById("navigation");
			var numberAndLogo = document.getElementById("numberAndLogo");
			var windowHeight = Lib.getWindowHeight();
			//do not position logo when styles are disabled
			if(navigation.offsetWidth <=270) {
				if(windowHeight-navigation.offsetHeight>45){	
					numberAndLogo.style.position="absolute";
					numberAndLogo.style.top= (windowHeight - 70 + Lib.getScrollY()) + "px";		
				}
				else {
					numberAndLogo.style.position = "static";
					numberAndLogo.style.top = "0px";
				}	
			}	
		}
	} catch (ex){ Lib.errHandler(ex); }	
}

/* <documentation about="FOL3.positionButtons" type="function">
	<summary>Positions buttons at bottof screen</summary>
	<namespace>FOL3</namespace>
</documentation> */
FOL3.positionButtons = function () {
	try {
		var buttonFieldset = Lib.getElementsByClassName("buttons", "fieldset");
		
		if(!buttonFieldset[0]) { return; }
		
		var spans = buttonFieldset[0].getElementsByTagName("span");
		for(var i=0; i<spans.length; i++) {
			if(spans[i].className != "right")  { continue; }
			var windowWidth =955;
			if( typeof( window.innerWidth ) == 'number' ) { windowWidth = window.innerWidth; } 
			else if( document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight ) ) {
    			//IE 6+ in 'standards compliant mode'
    			windowWidth = document.documentElement.clientWidth +20; //20px for scrollbar
  			} 
 			if(windowWidth<955) {
				if (windowWidth<785) { spans[i].style.paddingRight = "190px"; }
				else  { spans[i].style.paddingRight = (975 - windowWidth) + "px"; }
			}
			else { spans[i].style.paddingRight = "0px"; }
		}
	} catch (ex){ Lib.errHandler(ex); }	
}

/* <documentation about="Lib.addStyleSheet('theme/FOL3/javascript.css')" type="FUNCTION CALL">
	<summary>Add javascript style sheet to hide folds outs</summary>
</documentation> */
Lib.addStyleSheet("theme/FOL3/javascript.css");

/* <documentation about="Initialize FOL3.init" type="FUNCTION CALL">
	<summary>Calling Lib.addEvent: Add FOL3.init as eventhandler on window onload event</summary>
</documentation> */
Lib.addEvent(window, "load", FOL3.init);
Lib.eventCache.add(window, "load", FOL3.init, false); 	
//-->