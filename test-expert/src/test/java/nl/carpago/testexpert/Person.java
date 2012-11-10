package nl.carpago.testexpert;

class Bike {
	private String make;
	private int productionyear;
	
	public Bike(String make, int productionYear) {
		this.make = make;
		this.productionyear = productionYear;
	}

	protected String getMake() {
		return make;
	}

	protected int getProductionyear() {
		return productionyear;
	}
	
	
}

public class Person {
	private String name;
	private int age;
	
	private Bike bike = new Bike("Batavus", 2001);

	public Person(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}
	
	public Bike getBike() {
		return this.bike;
	}

}
