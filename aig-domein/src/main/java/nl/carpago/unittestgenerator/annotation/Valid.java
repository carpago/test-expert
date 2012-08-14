package nl.carpago.unittestgenerator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

public @interface Valid {
	
	String in() default "";
	String out() default "";

}
