package nl.carpago.unittestgenerator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
	public class Fixtures {

		public Fixtures() {

		}

		@Bean
		public Person person() {
			Person person = new Person("John Doe", 45);

			return person;
		}
	}