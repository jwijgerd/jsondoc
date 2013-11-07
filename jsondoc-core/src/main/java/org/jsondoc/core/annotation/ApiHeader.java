package org.jsondoc.core.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This annotation is to be used inside an annotation of type ApiHeaders
 * @see ApiHeaders
 * @author Fabio Maffioletti
 *
 */
@Documented
@Target(ANNOTATION_TYPE)
@Retention(RUNTIME)
public @interface ApiHeader {

	/**
	 * The name of the header parameter
	 * @return
	 */
	String name();

	/**
	 * A description of what the parameter is needed for
	 * @return
	 */
	String description() default "";
	
}
