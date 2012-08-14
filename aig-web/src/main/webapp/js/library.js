/* <documentation about="ABOUT library.js" type="GENERAL">
	<summary>This file is a library: it contains general functions used for the entire application. 
		No page initialisation calls are made in this file; there is one exception: Lib.addEvent(window, "unload", Lib.eventCache.flush): This removes all attached events.
		Make your function calls in the specific javascript files, in the [namespace].init()
	</summary>
	<namespace>Lib</namespace>
</documentation> */
var Lib = {};


/* <documentation about="Lib.debug/Lib.allowAlert" type="global variables">
	<summary>These variables are used for debugging - do not change</summary>
	<namespace>Lib</namespace>
</documentation> */
Lib.debug = false; 
Lib.allowAlert = true;

/* <documentation about="Lib.safari/Lib.opera/Lib.ie" type="global variables">
	<summary>Browser checks</summary>
	<namespace>Lib</namespace>
</documentation> */
Lib.safari = (navigator.userAgent.toLowerCase().indexOf('safari') != - 1);
Lib.opera = window.opera ? true : false;
Lib.ie = (document.all && document.getElementById) ? true : false;

/* <documentation about="Lib.closeLink/Lib.helpPanel/Lib.closeLinkTooltip" type="global variables">
	<summary>Variables used for help panel. Lib.closeLinkTooltip is title text shown on mouse over</summary>
	<namespace>Lib</namespace>
</documentation> */
Lib.closeLink;
Lib.helpPanel;
Lib.closeLinkTooltip = "Sluit deze uitleg";

/* <documentation about="Lib.newWindowToolTipText" type="global variables">
	<summary>Title text shown on mouse over of a link which opens in a new window</summary>
	<namespace>Lib</namespace>
</documentation> */
Lib.newWindowToolTipText = "deze link opent in een nieuw venster";


/* ========== CORE ============================================================================ */
/* <documentation about="Lib.addEvent" type="CORE FUNCTION">
	<summary>Adds events to elements of the DOM</summary>
	<namespace>Lib</namespace>
	<param type="object" descr="A reference to the node on which the event has been set (HTML element).">obj</param>
	<param type="string" descr="The name of the event ">evt</param>
	<param type="object" descr="A reference to the function which handles the event.">fn</param>
</documentation> */
Lib.addEvent = function (obj,evt,fn) {
	if (obj.addEventListener)
		obj.addEventListener(evt,fn,false);
	else if (obj.attachEvent)
		obj.attachEvent('on'+evt,fn);
}

/* <documentation about="Lib.removeEvent" type="CORE FUNCTION">
	<summary>Removes events to elements of the DOM</summary>
	<namespace>Lib</namespace>
	<param type="object" descr="A reference to the node on which the event has been set (HTML element).">obj</param>
	<param type="string" descr="The name of the event ">evt</param>
	<param type="object" descr="A reference to the function which handles the event.">fn</param>
</documentation> */
Lib.removeEvent = function (obj,evt,fn) {
	if (obj.removeEventListener)
		obj.removeEventListener(evt,fn,false);
	else if (obj.detachEvent)
		obj.detachEvent('on'+evt,fn);
}

Lib.eventCache = function(){
	try {
		var listEvents = [];
		
		/*  Implement array.push for browsers which don't support it natively. (used in EventCache)
		Please remove this if it's already in other code */
		if(Array.prototype.push == null){
			Array.prototype.push = function(){
				for(var i = 0; i < arguments.length; i++){
					this[this.length] = arguments[i];
		       	};
		        return this.length;
			};
		};
	     
	    return {
			listEvents : listEvents,
	     
			/* <documentation about="Lib.eventCache.add" type="CORE FUNCTION">
				<summary>Keeping track of all the attached events</summary>
				<namespace>Lib</namespace>
				<param type="object" descr="A reference to the node on which the event has been set (HTML element).">node</param>
				<param type="string" descr="The name of the event">sEventName</param>
				<param type="object" descr="A reference to the function which handles the event. ">fHandler</param>
				<param type="bool" descr="A boolean which determines whether the event is triggered in capture mode or not. Does not apply to Internet Explorer.">bCapture</param>
			</documentation> */
			add : 	function(node, sEventName, fHandler, bCapture){
						listEvents.push(arguments); 
					},
	     
		 	/* <documentation about="Lib.eventCache.flush" type="CORE FUNCTION">
				<summary>Used to remove (detach) all cached events.</summary>
			</documentation> */
			flush : 	function(){
					var i, item;
					for(i = listEvents.length - 1; i >= 0; i = i - 1){
						item = listEvents[i];
	                 	Lib.removeEvent(item[0], item[1], item[2])
	                    
						item[0][item[1]] = null;
					};
			}
	      };
	} catch (ex){ Lib.errHandler(ex); }	
}();

