/* <documentation about="ABOUT autosuggest.js" type="GENERAL">
	<summary><![CDATA[ Add this autosuggest.js as an external file to your document and call Lib.AutoSuggest.add(autoSuggestFieldId, data, numerOfItems, onfocus, filterData);.
			
		This autosuggest depends on the main library (library.js).
		Dependencies:
		- namespace Lib & Lib.eventCache {}
		- global variables: Lib.opera, Lib.ie
		- function Lib.findElementPosition(...);  
		- function Lib.trim();]]></summary>
	<namespace>Lib.AutoSuggest</namespace>
</documentation> */
Lib.AutoSuggest = {};

/* <documentation about="Lib.AutoSuggest.autoSuggestItems" type="global variables">
	<summary>Array of autosuggest object. 
		Properties autosuggest object: 
		- data: array of strings
		- isVisible (dropdown is visible or not): bool
		- element (input element): HTML element (object)
		- dropdown (dropdown with suggested values): HTML element (object),
		- highlighted (is selected or not): bool
		Index is id of the textbox.</summary>
	<namespace>Lib.AutoSuggest</namespace>
</documentation> */
Lib.AutoSuggest.autoSuggestItems = new Array();

/* <documentation about="Lib.AutoSuggest.add" type="function">
	<summary>Attachs the autosuggest object to a form input element. Also sets eventhandlers.</summary>
	<namespace>Lib.AutoSuggest</namespace>
	<param type="string" descr="Id autosuggest input element">autoSuggestFieldId</param>
	<param type="array" descr="Array of strings of which to use as the autosuggest data">data</param>
	<param type="integer" descr="Number of items to display in autosuggest box">numberOfItems</param>
	<param type="bool" descr="If true open autosuggest onfocus">onfocus</param>
	<param type="bool" descr="If true data is filtered while typing">filterData</param>
</documentation> */
Lib.AutoSuggest.add = function (autoSuggestFieldId, data, numerOfItems, onfocus, filterData) {
   	Lib.AutoSuggest.autoSuggestItems[autoSuggestFieldId] = {
						"data":data,
						"isVisible":false,
						"element": document.getElementById(autoSuggestFieldId),
						"dropdown":null,
						"highlighted":null};

	Lib.AutoSuggest.autoSuggestItems[autoSuggestFieldId]["element"].setAttribute("autocomplete", "off");
	
	Lib.AutoSuggest.autoSuggestItems[autoSuggestFieldId]["element"].onkeydown  = function(e) {return Lib.AutoSuggest.doOnKeyDown(this.getAttribute("id"), filterData,  e);};
	Lib.eventCache.add(Lib.AutoSuggest.autoSuggestItems[autoSuggestFieldId]["element"], "onkeydown", function(e) {return Lib.AutoSuggest.doOnKeyDown(this.getAttribute("id"), filterData, e);}, false); 	
	
	Lib.AutoSuggest.autoSuggestItems[autoSuggestFieldId]["element"].onkeyup    = function(e) {return Lib.AutoSuggest.doOnKeyUp(this.getAttribute("id"), filterData, e);};
	Lib.eventCache.add(Lib.AutoSuggest.autoSuggestItems[autoSuggestFieldId]["element"], "onkeyup", function(e) {return Lib.AutoSuggest.doOnKeyUp(this.getAttribute("id"), filterData, e);}, false); 	
	   
	Lib.AutoSuggest.autoSuggestItems[autoSuggestFieldId]["element"].onkeypress = function(e) {if (!e) e = window.event; if (e.keyCode == 13 || Lib.opera) return false;};
   	Lib.eventCache.add(Lib.AutoSuggest.autoSuggestItems[autoSuggestFieldId]["element"], "onkeypress", function(e) {if (!e) e = window.event; if (e.keyCode == 13 || Lib.opera) return false;}, false); 	
	   
    Lib.AutoSuggest.autoSuggestItems[autoSuggestFieldId]["element"].onclick    = function(e) {if (!e) e = window.event; e.cancelBubble = true; e.returnValue = false;};
	Lib.eventCache.add(Lib.AutoSuggest.autoSuggestItems[autoSuggestFieldId]["element"], "onclick", function(e) {if (!e) e = window.event; e.cancelBubble = true; e.returnValue = false;}, false); 	
	
	if(onfocus) { 
		Lib.AutoSuggest.autoSuggestItems[autoSuggestFieldId]["element"].onfocus = function() {Lib.AutoSuggest.showSuggest(this.getAttribute("id"), filterData );} 
		Lib.eventCache.add(Lib.AutoSuggest.autoSuggestItems[autoSuggestFieldId]["element"], "onfocus", function() {Lib.AutoSuggest.showSuggest(this.getAttribute("id"), filterData);} , false); 	
	}

    // Hides the dropdowns when document clicked
	/* <documentation about="documentClick" type="'private' function in Lib.AutoSuggest.add">
		<summary>Hides all autosuggest dropdowns when document is clicked.</summary>
		<namespace>Lib.AutoSuggest</namespace>
	</documentation> */
    var documentClick = function() {
       for (autoSuggestFieldId in Lib.AutoSuggest.autoSuggestItems) {
           Lib.AutoSuggest.hideSuggest(autoSuggestFieldId);
       }
    }	
	Lib.addEvent (document, "click", documentClick);
	Lib.eventCache.add(document, "onclick",  documentClick, false); 	

	Lib.AutoSuggest.autoSuggestItems[autoSuggestFieldId]["maxitems"] = numerOfItems;
	Lib.AutoSuggest.autoSuggestItems[autoSuggestFieldId]["firstItemShowing"] = 0;
	Lib.AutoSuggest.autoSuggestItems[autoSuggestFieldId]["lastItemShowing"]  = Lib.AutoSuggest.autoSuggestItems[autoSuggestFieldId]["maxitems"] - 1;
    
    Lib.AutoSuggest.createSuggest(autoSuggestFieldId);
    
    // Prevent select dropdowns showing thru
    if (Lib.ie) {
        Lib.AutoSuggest.autoSuggestItems[autoSuggestFieldId]["iframe"] = document.createElement("iframe");
        Lib.AutoSuggest.autoSuggestItems[autoSuggestFieldId]["iframe"].id = autoSuggestFieldId +"_iframe";
        Lib.AutoSuggest.autoSuggestItems[autoSuggestFieldId]["iframe"].style.position = "absolute";
        Lib.AutoSuggest.autoSuggestItems[autoSuggestFieldId]["iframe"].style.top = "0";
        Lib.AutoSuggest.autoSuggestItems[autoSuggestFieldId]["iframe"].style.left = "0";
        Lib.AutoSuggest.autoSuggestItems[autoSuggestFieldId]["iframe"].style.width = "0px";
        Lib.AutoSuggest.autoSuggestItems[autoSuggestFieldId]["iframe"].style.height = "0px";
        Lib.AutoSuggest.autoSuggestItems[autoSuggestFieldId]["iframe"].style.zIndex = "98";
        Lib.AutoSuggest.autoSuggestItems[autoSuggestFieldId]["iframe"].style.visibility = "hidden";        
        Lib.AutoSuggest.autoSuggestItems[autoSuggestFieldId]["element"].parentNode.insertBefore(Lib.AutoSuggest.autoSuggestItems[autoSuggestFieldId]["iframe"], Lib.AutoSuggest.autoSuggestItems[autoSuggestFieldId]["element"]);
    }
}

