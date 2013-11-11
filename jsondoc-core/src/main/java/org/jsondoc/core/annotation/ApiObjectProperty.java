package org.jsondoc.core.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This annotation is to be used on your objects' fields and represents a field of an object
 * @author Fabio Maffioletti
 *
 */
@Documented
@Target({FIELD, METHOD})
@Retention(RUNTIME)
public @interface ApiObjectProperty {

    String name() default "";

	/**
	 * A description of what the field is
	 * @return
	 */
	String description() default "";

    /**
     * The fields type. This can be used to override the inspected type.
     * @return
     */
    String type() default "";

	/**
	 * The format pattern for this field
	 * @return
	 */
	String format() default "";
	
	/**
	 * The allowed values for this field
	 * @return
	 */
	String[] allowedvalues() default {};

}
