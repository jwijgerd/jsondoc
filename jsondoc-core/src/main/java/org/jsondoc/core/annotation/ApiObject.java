package org.jsondoc.core.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This annotation is to be used on your object classes and represents an object used for communication between clients and server
 * @author Fabio Maffioletti
 *
 */
@Documented
@Target(TYPE)
@Retention(RUNTIME)
public @interface ApiObject {

	/**
	 * The name of the object, to be referenced by other annotations with an "object" attribute
	 * @see ApiBodyObject
	 * @see ApiResponseObject
	 * @return
	 */
	String name() default "";
	
	/**
	 * A description of what the object contains or represents
	 * @return
	 */
	String description() default "";
	
	/**
	 * Whether to build the json documentation for this object or not. Default value is true
	 * @return
	 */
	boolean show() default true;

    /**
     * A category provides an arbitrary grouping identifier, allowing the UI to group objects
     * from the same category together in the UI.
     *
     * @return
     */
    String category() default "";
}
