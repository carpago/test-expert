package nl.carpago.testexpert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.apache.log4j.Logger;

public class InvokeDTO {
	
	//logging
	private static Logger logger = Logger.getLogger(InvokeDTO.class);
	private final String EMPTY_STRING = "";
	
	//input
	private String regel;
	
	//output
	private String constructionFromLine; // regel zonder assignment
	private String collab;
	private String method;
	private List<String> params = new ArrayList<String>();
	private Set<String> collabs;
	
	public InvokeDTO(String regel, Set<String> collabs){
		this.regel = regel;
		System.out.println("regel:>"+regel+"<");
		this.collabs = collabs;
		process();
	}

	
	private void process() {
		
		Scanner scanner = null;
		String declaration = null;
		String construction = null;

		regel = regel.replaceAll("this.", "");
		
		for(String aCollab : collabs) {
			int positionOfCollab = regel.indexOf(aCollab);
			
			if(positionOfCollab > -1) {
				regel = regel.substring(positionOfCollab);
				break;
			}
		}
		
		
		
		
		if (regel.indexOf(" return ") > -1) {
			regel = regel.replaceFirst("return", EMPTY_STRING).trim();
		}
		if (regel.indexOf("=") > -1) {
			scanner = new Scanner(regel).useDelimiter("=");
			declaration = scanner.next().trim();

			if (declaration.indexOf(" ") > -1) {
				Scanner subScanner = null;
				subScanner = new Scanner(declaration).useDelimiter(" ");

				// skip
				String type = subScanner.next();
				String variableName = subScanner.next();

			}
			regel = scanner.next();
		}
		
		scanner = new Scanner(regel).useDelimiter(";");
		construction = scanner.next().trim();
		this.constructionFromLine = construction;
		
		// new
		
		int indexOfFirstDot = construction.indexOf('.');
		// voor temp sol. int starter = construction.indexOf('(') < ch
				//x // hier verder. moet goede beginpunt van regel hebben. en das niet altijd 0 bijvoorbeeld bij if en System.out.println.
				// vanuit '.' 1 of twee haakjes terug.
				// moet maar gewoon met een kleine parser. Eerst andere issues ...
		collab = construction.substring(0, indexOfFirstDot);
		while(collab.indexOf(" ") > -1) {
			collab = collab.substring(collab.indexOf(" ")+1);
		}
		
		construction = construction.substring(indexOfFirstDot+1,construction.length());
		
		int indexOfOpenParenthesis = construction.indexOf('(');
		
		this.method = construction.substring(0,indexOfOpenParenthesis);
		
		construction = construction.substring(indexOfOpenParenthesis+1, construction.length());
		
		int indexOfClosingParenthesis = construction.indexOf('.')-1;
		if(indexOfClosingParenthesis  < 0 ) { // no separator so take last closing parenthesis 
			indexOfClosingParenthesis = construction.lastIndexOf(")");
		}
		
		// this.constructionFromLine = construction.substring(0, indexOfClosingParenthesis);
		
		String paramsVanCollab = construction.substring(0, indexOfClosingParenthesis);
		String[] paramsVanCollabArray = paramsVanCollab.split(",\\s");
		this.params = Arrays.asList(paramsVanCollabArray);
	}
	
	public String getCollabMethodParams() {
		String result = "";
		result += this.getCollab();
		result += ".";
		result += this.getMethod();
		result+= "(";
		
		
		
		String first = EMPTY_STRING;
		String tail = EMPTY_STRING;
		if (!(this.params.size() < 1)) {
			first = this.params.get(0);
		}
		for (int i = 1; i <= this.params.size() - 1; i++) {
			tail += ", " + this.params.get(i);
		}
		
		result  += first + tail;
		
		result += ")";
		
		return result;
	}


	public String getCollab() {
		return collab;
	}


	public String getMethod() {
		return method;
	}


	public String getConstruction() {
		return this.constructionFromLine;
	}


	public List<String> getParams() {
		return params;
	}
	
	
	
	
	
}