/* <documentation about="Lib.AutoSuggest.createSuggest" type="function">
	<summary>Creates the suggest panel.</summary>
	<namespace>Lib.AutoSuggest</namespace>
	<param type="string" descr="This id of the textbox, it is used to identify the correct autosuggest object.">id</param>	
</documentation> */
Lib.AutoSuggest.createSuggest = function (id) {
    Lib.AutoSuggest.autoSuggestItems[id]["dropdown"] = document.createElement("div");
    Lib.AutoSuggest.autoSuggestItems[id]["dropdown"].className = "autosuggest-panel"; 

	//add node to DOM
   	Lib.AutoSuggest.autoSuggestItems[id]["element"].parentNode.insertBefore(Lib.AutoSuggest.autoSuggestItems[id]["dropdown"], Lib.getNextElement(Lib.AutoSuggest.autoSuggestItems[id]["element"]));
    //Lib.AutoSuggest.autoSuggestItems[id]["element"].parentNode.insertBefore(Lib.AutoSuggest.autoSuggestItems[id]["dropdown"], Lib.AutoSuggest.autoSuggestItems[id]["element"]);
    
   	Lib.AutoSuggest.autoSuggestItems[id]["dropdown"].style.zIndex     = "99";
    Lib.AutoSuggest.autoSuggestItems[id]["dropdown"].style.visibility = "hidden";
	 
}


