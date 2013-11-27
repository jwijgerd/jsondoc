package org.jsondoc.ebuddy.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * The ebuddy api object options annotation provides a set of configuration
 * options that allow us to customize the behaviour of the json doc generation
 * in non standard ways.
 *
 * @author Daniel Ostermeier
 */
@Documented
@Target(TYPE)
@Retention(RUNTIME)
public @interface EbuddyApiObjectOptions {

    /**
     * When true, the locallyDefined property is used to filter out all
     * properties from an api object that are not explicitly defined on
     * the object where this annotation is defined. Defaults to false.
     *
     * @return true to filter the properties, false otherwise.
     */
    boolean locallyDefined() default false;
}
