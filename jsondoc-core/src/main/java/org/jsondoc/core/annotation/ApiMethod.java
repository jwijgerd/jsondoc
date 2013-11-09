package org.jsondoc.core.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.jsondoc.core.pojo.ApiStatus;
import org.jsondoc.core.pojo.ApiVerb;

/**
 * This annotation is to be used on your exposed methods.
 * @author Fabio Maffioletti
 *
 */
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface ApiMethod {

	/**
	 * The relative path for this method (ex. /country/get/{name})
	 * @return
	 */
	String path() default "";

	/**
	 * A description of what the method does
	 * @return
	 */
	String description() default "";
	
	/**
	 * The request verb (or method), to be filled with an ApiVerb value (GET, POST, PUT, DELETE)
	 * @see ApiVerb
	 * @return
	 */
	ApiVerb verb() default ApiVerb.UNDEFINED;
	
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

    ApiStatus status() default ApiStatus.UNDEFINED;

}