/* <documentation about="Lib.debugAlert" type="CORE FUNCTION">
	<summary>Displays alert with error message, if alert is allowed (not cancelled in confirm) and Lib.debug = true</summary>
	<namespace>Lib</namespace>
	<param type="string" descr="Message to display in alert">message</param>
</documentation> */
Lib.debugAlert = function (message) {
		if(Lib.allowAlert && Lib.debug) { Lib.allowAlert = confirm(message); }
	}


/* <documentation about="Lib.errHandler" type="CORE FUNCTION">
	<summary>Handles errors in javascript application</summary>
	<namespace>Lib</namespace>
	<param type="object" descr="Error object">err</param>
</documentation> */
Lib.errHandler = function (err) {
		var errorText = "";
		for (var i in err) { errorText += i + "=" + err[i] + "\n"; }
		Lib.debugAlert("An error has occured: \n\n" + errorText + "\nSee Firefox browser for correct linenumbers."); 			
		return true;	
	}	
/* ========== END CORE ====================================================================== */
	
/* ========== GENERAL FUNCTIONS ============================================================= */
/* <documentation about="Lib.getWindowHeight" type="general function">
	<summary>Gets innerheight of browser window</summary>
	<namespace>Lib</namespace>
	<returns>Inner window height in pixels (integer)</returns>
</documentation> */
Lib.getWindowHeight = function () {
	var myHeight = 0;
	if( typeof( window.innerHeight ) == 'number' ) {
		//Non-IE
		myHeight = window.innerHeight;
	} else if( document.documentElement &&  document.documentElement.clientHeight  ) {
		//IE 6+ in 'standards compliant mode'
		myHeight = document.documentElement.clientHeight;
	} else if( document.body && document.body.clientHeight) {
		//IE 4 compatible
		myHeight = document.body.clientHeight;
	}
	return myHeight;
}

/* <documentation about="Lib.getScrollY" type="general function">
	<summary>Get scrolling distance from the top of the window in pixels</summary>
	<namespace>Lib</namespace>
	<returns>Scrolling distance in pixels (integer)</returns>
</documentation> */
Lib.getScrollY = function () {
  var scrOfY = 0;
  if( typeof( window.pageYOffset ) == 'number' ) {
    //Netscape compliant
    scrOfY = window.pageYOffset;
  } else if( document.body && ( document.body.scrollLeft || document.body.scrollTop ) ) {
    //DOM compliant
    scrOfY = document.body.scrollTop;
  } else if( document.documentElement && ( document.documentElement.scrollLeft || document.documentElement.scrollTop ) ) {
    //IE6 standards compliant mode
    scrOfY = document.documentElement.scrollTop;    
  }
  return scrOfY;
}

/* <documentation about="Lib.findElementPosition" type="general function">
	<summary>Find position of a HTML element relative to window</summary>
	<namespace>Lib</namespace>
	<param type="object" descr="HTML element">elem</param>
	<returns>array [left (integer), top (integer)]</returns>
</documentation> */
Lib.findElementPosition = function (elem){
	var curleft = curtop = 0;
	if (elem.offsetParent) {
	
		curleft = elem.offsetLeft
		curtop = elem.offsetTop
		while (elem = elem.offsetParent) {
			curleft += elem.offsetLeft
			curtop += elem.offsetTop
		}
	}
	return [curleft,curtop];
}