/* <documentation about="Lib.AutoSuggest.showSuggest" type="function">
	<summary>Shows the suggest dropdown</summary>
	<namespace>Lib.AutoSuggest</namespace>
	<param type="string" descr="This id of the textbox, it is used to identify the correct autosuggest object">id</param>	
	<param type="boolean" descr="If true data is filtered while typing">filterData</param>	
</documentation> */
Lib.AutoSuggest.showSuggest = function (id, filterData) {
    Lib.AutoSuggest.hideAllSuggests();
 
 	// Position it
	var left  = Lib.findElementPosition(Lib.AutoSuggest.autoSuggestItems[id]["element"])[0]; 
    var top   =  Lib.findElementPosition(Lib.AutoSuggest.autoSuggestItems[id]["element"])[1] + Lib.AutoSuggest.autoSuggestItems[id]["element"].offsetHeight;
	var width = Lib.AutoSuggest.autoSuggestItems[id]["element"].offsetWidth;
	Lib.AutoSuggest.autoSuggestItems[id]["dropdown"].style.left = left + "px";
    Lib.AutoSuggest.autoSuggestItems[id]["dropdown"].style.top = top + "px"; 
	Lib.AutoSuggest.autoSuggestItems[id]["dropdown"].style.width = width - 2 + "px";
   
    var value = Lib.AutoSuggest.autoSuggestItems[id]["element"].value;
    var toDisplay = new Array();
    var newDiv    = null;
    var text      = null;
    var numItems  = Lib.AutoSuggest.autoSuggestItems[id]["dropdown"].childNodes.length;

	// Remove all child nodes from dropdown
    while (Lib.AutoSuggest.autoSuggestItems[id]["dropdown"].childNodes.length > 0) {
        Lib.AutoSuggest.autoSuggestItems[id]["dropdown"].removeChild(Lib.AutoSuggest.autoSuggestItems[id]["dropdown"].childNodes[0]);
    }

	if(filterData) {
		// Go thru data searching for matches
		for (i=0; i<Lib.AutoSuggest.autoSuggestItems[id]["data"].length; ++i) {
		    if (Lib.AutoSuggest.autoSuggestItems[id]["data"][i].substr(0, value.length) == value) {
		        toDisplay[toDisplay.length] = Lib.AutoSuggest.autoSuggestItems[id]["data"][i];
		    }
		}
	}
	else { toDisplay = Lib.AutoSuggest.autoSuggestItems[id]["data"]; }
    
    // No matches?
    if (toDisplay.length == 0) {
        Lib.AutoSuggest.hideSuggest(id);
        return;
    }

    // Add data to the dropdown layer
    for (i=0; i<toDisplay.length; ++i) {
        var hyperlink = document.createElement("a");
        hyperlink.setAttribute("id", "item" + id + i);
       	hyperlink.setAttribute('index', i);
        hyperlink.style.zIndex = '99';
        
		hyperlink.onmouseover = function() { Lib.AutoSuggest.highLightItemWithMouse(Lib.AutoSuggest.autoSuggestItems[id]["element"].getAttribute("id"), this.getAttribute("index"));};
		Lib.eventCache.add(hyperlink, "onmouseover", function() { Lib.AutoSuggest.highLightItemWithMouse(Lib.AutoSuggest.autoSuggestItems[id]["element"].getAttribute("id"), this.getAttribute("index"));}, false); 	
			
		hyperlink.onclick = function() { Lib.AutoSuggest.setValue(Lib.AutoSuggest.autoSuggestItems[id]["element"].getAttribute("id")); Lib.AutoSuggest.hideSuggest(Lib.AutoSuggest.autoSuggestItems[id]["element"].getAttribute("id"));}; 
		Lib.eventCache.add(hyperlink, "onclick", function() { Lib.AutoSuggest.setValue(Lib.AutoSuggest.autoSuggestItems[id]["element"].getAttribute("id")); Lib.AutoSuggest.hideSuggest(Lib.AutoSuggest.autoSuggestItems[id]["element"].getAttribute("id"));}, false); 	
	
        var text   = document.createTextNode(toDisplay[i]);
        hyperlink.appendChild(text);
        
        Lib.AutoSuggest.autoSuggestItems[id]["dropdown"].appendChild(hyperlink);
    }
        
    /* Too many items?*/
    if (toDisplay.length > Lib.AutoSuggest.autoSuggestItems[id]['maxitems']) { Lib.AutoSuggest.autoSuggestItems[id]['dropdown'].style.height = (Lib.AutoSuggest.autoSuggestItems[id]['maxitems'] * hyperlink.offsetHeight) +"px";} 
	else {  Lib.AutoSuggest.autoSuggestItems[id]['dropdown'].style.height = ""; }

    
    //Set left/top in case of document movement/scroll/window resize etc
    Lib.AutoSuggest.autoSuggestItems[id]["dropdown"].style.left = Lib.findElementPosition(Lib.AutoSuggest.autoSuggestItems[id]["element"])[0]; 
    Lib.AutoSuggest.autoSuggestItems[id]["dropdown"].style.top  = Lib.findElementPosition(Lib.AutoSuggest.autoSuggestItems[id]["element"])[1] + Lib.AutoSuggest.autoSuggestItems[id]["element"].offsetHeight;

    // Show the iframe for IE
	 if (Lib.ie) {
		Lib.AutoSuggest.autoSuggestItems[id]["iframe"].style.top    = Lib.AutoSuggest.autoSuggestItems[id]["dropdown"].style.top;
        Lib.AutoSuggest.autoSuggestItems[id]["iframe"].style.left   = Lib.AutoSuggest.autoSuggestItems[id]["dropdown"].style.left;
        Lib.AutoSuggest.autoSuggestItems[id]["iframe"].style.width  = Lib.AutoSuggest.autoSuggestItems[id]["dropdown"].offsetWidth;
        Lib.AutoSuggest.autoSuggestItems[id]["iframe"].style.height = Lib.AutoSuggest.autoSuggestItems[id]["dropdown"].offsetHeight;
        Lib.AutoSuggest.autoSuggestItems[id]["iframe"].style.visibility = 'visible';
    }

    // Show dropdown
    if (!Lib.AutoSuggest.autoSuggestItems[id]["isVisible"]) {
        Lib.AutoSuggest.autoSuggestItems[id]["dropdown"].style.visibility = 'visible';
		Lib.AutoSuggest.autoSuggestItems[id]["isVisible"] = true;
    }
    
    // If now showing less items than before, reset the highlighted value
    if (Lib.AutoSuggest.autoSuggestItems[id]["dropdown"].childNodes.length != numItems) {
        Lib.AutoSuggest.autoSuggestItems[id]["highlighted"] = null;
    }
}

