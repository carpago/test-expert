package nl.carpago.testexpert.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CreateUnittest {
	
	String[] in() default {};
	String out() default "";
	String post() default "";

}
