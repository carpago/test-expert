<!--
/*
 * Web straat java script librarie die er voor zorgt dat tabs een "drop down" menu kunnen hebben.
 * 
 */

WEBSTRAAT.tabs = {};
WEBSTRAAT.tabs.containerpos	= null;
WEBSTRAAT.tabs.delayhide = null;
WEBSTRAAT.tabs.tablayer = [null,null,null,null,null];
WEBSTRAAT.tabs.tabfocus = [null,null,null,null,null];
WEBSTRAAT.tabs.MAXLEVEL = 5;

WEBSTRAAT.tabs.init = function() {
	// zoek de positie op van de content-tab div
	WEBSTRAAT.tabs.containerpos = Lib.findElementPosition($('content-tab'));
	var oNav = $('content');
	WEBSTRAAT.tabs.attach(oNav);
}
WEBSTRAAT.tabs.attach = function(oParent) {		
	var oas = oParent.getElementsByTagName('a');
	
	var i;
	for (i=0;i<oas.length;i++) {
		var oA = oas[i];
		oA.onmouseover = WEBSTRAAT.tabs.mouseover;
		oA.onmouseout = WEBSTRAAT.tabs.mouseout;
		if (WEBSTRAAT.tabs.getsubtab(oA)) {
			oA.className = 'subtab';
		}
	}
}

WEBSTRAAT.tabs.getsubtab = function(oA) {
	var sLayerId = oA.getAttribute("rel");
	if (sLayerId) {
		return $(sLayerId);
	}
	return null;
}

/*
 * Mouse over event, deze toont de submenu.
 */
WEBSTRAAT.tabs.mouseover = function() {	WEBSTRAAT.tabs.mouseover_f(this); }
WEBSTRAAT.tabs.mouseover_f = function(oA) {
	
	var iLevel = WEBSTRAAT.tabs.getlevel(oA);
	var oSubLayer = WEBSTRAAT.tabs.getsubtab(oA);

	// cancel tab out
	if (WEBSTRAAT.tabs.delayhide) {
		clearTimeout(WEBSTRAAT.tabs.delayhide);
		WEBSTRAAT.tabs.delayhide = null;
	}
	
	// drop subtabs that lost focus,
	//  if there is a subtab and the subtab didn't change (another dropdown), then maintain it
	var hidefrom = iLevel+1;
	if (oSubLayer && oSubLayer==WEBSTRAAT.tabs.tablayer[hidefrom])
		hidefrom++; // maintain the subtab if any
	WEBSTRAAT.tabs.hide(hidefrom);

	// clear previous subtab focus if any
	if (WEBSTRAAT.tabs.tabfocus[iLevel] && WEBSTRAAT.tabs.tabfocus[iLevel]!=oA) {
		WEBSTRAAT.tabs.tabfocus[iLevel].className = 'subtab';
	}

	// open a sub tab ?
	if (oSubLayer) {
		
		// set subtab focus
		oA.className = 'subtabfocus';
		WEBSTRAAT.tabs.tabfocus[iLevel] = oA;
		
		// clear subtab's last subtab focus if any
		if (WEBSTRAAT.tabs.tabfocus[iLevel+1]) {
			WEBSTRAAT.tabs.tabfocus[iLevel+1].className = 'subtab';
			WEBSTRAAT.tabs.tabfocus[iLevel+1] = null;
		}
		
		// position subtab down if root level, or to the side
		if (iLevel<1) {
			var oLI = WEBSTRAAT.tabs.getParent(oA,'li');
			var pos = Lib.findElementPosition(oLI);
			if(oSubLayer.offsetWidth < oLI.offsetWidth) {
				oSubLayer.style.width = oLI.offsetWidth+'px';
				oSubLayer.getElementsByTagName('table')[0].style.width = '100%';
			}
		
		} else {
			// ie/fox correct offset from the UL (!?!?!?)			
			var oUL = $(tabs.cssjs.NAVCONTAINER).getElementsByTagName('ul')[0];
			var posUL = Lib.findElementPosition(oUL);
			var oTABLE = WEBSTRAAT.tabs.getParent(oA,'table');
			var pos = Lib.findElementPosition(WEBSTRAAT.tabs.getParent(oA,'td'));
			// position sub tab a little bit overlap & down
			pos[0] = pos[0]-posUL[0]+oTABLE.offsetWidth-4;
			pos[1] = pos[1]-WEBSTRAAT.tabs.containerpos[1]+1;
		}
		WEBSTRAAT.tabs.attach(oSubLayer);
		WEBSTRAAT.tabs.displayLayer(oSubLayer, pos, iLevel+1);
	}

}

WEBSTRAAT.tabs.getlevel = function(oA) {
	var parentDiv = WEBSTRAAT.tabs.getParent(oA,'div');		
	if ( /level1/.test(parentDiv.className) )
		return 1;
	else if ( /level2/.test(parentDiv.className) )
		return 2;
	else if ( /level3/.test(parentDiv.className) )
		return 3;
	return 0;
}

/*
 * Mouse out event deze hide de submenu
 */
WEBSTRAAT.tabs.mouseout = function() { WEBSTRAAT.tabs.mouseout_f(this); }
WEBSTRAAT.tabs.mouseout_f = function(oA) {
	var iLevel = WEBSTRAAT.tabs.getlevel(oA);
	if (!WEBSTRAAT.tabs.delayhide) {
		WEBSTRAAT.tabs.delayhide = setTimeout("WEBSTRAAT.tabs.delayedhide()", 500);//msec
	} 
}


// pos = [x, y]
WEBSTRAAT.tabs.displayLayer = function(oLayer, pos, iLevel) {
	oLayer.style.left = pos[0]+1+'px';
	oLayer.style.top = pos[1]+22+'px';
	oLayer.style.position = 'absolute';
	oLayer.style.display = 'block';
	oLayer.style.visibility = 'visible';
	this.tablayer[iLevel] = oLayer;
}

WEBSTRAAT.tabs.delayedhide = function() {
	WEBSTRAAT.tabs.hide(1);
	WEBSTRAAT.tabs.delayhide = null;
}

WEBSTRAAT.tabs.hide = function(iLevel) {
	var i;
	for (i=iLevel;i<WEBSTRAAT.tabs.MAXLEVEL;i++)
	{
		var oLayer = WEBSTRAAT.tabs.tablayer[i];
		if (oLayer) {
			oLayer.style.visibility = 'hidden';
			WEBSTRAAT.tabs.tablayer[i] = null;
		}
	}
	// clear the focus on the tab head		
	if (iLevel<=1) {
		if (WEBSTRAAT.tabs.tabfocus[0]) {
			WEBSTRAAT.tabs.tabfocus[0].className = '';
		}
	}
}
WEBSTRAAT.tabs.getParent = function(el, sTagName)
{
	if (el == null) {
		return null;
	}
	// gecko bug, supposed to be uppercase
	else if (el.nodeType == 1 && el.tagName.toLowerCase()==sTagName)
	{
		return el;
	}else{
		return WEBSTRAAT.tabs.getParent(el.parentNode, sTagName);
	}
}
function $() {
	var elements = new Array();
	
	for (var i = 0; i < arguments.length; i++) {
		var element = arguments[i];
		if (typeof element == 'string')
			element = document.getElementById(element);
		
		if (arguments.length == 1)
			return element;
	
		elements.push(element);
	}
	
	return elements;
}
Lib.addEvent(window, "load", WEBSTRAAT.tabs.init);
Lib.eventCache.add(window, "load", WEBSTRAAT.tabs.init, false);
//-->