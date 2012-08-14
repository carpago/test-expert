/* <documentation about="ABOUT validation.js" type="GENERAL">
	<summary>This file is a library: it contains general functions used for tform validation.
		No page initialisation calls are made in this file. Make your function calls in the specific javascript files, in the [namespace].init()
	</summary>
	<namespace>Lib.Validation</namespace>
</documentation> */
Lib.Validation = {};

Lib.Validation.validationElements; 
Lib.Validation.ulErrorList;

Lib.Validation.init = function() {
	try {
		var form = Lib.Validation.prepareForm();
	
		form.onsubmit = function () { return Lib.Validation.validateForm(this); };
		Lib.eventCache.add(form, "onsubmit", function () { return Lib.Validation.validateForm(this); }, false); 			
	} 
	catch (ex){ Lib.errHandler(ex); }	
}

Lib.Validation.validateForm = function (form) {
	try {
		Lib.Validation.removeMessages();
		
		var validationText  = "";
		var alertText = "";
		var isValid = true;
				
		for(var i=0; i<Lib.Validation.validationElements.length;i++) {
			var sortOfValidation = Lib.Validation.validationElements[i].getAttribute("validation").split(";");
			var validationText = Lib.Validation.validationElements[i].getAttribute("validationText").split(";");
			
			var fieldIsInvalid=false;
			
			for(var j=0; j<sortOfValidation.length; j++) {
				fieldIsInvalid = Lib.Validation.validateField(Lib.Validation.validationElements[i] , Lib.trim(sortOfValidation[j]), validationText[j], fieldIsInvalid);				
				if(fieldIsInvalid) { break; }
			}
			
			if(fieldIsInvalid) { isValid = false; }
		}
		if(!isValid) {
			Lib.Validation.showErrors();		
			
			//reset
			isValid = true;
	
			return false;	
		}
		return true;
		
	} 
	catch (ex){ Lib.errHandler(ex); }	
}

Lib.Validation.showErrors = function () {
	var insertBeforeElement = Lib.getElementsByClassName("buttons", "fieldset")[0];
	
	var prevElem = Lib.getPreviousElement(insertBeforeElement);
	if(prevElem.nodeName.toLowerCase() == "br") { insertBeforeElement.parentNode.removeChild(prevElem); }
	
	insertBeforeElement.parentNode.insertBefore(Lib.Validation.ulErrorList, insertBeforeElement);
}		

Lib.Validation.validateField = function (formElement, sortOfValidation, validationText, fieldIsInvalid) {
	var elementsValue = Lib.trim(formElement.value);
	
	switch(sortOfValidation)
	{
		case "required":
  			fieldIsInvalid = !Lib.Validation.isNotEmpty(elementsValue);
			break;  
		case "integer":
  			fieldIsInvalid = !Lib.Validation.isInteger(elementsValue);
			break;  
		case "radiobuttons":
  			fieldIsInvalid = !Lib.Validation.radioButtonIsChecked(formElement);		
			break;  
		default:
			Lib.debugAlert("Validation not recognized (" + sortOfValidation + ")");
	} 
	
	if(fieldIsInvalid) { Lib.Validation.addError(formElement, validationText); }

	return fieldIsInvalid;
}



Lib.Validation.prepareForm = function () {
	Lib.Validation.validationElements = new Array();
	
	var form = document.getElementsByTagName("form")[0];		
	
	var lastFieldset;
	
	for(var i=0; i< form.elements.length; i++) {
		//look for fields with attribute "validation" and make sure the attribute is not empty
		if(form.elements[i].getAttribute("validation") && form.elements[i].getAttribute("validation").length!=0 && !Lib.elementIsHidden(form.elements[i])) {
			//add field to collection
			Lib.Validation.validationElements[Lib.Validation.validationElements.length] = form.elements[i];			
		}		
	}
	return form;
}


Lib.Validation.removeMessages = function () {
	//remove ul
	if(Lib.Validation.ulErrorList) { 
		Lib.Validation.ulErrorList.parentNode.removeChild(Lib.Validation.ulErrorList);
	}	
	Lib.Validation.ulErrorList = null;
	
	//remove strong.errorMessage
	var strongs = Lib.getElementsByClassName("errorMessage", "strong");
	for(var i=0; i<strongs.length; i++) {
		strongs[i].parentNode.removeChild(strongs[i]);
	}
}

Lib.Validation.addError = function (formField, validationText) {
	if(!Lib.Validation.ulErrorList) { 
		Lib.Validation.ulErrorList = document.createElement("ul");
		Lib.Validation.ulErrorList.className = "errorMessages";
	}
	
	var li = document.createElement("li");
	li.innerHTML = validationText;
	Lib.Validation.ulErrorList.appendChild(li);
	
	
	if(formField.getAttribute("type") == "radio") { formField = formField.parentNode; }
	
	var strong = document.createElement("strong");
	strong.innerHTML = "!";
	strong.className = "errorMessage";
	formField.parentNode.insertBefore(strong, formField);
	
	return true;
}				

/* ========== START VALIDATION SCRIPTS ====================================================== */
/* <documentation about="Lib.Validation.isInteger" type="validation function">
	<summary>Validates that a string contains only  valid integer number. True if valid, otherwise false.</summary>
	<namespace>Lib</namespace>
	<param type="string" descr="Variable that you want to validate">elementValue</param>
	<returns>boolean</returns> 
</documentation> */
Lib.Validation.isInteger = function (elementValue) {
	if(elementValue == "-") { return true; }
	
	var re = new RegExp(/^-{0,1}\d+$/);
  	return elementValue.match(re);
}

Lib.Validation.isNotEmpty = function (elementValue) {
	if(elementValue.length == 0) { return false; }
	else return true;
}

Lib.Validation.radioButtonIsChecked = function (formElement) {
	var radioButtonGroup = document.getElementsByName(formElement.getAttribute("name"));
	var fieldIsInvalid=true;		
	for (var i=0; i<radioButtonGroup.length; i++) {
    	if (radioButtonGroup[i].checked) { fieldIsInvalid=false; }
    }
	return !fieldIsInvalid;
}
/* ========== END VALIDATION SCRIPTS ======================================================== */

/* <documentation about="Lib.Validation.init" type="FUNCTION CALL">
	<summary>Calling Lib.addEvent: Add Lib.Validation.init as eventhandler on window onload event</summary>
</documentation> */
Lib.addEvent(window, "load", Lib.Validation.init);
Lib.eventCache.add(window, "load", Lib.Validation.init, false); 	