/* <documentation about="Lib.elementIsHidden" type="general function">
	<summary>Checks if an element is hidden (has parent with className 'hide'</summary>
	<namespace>Lib</namespace>
	<param type="object" descr="HTML element">obj</param>
	<returns>true or false</returns>
</documentation> */
Lib.elementIsHidden = function (elem) {
	var isHidden = false;
	
	while ( elem && !isHidden) {
		elem = elem.parentNode; 
		if(elem && elem.className && elem.className.indexOf("hide") != -1) { isHidden=true; }
	}
    return isHidden;
}

/* <documentation about="Lib.elementsExists" type="general function">
	<summary>Checks if all the elements with the id specified in the arguments of this function exist; returns true if they do exist; false if one or more do not exist</summary>
	<namespace>Lib</namespace>
	<param type="array of strings" descr="Id's of HTML elements">[array]</param>
	<returns>boolean</returns>
</documentation> */
Lib.elementsExists = function () {
	var result = true;
	for(var i=0; i< arguments.length; i++) {
		if(!document.getElementById(arguments[i])) result=false;
	}
	return result;
}

/* <documentation about="Lib.addStyleSheet" type="general function">
	<summary>Adds stylesheet to the document</summary>
	<namespace>Lib</namespace>
	<param type="string" descr="Relative path to the stylesheet for example '../css/stylesheet.css'">relPath</param>
</documentation> */
Lib.addStyleSheet = function (relPath) {
	if(document.getElementsByTagName("head"))
	{
		var head = document.getElementsByTagName("head")[0]; 
		var newStyle = document.createElement("link");
   		newStyle.setAttribute("type", "text/css");
		newStyle.setAttribute("rel", "stylesheet"); 
		newStyle.setAttribute("href", relPath); 
		head.appendChild(newStyle);
	}
} 

/* <documentation about="Lib.getBodyId" type="general function">
	<summary>Gets  id of body element</summary>
	<namespace>Lib</namespace>
	<returns>id (string)</returns>
</documentation> */
Lib.getBodyId = function ()  {
 	try {
		var body = document.getElementsByTagName("body")[0];
		return body.id;
	} catch (ex){ Lib.errHandler(ex); }	
 }

/* <documentation about="Lib.getNextElement" type="general function">
	<summary>Gets next element in DOM tree; while ignoring text nodes</summary>
	<namespace>Lib</namespace>
	<param type="object" descr="HTML element">elem</param>
	<returns>HTML element</returns>
</documentation> */
Lib.getNextElement = function ( elem ) {
    do { elem = elem.nextSibling; } 
	while ( elem && elem.nodeType != 1 );
    return elem;
}


/* <documentation about="Lib.getPreviousElement" type="general function">
	<summary>Gets previous element in DOM tree; while ignoring text nodes</summary>
	<namespace>Lib</namespace>
	<param type="object" descr="HTML element">elem</param>
	<returns>HTML element</returns>
</documentation> */
Lib.getPreviousElement = function ( elem ) {
	do { elem = elem.previousSibling; }
	while ( elem && elem.nodeType != 1 );
	return elem;
}

/* <documentation about="Lib.getElementsByClassName" type="general function">
	<summary>Used to find HTML elements with certain classname</summary>
	<namespace>Lib</namespace>
	<param type="string" descr="Classname you are looking for">searchClass</param>
	<param type="string" descr="An optional tag name to narrow the search to specific tags e.g. 'a' for links (optional, defaults to ‘*’).">tagName</param>
	<param type="string" descr="An optional object container to search inside. This narrows the scope of the search (optional, defaults to document).">elem</param>
	<returns>Array of HTML elements</returns>
</documentation> */
Lib.getElementsByClassName = function (searchClass, tagName, containerElement) {
	tagName = tagName || "*";
	containerElement = containerElement || document;
	
	var allElements = containerElement.getElementsByTagName(tagName);
	if (!allElements.length &&  tagName == "*" &&  containerElement.all) allElements = containerElement.all;
	
	var elementsFound = new Array();
	var delim = searchClass.indexOf("|") != -1  ? '|' : " ";
	
	var arrClass = searchClass.split(delim);
	for (var i = 0, j = allElements.length; i < j; i++) {
		var arrObjClass = allElements[i].className.split(" ");
		if (delim == " " && arrClass.length > arrObjClass.length) { continue; }
		var c = 0;
		comparisonLoop:
			for (var k = 0, l = arrObjClass.length; k < l; k++) {
				for (var m = 0, n = arrClass.length; m < n; m++) {
					if (arrClass[m] == arrObjClass[k]) c++;
					if (( delim == "|" && c == 1) || (delim == " " && c == arrClass.length)) {
						elementsFound.push(allElements[i]);
					break comparisonLoop;
				}
			}
		}
	}
	return elementsFound;
}

