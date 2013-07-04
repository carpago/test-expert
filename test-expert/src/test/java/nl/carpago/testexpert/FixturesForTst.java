package nl.carpago.testexpert;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
	public class FixturesForTst {

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
		public String one() {
			return "1";
		}
		
		@Bean
		public String two() {
			return "2";
		}
		
		@Bean
		public String three() {
			return "3";
		}
		
		@Bean
		public String onetwothree() {
			return "123";
		}
		
		@Bean
		public int id() {
			return 1;
		}
		
		@Bean
		public String string() {
			return "3";
		}
		
		@Bean
		public int vier() {
			return 4;
		}
		
		@Bean
		public int four()
		{
			return 4;
		}
		
		@Bean
		public Persoon eenAnderPersoon() {
			return new Persoon(44, "John Doe");
		}
		
		@Bean
		public List<String> vliegveldServiceId()
		{
			List<String> result = new ArrayList<String>();
			result.add("RaymondTest");
			
			return result;
		}
	}