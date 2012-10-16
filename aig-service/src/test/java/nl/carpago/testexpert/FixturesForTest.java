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
	}