package nl.carpago.testexpert.testers;

import java.util.HashMap;
import java.util.List;

import nl.carpago.unittestgenerator.annotation.CreateUnittest;

public class UnittestGeneratorMethodesMetCollabs {
	
	private int counter;
	private UnittestGeneratorMethodes methodes = new UnittestGeneratorMethodesImpl();
	private List list;
	private HashMap hashMap;
	
	@CreateUnittest
	public void geenParameterGeenReturn() {
		this.methodes.geenParameterGeenReturn();
	}
	
	@CreateUnittest(in="3")
	public void welParameterGeenReturn(int in) {
		this.methodes.welParameterGeenReturn(in);
		this.hashMap.put("teststring2", new Object()).toString();
	
	}
	
	@CreateUnittest(in="voornaam")
	public void welParameterGeenReturn(String in) {
		this.list.add("teststring");
		this.hashMap.put("teststring2", new Object()).toString();
		this.methodes.welParameterGeenReturn(in);
		
	}
	
	@CreateUnittest(in="lijst")
	public void welParameterGeenReturn(List in) {
		this.methodes.welParameterGeenReturn(in);
		this.hashMap.put("teststring2", new Object()).toString();
		
	}
	
	@CreateUnittest(in="3", out="3")
	public int welParameterWelReturn(int in) {
		
		return this.methodes.welParameterWelReturn(in);
		
	}
	
	@CreateUnittest(in="voornaam",out="3")
	public int welParameterWelReturn(String in) {
		
		return this.methodes.welParameterWelReturn(in);
		
	}
	
	@CreateUnittest(in="lijst",out="3")
	public int welParameterWelReturn(List in) {
		return this.methodes.welParameterWelReturn(in);
	}
	
	@CreateUnittest(out="3")
	public int geenParameterWelReturnVoorInt() {
		return this.methodes.geenParameterWelReturnVoorInt();
	}
	
	@CreateUnittest(out="voornaam")
	public String geenParameterWelReturnVoorString() {
		
		return this.methodes.geenParameterWelReturnVoorString();
	}
	
	@CreateUnittest(out="lijst")
	public List geenParameterWelReturnVoorList() {
		return this.methodes.geenParameterWelReturnVoorList();
		
	}

}
