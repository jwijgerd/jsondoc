package org.jsondoc.core.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This annotation is to be used on your method and represents the returned value
 * @see ApiObject
 * @author Fabio Maffioletti
 *
 */
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface ApiResponseObject {

}
