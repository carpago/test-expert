package nl.carpago.testexpert;

public class InnerClassTrainerJav210 {
	
	
	

}

class Car {
	
	private Engine engine;
	private String merk;
	
	class Engine {
		
		private int aantalBougies;
		
		public void foo() {
			merk = "bmw";
		}
	}
	
	public void carStartsEngine() {
		this.engine.aantalBougies = this.engine.aantalBougies +1;
	}
}


class AnonyMousInnerClass {
	
	
	public void bar() {
		
		//runner is hier dus een runnable implementor!!! en dus een class.
		Runnable runner = new Runnable() {
			public void run() {
				System.out.println("In the bar van inner anonymous runner.");
				
			}
		};
	}
	
	
}


