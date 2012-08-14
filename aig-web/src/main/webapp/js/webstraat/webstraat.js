<!--
/*
 * Webstraat javascript definities.
 * 
 * Generieke functies om css classes aan een tag te kunnen toevoegen verwijderen opvragen etc.
 */
WEBSTRAAT = {};

WEBSTRAAT.css = {};

WEBSTRAAT.css.classnames = function(element) {
	return element.className.split(/\s+/);
}
	
WEBSTRAAT.css.set = function(element, s) {
	element.className = s;
}
	
WEBSTRAAT.css.add = function(element, s) {
	if (WEBSTRAAT.css.has(element,s)) return;// minimize refresh (for buggy IE)
	
	var classes = WEBSTRAAT.css.classnames(element);
	for (var i=0;i<classes.length;i++) {
		if (classes[i]=='s') return;
	}
	var newclasses = classes.concat(s).join(' ');
	element.className = newclasses;
}
	
WEBSTRAAT.css.has = function(element, s) {
	var classes = WEBSTRAAT.css.classnames(element);
	for (var i=0;i<classes.length;i++) {
		if (classes[i]==s) {
			return true;
		}
	}
	return false;
}

WEBSTRAAT.css.remove = function(element, s) {
	if (!WEBSTRAAT.css.has(element,s)) return;// minimize refresh (for buggy IE)
	
	var classes = WEBSTRAAT.css.classnames(element);
	var newclasses = '';
	for (var i=0;i<classes.length;i++) {
		if (classes[i]!=s) {
			newclasses += (i>0?' ':'')+classes[i];
		}
	}
	element.className = newclasses;
}

WEBSTRAAT.css.replace = function(element, sold, snew) {
	var classes = WEBSTRAAT.css.classnames(element);
	var newclasses = '';
	for (var i=0;i<classes.length;i++) {
		if (classes[i]!=sold) {
			newclasses += (i>0?' ':'')+classes[i];
		}
	}
	element.className = newclasses+' '+snew;
}



//-->