/* <documentation about="Lib.AutoSuggest.hideSuggest" type="function">
	<summary>Hides the suggest driopdown.</summary>
	<namespace>Lib.AutoSuggest</namespace>
	<param type="string" descr="This id of the textbox, it is used to identify the correct autosuggest object">id</param>	
</documentation> */
Lib.AutoSuggest.hideSuggest = function (id) {
    if (Lib.AutoSuggest.autoSuggestItems[id]["iframe"]) {
        Lib.AutoSuggest.autoSuggestItems[id]["iframe"].style.visibility = "hidden";
    }

    Lib.AutoSuggest.autoSuggestItems[id]["dropdown"].style.visibility = "hidden";
	Lib.AutoSuggest.autoSuggestItems[id]["dropdown"].style.height = "auto"; //needed for bug on MAC FF
	Lib.AutoSuggest.autoSuggestItems[id]["highlighted"] = null;
    Lib.AutoSuggest.autoSuggestItems[id]["isVisible"]   = false;
}

/* <documentation about="Lib.AutoSuggest.hideAllSuggests" type="function">
	<summary>Hides the all suggest driopdowns.</summary>
	<namespace>Lib.AutoSuggest</namespace>
</documentation> */
Lib.AutoSuggest.hideAllSuggests = function () {
    for (id in Lib.AutoSuggest.autoSuggestItems) {
        Lib.AutoSuggest.hideSuggest(id);
    }
}