/* <documentation about="Lib.removeElements" type="general function">
	<summary>Used to remove HTML elements</summary>
	<namespace>Lib</namespace>
	<param type="string" descr="An optional tag name to narrow the search to specific tags e.g. 'a' for links (optional, defaults to ‘*’).">tagName</param>
	<param type="string" descr="Classname you are looking for">searchClass</param>
	<param type="string" descr="An optional object container to search inside. This narrows the scope of the search (optional, defaults to document).">elem</param>
</documentation> */
Lib.removeElements = function (tagName, searchClass, containerElement) {
	var elementToRemove = Lib.getElementsByClassName(searchClass, tagName, containerElement);
	
	for(var i=0; i< elementToRemove.length; i++) {
		elementToRemove[i].parentNode.removeChild(elementToRemove[i]);
	}
}

/* <documentation about="Lib.trim" type="general function">
	<summary>Used to trim a variable (removing spaces left and right)</summary>
	<namespace>Lib</namespace>
	<param type="string" descr="Variables that you want to trim">stringToTrim</param>
	<returns>Trimmed variable (string)</returns>
</documentation> */
Lib.trim = function(stringToTrim) {
	return stringToTrim.replace(/^\s+|\s+$/g, '') ;
}
/* ========== END GENERAL FUNCTIONS ========================================================= */

/* ========== SPECIFIC FUNCTIONS ============================================================ */
/* <documentation about="Lib.addOpenInLinksNewWindow" type="specific function">
	<summary>Adds functionality that all links with type='new window' will be opened in a new window</summary>
	<namespace>Lib</namespace>
	<param type="string" descr="Id of parent element; defaults to document if not supplied">parentElementId</param>
</documentation> */
Lib.addOpenInLinksNewWindow = function (parentElementId) {
	//get links in layer #content
	var hyperlinks;
	
	if (!parentElementId || !document.getElementById(parentElementId)) { hyperlinks = document.getElementsByTagName("a"); }
	else { hyperlinks = document.getElementById(parentElementId).getElementsByTagName("a");}
	
	for (var i=0; i < hyperlinks.length; i++) {
		if (hyperlinks[i].getAttribute("type") == "new-window") {
			hyperlinks[i].setAttribute("title", Lib.newWindowToolTipText); 
			hyperlinks[i].onclick = function () { window.open(this.href); return false;  };		
			Lib.eventCache.add(hyperlinks[i], "onclick", function () { window.open(this.href); return false;  }, false); 			
		}
	}
}

/* <documentation about="Lib.disableFormFields" type="specific function">
	<summary>Disables all formfields with alt='read-only'</summary>
	<namespace>Lib</namespace>
</documentation> */
Lib.disableFormFields = function () {
	var inputFields = document.getElementById("content").getElementsByTagName("input");
	for (var i=0; i<inputFields.length; i++) { 
		//alert(inputFields[i].getAttribute("alt"));
		if(inputFields[i].getAttribute("alt") != "read-only") { continue; } //only do the rest type=disable-onload
		inputFields[i].readOnly = true;
		inputFields[i].parentNode.className = "euro read-only"; 
	}
}


/* <documentation about="Lib.clearFormFields" type="specific function">
	<summary>Clears all form fields</summary>
	<namespace>Lib</namespace>
</documentation> */
Lib.clearFormFields = function (formFields) {
	for (var i=0; i<formFields.length; i++) { 
		if(formFields[i].getAttribute("type") == "text") {  formFields[i].value = ""; }
		else if (formFields[i].getAttribute("type") == "checkbox") {  formFields[i].checked = false; }
		else if (formFields[i].getAttribute("type") == "radio") {  formFields[i].checked = false; }
		else if (formFields[i].nodeName.toLowerCase() == "select") {  formFields[i].selectedIndex=0; }
	}
}

