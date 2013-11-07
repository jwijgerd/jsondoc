package org.jsondoc.core.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This annotation is to be used inside an annotation of type ApiErrors
 * @see ApiErrors
 * @author Fabio Maffioletti
 *
 */
@Documented
@Target(ANNOTATION_TYPE)
@Retention(RUNTIME)
public @interface ApiError {

	/**
	 * The error code returned
	 * @return
	 */
	String code();

	/**
	 * A description of what the error code means
	 * @return
	 */
	String description() default "";
}
