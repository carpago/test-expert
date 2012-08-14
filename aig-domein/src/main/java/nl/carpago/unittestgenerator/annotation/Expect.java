package nl.carpago.unittestgenerator.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Expect {
	
	String[] in() default {};
	String out() default "";
}