/* <documentation about="Lib.addFoldOutPanels" type="specific function (fold out)">
	<summary>Adds functionality 'fold out' panels to form</summary>
	<namespace>Lib</namespace>
	<param type="string" descr="Id of container in which the hidden form layers can be found">containerId</param>
	<param type="string" descr="Text for the 'add layer link'">linkText</param>
	<param type="string" descr="Class of the 'minus link'; this makes positioning of minus link possible (optional: default is 'remove-panel')">removeLinkClassName</param>
</documentation> */
Lib.addFoldOutPanels = function (containerId, linkText, removeLinkClassName) {
	if( !removeLinkClassName ||  removeLinkClassName.lenght == 0) {  removeLinkClassName = "remove-panel"}
	
	Lib.addPanelPlusLink (containerId, linkText, removeLinkClassName);
}


/* <documentation about="Lib.hideAllPanels" type="specific function (fold out)">
	<summary>Hides all folded out panels</summary>
	<namespace>Lib</namespace>	
</documentation> */
Lib.hideAllPanels = function () {
	var panelDivs = Lib.getElementsByClassName("panel", "div");
	for(var i=0; i < panelDivs.length; i++) {
		 panelDivs[i].className = "panel hide";
	}
	//but show add panel
	var addPanelDivs = Lib.getElementsByClassName("add-panel", "div");
	for(var i=0; i < addPanelDivs.length; i++) {
		 addPanelDivs[i].className = "add-panel";
	}	
}

/* <documentation about="Lib.showPanel" type="specific function (fold out)">
	<summary>Shows panel after click on 'add-panel hyperlink'</summary>
	<namespace>Lib</namespace>
	<param type="object" descr="Add panel hyperlink (HTML element)">addPanelLink</param>
</documentation> */
Lib.showPanel = function(addPanelLink) {
	//get all hidden divs
	var panelDivs = Lib.getElementsByClassName("hide", "div", addPanelLink.containerNode);
	
	if(panelDivs && panelDivs.length > 0) { 
		//remove className "hide" to show panel
		panelDivs[0].className = "panel";  
		
		//set focus to first field
		//if(panelDivs[0].getElementsByTagName("input")[0]  && !panelDivs[0].getElementsByTagName("input")[0].disabled) { panelDivs[0].getElementsByTagName("input")[0].focus(); }
						
		//add minus link to this panel
		Lib.addPanelMinusLink(panelDivs[0], addPanelLink.removeLinkClassName);
		
		//if this the last panel: deactivate/hide plus link
		if(panelDivs.length==1) {  addPanelLink.parentNode.className = "add-panel hide"; }		
		
		//add validation fields
		if(Lib.Validation) { Lib.Validation.prepareForm(); }
	}
}

/* <documentation about="Lib.hidePanel" type="specific function (fold out)">
	<summary>Hides panel after click on 'remove-panel hyperlink'</summary>
	<namespace>Lib</namespace>
	<param type="object" descr="Remove panel hyperlink (HTML element)">removePanelLink</param>
</documentation> */
Lib.hidePanel = function(removePanelLink) {
	var removeLinkClassName = removePanelLink.className;
	
	//remove all minus links first
	Lib.removeElements("a", "remove-panel", removePanelLink.panelDiv.parentNode);
	
	//activate addLink (might be deactivated if it was the last panel
	var addLink = document.getElementById(removePanelLink.panelDiv.parentNode.id + "addLink");
	//addLink.className = "";
	addLink.parentNode.className = "add-panel";
	
	//hide panel
	removePanelLink.panelDiv.className = "panel hide";
	
	//clear field of hidden panel
	Lib.clearFormFields(removePanelLink.panelDiv.getElementsByTagName("*"));
	
	//insert minus link:
	var panelDiv = Lib.getPreviousElement(removePanelLink.panelDiv);
	if(panelDiv.className.indexOf("first") == -1) {  Lib.addPanelMinusLink(panelDiv, removeLinkClassName); 	}
	
	//remove validation fields
	if(Lib.Validation) { Lib.Validation.prepareForm(); }
}