/* <documentation about="Lib.AutoSuggest.highLightItemWithMouse" type="function">
	<summary>Highlights a specific autosuggest item when mouse is clicked.</summary>
	<namespace>Lib.AutoSuggest</namespace>
	<param type="string" descr="This id of the textbox, it is used to identify the correct autosuggest object">id</param>	
	<param type="int" descr="The index of the element in the suggest to highlight">index</param>	
</documentation> */
Lib.AutoSuggest.highLightItemWithMouse = function (id, index){
    if (Lib.AutoSuggest.autoSuggestItems[id]["dropdown"].childNodes[index]) {
        for (var i=0; i<Lib.AutoSuggest.autoSuggestItems[id]["dropdown"].childNodes.length; ++i) {
            if (Lib.AutoSuggest.autoSuggestItems[id]["dropdown"].childNodes[i].className == 'autosuggest-item-highlighted') {
                Lib.AutoSuggest.autoSuggestItems[id]["dropdown"].childNodes[i].className = "";
            }
        }
        
        Lib.AutoSuggest.autoSuggestItems[id]["dropdown"].childNodes[index].className = 'autosuggest-item-highlighted';
        Lib.AutoSuggest.autoSuggestItems[id]["highlighted"] = index;
    }
}


/* <documentation about="Lib.AutoSuggest.highLightItemWithKeyBoard" type="function">
	<summary>Highlights a specific autosuggest item when user uses keyboard.</summary>
	<namespace>Lib.AutoSuggest</namespace>
	<param type="string" descr="This id of the textbox, it is used to identify the correct autosuggest object">id</param>	
	<param type="integer" descr="The index of the element in the suggest to highlight">index</param>	
</documentation> */
Lib.AutoSuggest.highLightItemWithKeyBoard = function (id, index)
{
    if(Lib.AutoSuggest.autoSuggestItems[id]["dropdown"].childNodes.length ==0) { return; }
	
	// Out of bounds checking
    if (index == 1 && Lib.AutoSuggest.autoSuggestItems[id]["highlighted"] == Lib.AutoSuggest.autoSuggestItems[id]["dropdown"].childNodes.length - 1) {
        Lib.AutoSuggest.autoSuggestItems[id]["dropdown"].childNodes[Lib.AutoSuggest.autoSuggestItems[id]["highlighted"]].className = "";
        Lib.AutoSuggest.autoSuggestItems[id]["highlighted"] = null;
    
    } else if (index == -1 && Lib.AutoSuggest.autoSuggestItems[id]["highlighted"] == 0 ) {
        Lib.AutoSuggest.autoSuggestItems[id]["dropdown"].childNodes[0].className = "";
        Lib.AutoSuggest.autoSuggestItems[id]["highlighted"] = Lib.AutoSuggest.autoSuggestItems[id]["dropdown"].childNodes.length;
    }

    // Nothing highlighted at the moment
    if (Lib.AutoSuggest.autoSuggestItems[id]["highlighted"] == null ) {
        Lib.AutoSuggest.autoSuggestItems[id]["dropdown"].childNodes[0].className = 'autosuggest-item-highlighted';
        Lib.AutoSuggest.autoSuggestItems[id]["highlighted"] = 0;
    } 
	else {
        if (Lib.AutoSuggest.autoSuggestItems[id]["dropdown"].childNodes[Lib.AutoSuggest.autoSuggestItems[id]["highlighted"]]) {
            Lib.AutoSuggest.autoSuggestItems[id]["dropdown"].childNodes[Lib.AutoSuggest.autoSuggestItems[id]["highlighted"]].className = "";
        }

        var newIndex = Lib.AutoSuggest.autoSuggestItems[id]["highlighted"] + index;

        if (Lib.AutoSuggest.autoSuggestItems[id]["dropdown"].childNodes[newIndex]) {
            Lib.AutoSuggest.autoSuggestItems[id]["dropdown"].childNodes[newIndex].className = 'autosuggest-item-highlighted';
            
            Lib.AutoSuggest.autoSuggestItems[id]["highlighted"] = newIndex;
        }
    }
}

