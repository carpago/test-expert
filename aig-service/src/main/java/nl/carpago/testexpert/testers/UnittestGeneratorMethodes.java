package nl.carpago.testexpert.testers;

import java.util.List;

import nl.carpago.unittestgenerator.annotation.Expect;

public interface UnittestGeneratorMethodes {
	
	
	public void geenParameterGeenReturn() ;
	
	@Expect(in="3")
	public void welParameterGeenReturn(int in) ;
	
	@Expect(in="voornaam")
	public void welParameterGeenReturn(String in);
	
	@Expect(in="lijst")
	public void welParameterGeenReturn(List in);
	
	@Expect(in="3",out="3")
	public int welParameterWelReturn(int in) ;
	
	@Expect(in="voornaam",out="3")
	public int welParameterWelReturn(String in) ;
	
	@Expect(in="lijst",out="3")
	public int welParameterWelReturn(List in) ;
	
	@Expect(out="3")
	public int geenParameterWelReturnVoorInt();
	
	@Expect(out="voornaam")
	public String geenParameterWelReturnVoorString() ;
	
	@Expect(out="lijst")
	public List geenParameterWelReturnVoorList() ;

}