/* <documentation about="Lib.addPanelPlusLink" type="specific function (fold out)">
	<summary>Adds 'add panel link' (+ link)</summary>
	<namespace>Lib</namespace>
	<param type="string" descr="Id of container in which the hidden form layers can be found">containerId</param>
	<param type="string" descr="Text for the 'add layer link'">linkText</param>
	<param type="string" descr="Class of the 'minus link'.">removeLinkClassName</param>
</documentation> */
Lib.addPanelPlusLink = function (containerId, linkText, removeLinkClassName) {
	//if fieldset exists
	if(document.getElementById(containerId)) {
		//get fieldset element
		var container = document.getElementById(containerId);
		
		//get last panel
		var allPanels = Lib.getElementsByClassName("hide", "div", container);
		
		if(allPanels && allPanels.length > 0) {
			var lastPanel = allPanels[allPanels.length-1];
		
			//create div
			var div = document.createElement("div");
			div.className = "add-panel";
			
			//create hypelink
			var a = document.createElement("a");
			a.href= "#";
			a.id = containerId + "addLink";
			a.innerHTML = linkText; // add text
			a.containerNode = container;
			a.removeLinkClassName = removeLinkClassName;
			
			//add functionality
			a.onclick = function () { Lib.showPanel(this); return false; }; 
			Lib.eventCache.add(a, "onclick", function () { Lib.showPanel(this); return false; }, false); 			
			
			//add link to div
			div.appendChild(a);
			//add div to form, right after the last Panel
			container.insertBefore(div, Lib.getNextElement(lastPanel));
		}
	}
}

/* <documentation about="Lib.addPanelMinusLink" type="specific function (fold out)">
	<summary>Adds 'remove panel link' (appends 'remove panel link to div class=row-content of panelDiv (parameter))</summary>
	<namespace>Lib</namespace>
	<param type="object" descr="HTML element (container)">panelDiv</param>
	<param type="string" descr="Class of the 'minus link'.">removeLinkClassName</param>
</documentation> */
Lib.addPanelMinusLink = function (panelDiv, removeLinkClassName) {	
	var rowContentDivs = Lib.getElementsByClassName("row-content", "div", panelDiv);
	
	//remove all minus links first
	Lib.removeElements ("a", "remove-panel", panelDiv.parentNode);
	
	//create hypelink
	var a = document.createElement("a");
	a.href= "#";
	a.innerHTML = ""; // add text
	a.className = removeLinkClassName; //add class name
	a.panelDiv = panelDiv;
	
	//add functionality
	a.onclick = function () { Lib.hidePanel(this); return false; }; 
	Lib.eventCache.add(a, "onclick", function () { Lib.hidePanel(this); return false; } , false); 			
	
	//append links to div class="row-content"
	//rowContentDivs[0].appendChild(a);	
	rowContentDivs[0].insertBefore(a, rowContentDivs[0].childNodes[rowContentDivs[0].childNodes.length-1]);
	//alert(rowContentDivs[0].innerHTML)
}

