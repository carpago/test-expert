package nl.carpago.testexpert;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
	public class FixturesForTest {

		public FixturesForTest() {

		}

		@Bean
		public Person person() {
			Person person = new Person("John Doe", 45);

			return person;
		}
		
		@Bean
		public Person anotherPerson() {
			Person person = new Person("Jane Doe", 45);
			
			return person;
		}
		
		@Bean
		public int number() {
			return 3;
		}
		
		@Bean
		public String string() {
			return "3";
		}
		
		@Bean
		public Persoon eenAnderPersoon() {
			return new Persoon(44, "John Doe");
		}
	}