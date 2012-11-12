package nl.carpago.testexpert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;

public class InvokeDTO {

	// logging
	private static Logger logger = Logger.getLogger(InvokeDTO.class);
	private final String EMPTY_STRING = "";

	// input
	private String regel;

	// output
	private String collab;
	private String method;
	private List<String> params = new ArrayList<String>();
	private String paramsAsString = "";
	private Set<String> collabs; // contains the current collabs from the
									// generated class for test class.
	
	public InvokeDTO(String regel, Set<String> collabs) {
		this.regel = regel.trim();
		this.collabs = collabs;
		process();
	}

	private void process() {

		regel = regel.replaceAll("this.", "");
		int positionOfCollab;

		for (String aCollab : collabs) {
			positionOfCollab = regel.toLowerCase().indexOf("get"+aCollab.toLowerCase()+"()");

			if (positionOfCollab > -1) {
				regel = regel.substring(positionOfCollab);
				break;
			}
			else {
				positionOfCollab = regel.indexOf(aCollab);
				if(positionOfCollab > -1) {
					regel = regel.substring(positionOfCollab);
					break;
				}
			}
		}

		int indexDotAfterCollab = regel.indexOf('.');

		this.collab = this.regel.substring(0, indexDotAfterCollab);
		this.regel = this.regel.substring(indexDotAfterCollab + 1);

		int indexOpenParenthesisAfterMethod = this.regel.indexOf('(');

		this.method = this.regel.substring(0, indexOpenParenthesisAfterMethod);
		this.regel = this.regel.substring(indexOpenParenthesisAfterMethod);

		StringBuilder params = new StringBuilder();
		int level = 0;
		outer:
		for(int i = 0;i<this.regel.length();i++) {
			char element = this.regel.charAt(i);
			
			switch (element) {
			case '(':
				if(level != 0) {
					params.append(element);
				}
				level++;
				break;
			case ')':
				level--;
				if(level == 0) {
					break outer;
				}
				else {
					params.append(element);
				}
				break;
			default:
				params.append(element);
				break;
			}
		}
		
		String[] paramsVanCollabArray = params.toString().split(",\\s");
		this.paramsAsString = params.toString();
		this.params = Arrays.asList(paramsVanCollabArray);
	}

	public String getCollabMethodParams() {
		String result = this.collab+"."+this.method+"("+this.paramsAsString+")";
		return result;
	}

	public String getCollab() {
		return collab;
	}

	public String getMethod() {
		return method;
	}

	public List<String> getParams() {
		return params;
	}

}