/* <documentation about="Lib.AutoSuggest.setValue" type="function">
	<summary>Uses the value of the highlighted item to set value in text box.</summary>
	<namespace>Lib.AutoSuggest</namespace>
	<param type="string" descr="This id of the textbox, it is used to identify the correct autosuggest object">id</param>	
</documentation> */
Lib.AutoSuggest.setValue = function (id) {
  Lib.AutoSuggest.autoSuggestItems[id]["element"].value = Lib.trim(Lib.AutoSuggest.autoSuggestItems[id]["dropdown"].childNodes[Lib.AutoSuggest.autoSuggestItems[id]["highlighted"]].innerHTML);
}

/* <documentation about="Lib.AutoSuggest.scrollCheck" type="function">
	<summary>Checks if the dropdown needs scrolling and acts accodingly.</summary>
	<namespace>Lib.AutoSuggest</namespace>
	<param type="string" descr="This id of the textbox, it is used to identify the correct autosuggest object">id</param>	
</documentation> */
Lib.AutoSuggest.scrollCheck = function (id)
{
    var hyperlinks = Lib.AutoSuggest.autoSuggestItems[id]["dropdown"].getElementsByTagName("a");
	var hyperlinkHeight = 15; 
	if(hyperlinks[0]) { hyperlinkHeight  = hyperlinks[0].offsetHeight; }
	
	// Scroll down, or wrapping around from scroll up
    if (Lib.AutoSuggest.autoSuggestItems[id]["highlighted"] > Lib.AutoSuggest.autoSuggestItems[id]["lastItemShowing"]) {
        Lib.AutoSuggest.autoSuggestItems[id]["firstItemShowing"] = Lib.AutoSuggest.autoSuggestItems[id]["highlighted"] - (Lib.AutoSuggest.autoSuggestItems[id]["maxitems"] - 1);
        Lib.AutoSuggest.autoSuggestItems[id]["lastItemShowing"]  = Lib.AutoSuggest.autoSuggestItems[id]["highlighted"];
    }
    
    // Scroll up, or wrapping around from scroll down
    if (Lib.AutoSuggest.autoSuggestItems[id]["highlighted"] < Lib.AutoSuggest.autoSuggestItems[id]["firstItemShowing"]) {
        Lib.AutoSuggest.autoSuggestItems[id]["firstItemShowing"] = Lib.AutoSuggest.autoSuggestItems[id]["highlighted"];
        Lib.AutoSuggest.autoSuggestItems[id]["lastItemShowing"]  = Lib.AutoSuggest.autoSuggestItems[id]["highlighted"] + (Lib.AutoSuggest.autoSuggestItems[id]["maxitems"] - 1);
    }
    
    Lib.AutoSuggest.autoSuggestItems[id]["dropdown"].scrollTop = Lib.AutoSuggest.autoSuggestItems[id]["firstItemShowing"] * hyperlinkHeight;
}

