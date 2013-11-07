package org.jsondoc.core.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This annotation is to be used on your "service" class, for example controller classes in Spring MVC.
 *
 * @author Fabio Maffioletti
 */
@Documented
@Target(TYPE)
@Retention(RUNTIME)
public @interface Api {

    /**
     * The name of the API.
     * @return
     */
    String name() default "";

	/**
	 * A description of what the API does.
	 * @return
	 */
	String description() default "";
}
