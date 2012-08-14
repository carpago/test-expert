package nl.carpago.unittestgenerator.testers;

import java.util.ArrayList;
import java.util.List;

import nl.carpago.unittestgenerator.annotation.CreateUnittest;

public class UnittestGeneratorMethodesImpl implements UnittestGeneratorMethodes {
	
	private int counter;
	
	@CreateUnittest
	public void geenParameterGeenReturn() {
		this.counter ++;
	}
	
	@CreateUnittest(in="3")
	public void welParameterGeenReturn(int in) {
	
	}
	
	@CreateUnittest(in="voornaam")
	public void welParameterGeenReturn(String in) {
		
	}
	
	@CreateUnittest(in="lijst")
	public void welParameterGeenReturn(List in) {
		
	}
	
	@CreateUnittest(in="3", out="3")
	public int welParameterWelReturn(int in) {
		
		return 3;
		
	}
	
	@CreateUnittest(in="voornaam",out="3")
	public int welParameterWelReturn(String in) {
		
		return 3;
		
	}
	
	@CreateUnittest(in="lijst",out="3")
	public int welParameterWelReturn(List in) {
		return 3;
	}
	
	@CreateUnittest(out="3")
	public int geenParameterWelReturnVoorInt() {
		return 3;
	}
	
	@CreateUnittest(out="voornaam")
	public String geenParameterWelReturnVoorString() {
		
		return "Raymond";
		
	}
	
	@CreateUnittest(out="lijst")
	public List geenParameterWelReturnVoorList() {
		return new ArrayList();
		
	}

}