//xxxxxxxxxxxxxxxxxxxxxx  help panel xxxxxxxxxxxxxxxxxxxxxxxxxx
/* <documentation about="Lib.addHelp" type="specific function (help)">
	<summary>Adds panel behaviour to help link (hyperlinks with rel='help')</summary>
	<namespace>Lib</namespace>
</documentation> */
Lib.addHelp = function () {
	var hyperlinks;
	var key; 
	
	//get links in layer #content
	hyperlinks = document.getElementById("content").getElementsByTagName("a");
	//loop through all links in content
	for (var i=0; i<hyperlinks.length; i++) { 
		if(hyperlinks[i].getAttribute("rel") != "help") { continue; } //only do the rest if rel=help
		
		//read the key from the querystring
		hyperlinks[i].key = hyperlinks[i].href.split("#")[1];
		//add behavior
		if(hyperlinks[i].getAttribute("type") == "onfocus") {
			hyperlinks[i].onfocus = function () { Lib.toggleHelpPanel(this); };	//toggle panel
			//safari is special: if you only add onfocus handler; help panel does not open; therefor for safari onclick is added
			if(Lib.safari) { hyperlinks[i].onclick = function () { Lib.toggleHelpPanel(this);  return false; }; }
			else { hyperlinks[i].onclick = function () { return false; }; } //just stop the link
			
			//add event handlers to repository
			Lib.eventCache.add(hyperlinks[i], "onfocus", function () { Lib.toggleHelpPanel(this); }, false); 	
			Lib.eventCache.add(hyperlinks[i], "onclick", function () { return false; }, false); 	
		}
		else {
			hyperlinks[i].onclick = function () { Lib.toggleHelpPanel(this);  return false; }; 	
			if(!Lib.safari) { hyperlinks[i].onfocus = function () { this.className = "help-link focus"; }; 	}
						
			//add event handlers to repository
			Lib.eventCache.add(hyperlinks[i], "onclick", function () { Lib.toggleHelpPanel(this); }, false); 	
			Lib.eventCache.add(hyperlinks[i], "onfocus", function () { this.className = "help-link focus"; }, false); 	
		}
		
		hyperlinks[i].onblur = function () { this.className = "help-link"; }; 		
		Lib.eventCache.add(hyperlinks[i], "onblur", function () { this.className = "help-link"; }, false);				
	}	
}

/* <documentation about="Lib.toggleHelpPanel" type="specific function (help)">
	<summary>Shows/hides help panel</summary>
	<namespace>Lib</namespace>
	<param type="object" descr="HTML element (help link)">hyperlink</param>
</documentation> */ 
Lib.toggleHelpPanel = function (hyperlink) {
	try {
		//get first input element after help: needed if help panel is closed 
		var firstInputElementAfterHelpLink = null;
		if(hyperlink.id == "closeHelp" && hyperlink.parentNode && hyperlink.parentNode.parentNode && hyperlink.parentNode.parentNode.getElementsByTagName("input")[0]) {
			firstInputElementAfterHelpLink = hyperlink.parentNode.parentNode.getElementsByTagName("input")[0];
		}
		
		Lib.removeHelpPanel(); //always remove helpPanel and closeLink from DOM	first
		
		if (hyperlink.id == "closeHelp") {  
			/* if(firstInputElementAfterHelpLink) { 
				firstInputElementAfterHelpLink.focus(); //focus on next input field
				firstInputElementAfterHelpLink.select(); //select next input field
			} */
			return; //stop here if close help link is clicked
		} 
		else { if(!Lib.safari) { hyperlink.className = "help-link focus"; } }
				
		Lib.addHelpPanel(hyperlink); //create helpPanel and closeLink and add to DOM	
		
		// get content: this can be replaced by AJAX call
		var helpContent = document.getElementById(hyperlink.key).innerHTML;
		Lib.helpPanel.innerHTML = helpContent; //add content - call ajax function here
		
		/* 	add open links in new window for help tekst: this is necessary because the links in helpPanel
			are dynamically added; and do not have "open in new window" functionality added yet (for all 
			other links this is done in doCustomCalls - Lib.addOpenInLinksNewWindow("content");	*/
		Lib.addOpenInLinksNewWindow("helpPanel");	
		
		//position helpPanel and link
		var topPosition = 0; //initial value;
		
		var helpPanelHeight = parseInt(Lib.helpPanel.offsetHeight); 					//get height of helpPanel
		var windowHeight = parseInt(Lib.getWindowHeight()); 							//get window heigth
		var hyperlinkTopPosition = parseInt(Lib.findElementPosition(hyperlink)[1]); 	//get top position (y-axis) of hyperlink element
		var scrollTopPage = Lib.getScrollY();											//get scrolling distance
		
		var bottomHelpPanel = helpPanelHeight + hyperlinkTopPosition;					//get natural bottom of help element
		var bottomPage = scrollTopPage + windowHeight;
		
		if(bottomHelpPanel>bottomPage){ 												//if bottom helpPanel exceeds the bottom of page
			topPosition = hyperlinkTopPosition - (bottomHelpPanel - bottomPage);		//replace helpPanel upwards with distance bottomHelpPanel - bottomPage
			if (topPosition < scrollTopPage){ topPosition = scrollTopPage; } 			//if the helpPanel is above the start of the page: put it at scroll height
		}
		else { topPosition = hyperlinkTopPosition-1; }									//if the helpPanel fits: default positioning
		
		//set position helpPanel & closeLink
		Lib.helpPanel.style.top = topPosition + "px";
		Lib.closeLink.style.top = hyperlinkTopPosition + "px";				
	} catch (ex){ Lib.errHandler(ex); }	 
}

