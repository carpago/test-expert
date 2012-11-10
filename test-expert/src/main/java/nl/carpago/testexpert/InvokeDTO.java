package nl.carpago.testexpert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

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
		System.out.println("regel:>" + regel + "<");
		this.collabs = collabs;
		process();
	}

	private void process() {

		regel = regel.replaceAll("this.", "");

		for (String aCollab : collabs) {
			int positionOfCollab = regel.indexOf(aCollab);

			if (positionOfCollab > -1) {
				regel = regel.substring(positionOfCollab);
				break;
			}
		}

		int indexDotAfterCollab = regel.indexOf('.');

		this.collab = this.regel.substring(0, indexDotAfterCollab);
		this.regel = this.regel.substring(indexDotAfterCollab + 1);

		int indexOpenParenthesisAfterMethod = this.regel.indexOf('(');

		this.method = this.regel.substring(0, indexOpenParenthesisAfterMethod);
		this.regel = this.regel.substring(indexOpenParenthesisAfterMethod);

		StringBuilder params = new StringBuilder();
		System.out.println(params.toString());
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

		// old school

		/*
		 * if (regel.indexOf(" return ") > -1) { regel =
		 * regel.replaceFirst("return", EMPTY_STRING).trim(); } if
		 * (regel.indexOf("=") > -1) { scanner = new
		 * Scanner(regel).useDelimiter("="); declaration =
		 * scanner.next().trim();
		 * 
		 * if (declaration.indexOf(" ") > -1) { Scanner subScanner = null;
		 * subScanner = new Scanner(declaration).useDelimiter(" ");
		 * 
		 * // skip String type = subScanner.next(); String variableName =
		 * subScanner.next();
		 * 
		 * } regel = scanner.next(); }
		 * 
		 * scanner = new Scanner(regel).useDelimiter(";"); construction =
		 * scanner.next().trim(); this.constructionFromLine = construction;
		 * 
		 * // new
		 * 
		 * int indexOfFirstDot = construction.indexOf('.'); // voor temp sol.
		 * int starter = construction.indexOf('(') < ch //x // hier verder. moet
		 * goede beginpunt van regel hebben. en das niet altijd 0 bijvoorbeeld
		 * bij if en System.out.println. // vanuit '.' 1 of twee haakjes terug.
		 * // moet maar gewoon met een kleine parser. Eerst andere issues ...
		 * collab = construction.substring(0, indexOfFirstDot);
		 * while(collab.indexOf(" ") > -1) { collab =
		 * collab.substring(collab.indexOf(" ")+1); }
		 * 
		 * construction =
		 * construction.substring(indexOfFirstDot+1,construction.length());
		 * 
		 * int indexOfOpenParenthesis = construction.indexOf('(');
		 * 
		 * this.method = construction.substring(0,indexOfOpenParenthesis);
		 * 
		 * construction = construction.substring(indexOfOpenParenthesis+1,
		 * construction.length());
		 * 
		 * int indexOfClosingParenthesis = construction.indexOf('.')-1;
		 * if(indexOfClosingParenthesis < 0 ) { // no separator so take last
		 * closing parenthesis indexOfClosingParenthesis =
		 * construction.lastIndexOf(")"); }
		 * 
		 * // this.constructionFromLine = construction.substring(0,
		 * indexOfClosingParenthesis);
		 * 
		 * String paramsVanCollab = construction.substring(0,
		 * indexOfClosingParenthesis); String[] paramsVanCollabArray =
		 * paramsVanCollab.split(",\\s"); this.params =
		 * Arrays.asList(paramsVanCollabArray);
		 */
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