/* <documentation about="Lib.AutoSuggest.doOnKeyDown" type="function">
	<summary>Function which handles the onkeydown event.</summary>
	<namespace>Lib.AutoSuggest</namespace>
	<param type="string" descr="This id of the textbox, it is used to identify the correct autosuggest object">id</param>	
	<param type="bool" descr="If true data is filtered while typing">filterData</param>
</documentation> */
Lib.AutoSuggest.doOnKeyDown = function (id, filterData) {
    // Mozilla: get event
    if (arguments[2] != null) { event = arguments[2]; }

    var keyCode = event.keyCode;

    switch (keyCode) {
		case 13:// Return/Enter
            if (Lib.AutoSuggest.autoSuggestItems[id]["highlighted"] != null) {
                Lib.AutoSuggest.setValue(id);
                Lib.AutoSuggest.hideSuggest(id);
            }            
            event.returnValue = false;
            event.cancelBubble = true;
            break;
		case 27:// Escape
            Lib.AutoSuggest.hideSuggest(id);
            event.returnValue = false;
            event.cancelBubble = true;
            break;
        case 38:// Up arrow
            if (!Lib.AutoSuggest.autoSuggestItems[id]["isVisible"]) {
                Lib.AutoSuggest.showSuggest(id, filterData);
            }            
            Lib.AutoSuggest.highLightItemWithKeyBoard(id, -1);
            Lib.AutoSuggest.scrollCheck(id, -1);
            return false;
            break;
		case 9:// Tab
            if (Lib.AutoSuggest.autoSuggestItems[id]["isVisible"]) {
                Lib.AutoSuggest.hideSuggest(id);
            }
            return;
        case 40: // Down arrow
            if (!Lib.AutoSuggest.autoSuggestItems[id]["isVisible"]) {
                Lib.AutoSuggest.showSuggest(id, filterData);
            }            
            Lib.AutoSuggest.highLightItemWithKeyBoard(id, 1);
            Lib.AutoSuggest.scrollCheck(id, 1);
            return false;
            break;
    }
}

/* <documentation about="Lib.AutoSuggest.doOnKeyDown" type="function">
	<summary>Function which handles the onkeyup event.</summary>
	<namespace>Lib.AutoSuggest</namespace>
	<param type="string" descr="This id of the textbox, it is used to identify the correct autosuggest object">id</param>	
	<param type="bool" descr="If true data is filtered while typing">filterData</param>
</documentation> */
Lib.AutoSuggest.doOnKeyUp = function (id, filterData) {
    // Mozilla: catch the event
    if (arguments[2] != null) { event = arguments[2]; }

    var keyCode = event.keyCode;
	
    switch (keyCode) {
        case 13:// Return/Enter
            event.returnValue = false;
            event.cancelBubble = true;
            break;
        case 27:// Escape
            Lib.AutoSuggest.hideSuggest(id);
            event.returnValue = false;
            event.cancelBubble = true;
            break;        
        case 38:// Up arrow
        case 40:// Down arrow
            return false;
            break;
        default:
            Lib.AutoSuggest.showSuggest(id, filterData);
            break;
    }
}
