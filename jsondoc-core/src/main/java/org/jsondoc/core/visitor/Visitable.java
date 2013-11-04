package org.jsondoc.core.visitor;

/**
 * @author Daniel Ostermeier
 */
public interface Visitable {

    <T> T accept(Visitor<T> visitor);
}
