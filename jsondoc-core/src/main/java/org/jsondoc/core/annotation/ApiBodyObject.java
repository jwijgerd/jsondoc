package org.jsondoc.core.annotation;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This annotation is to be used on your method and represents the parameter passed in the body of the
 * requests.
 *
 * @see ApiObject
 * @author Fabio Maffioletti
 */
@Documented
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface ApiBodyObject {
}
