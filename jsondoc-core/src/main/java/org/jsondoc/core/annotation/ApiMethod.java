package org.jsondoc.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jsondoc.core.pojo.ApiVerb;

/**
 * This annotation is to be used on your exposed methods.
 * @author Fabio Maffioletti
 *
 */
@Documented
@Target(value=ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiMethod {

	/**
	 * The relative path for this method (ex. /country/get/{name})
	 * @return
	 */
	String path();

	/**
	 * A description of what the method does
	 * @return
	 */
	String description();
	
	/**
	 * The request verb (or method), to be filled with an ApiVerb value (GET, POST, PUT, DELETE)
	 * @see ApiVerb
	 * @return
	 */
	ApiVerb verb();
	
	/**
	 * An array of strings representing media types produced by the method, like application/json, application/xml, ...
	 * @return
	 */
	String[] produces() default {};
	
	/**
	 * An array of strings representing media types consumed by the method, like application/json, application/xml, ...
	 * @return
	 */
	String[] consumes() default {};
	
}