/* <documentation about="Lib.addHelpPanel" type="specific function (help)">
	<summary>Adds (creates) help panel and 'close link'</summary>
	<namespace>Lib</namespace>
	<param type="object" descr="HTML element (help link)">hyperlink</param>
</documentation> */ 
Lib.addHelpPanel = function (hyperlink) {
	//define help panel
	Lib.helpPanel = document.createElement("div");
	Lib.helpPanel.id = "helpPanel";
	
	//to avoid flickering in firefox mac
	Lib.helpPanel.style.top = "-9999em";				
	
	//append helppanel as a child of the TH where the help link is in
	hyperlink.parentNode.appendChild(Lib.helpPanel);
	
	//define close link
	Lib.closeLink = document.createElement("a");	
	Lib.closeLink.id = "closeHelp";
	Lib.closeLink.title = Lib.closeLinkTooltip;
	Lib.closeLink.href = "#";
	Lib.closeLink.innerHTML = "<span class=\"text-only\">" + Lib.closeLinkTooltip + "</span>";
	Lib.closeLink.onfocus = function () { this.className = "focus"};
	Lib.closeLink.onblur = function () { this.className = ""};
	Lib.closeLink.onclick = function () { Lib.toggleHelpPanel(this); return false; };
		
	Lib.eventCache.add(Lib.closeLink, "onclick", function () { Lib.toggleHelpPanel(this); return false; }, false); 	
	Lib.eventCache.add(Lib.closeLink, "onfocus", function () { this.className = "focus"}, false); 		
	Lib.eventCache.add(Lib.closeLink, "onblur", Lib.closeLink.onblur = function () { this.className = ""}, false); 			
	
	//to avoid flickering in firefox mac
	Lib.closeLink.style.top = "-9999em";	
	
	//append closeLink as a child of the TH where the help link and the helpPanel is in
	hyperlink.parentNode.appendChild(Lib.closeLink);		
}

/* <documentation about="Lib.removeHelpPanel" type="specific function (help)">
	<summary>Removes helpPanel and closeLink from DOM</summary>
	<namespace>Lib</namespace>
</documentation> */ 
Lib.removeHelpPanel = function () {
	if(document.getElementById("helpPanel")) {
		var parentNode = document.getElementById("helpPanel").parentNode;
		
		/* normally 
			td.removeChild(document.getElementById("closeHelp")); and
			td.removeChild(document.getElementById(helpPanel"));
		  should do the trick but opera has render problem with 
		  td.removeChild(document.getElementById(helpPanel"));: 
		  that why this detour is made: */
		parentNode.removeChild(document.getElementById("closeHelp"));
		var hp = document.getElementById("helpPanel");
		if (!Lib.opera) { parentNode.removeChild(hp); }
		else { //do this for opera
			hp.innerHTML="";
			hp.removeAttribute("id");
			hp.style.display = "none";
		}		
	}
}
//xxxxxxxxxxxxxxxxxxxxxx end help panel xxxxxxxxxxxxxxxxxxxxxxxxxx
/* ========== END SPECIFIC FUNCTIONS ========================================================= */

/* <documentation about="Add eventhandler Lib.eventCache.flush on window unload" type="FUNCTION CALL">
	<summary>Calling Lib.addEvent: Add Lib.eventCache.flush as eventhandler on window onunload: Detach all attached events (solves memory leak in ie)</summary>
</documentation> */
Lib.addEvent(window, "unload", Lib.eventCache.flush);