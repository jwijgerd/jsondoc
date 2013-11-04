package org.jsondoc.core.pojo;

import org.jsondoc.core.visitor.Visitable;
import org.jsondoc.core.visitor.Visitor;

/**
 * @author Daniel Ostermeier
 */
public class ApiVersionDoc implements Visitable {

    private final int since;
    private final int until;

    public ApiVersionDoc(int since, int until) {
        this.since = since;
        this.until = until;
    }

    public int getSince() {
        return since;
    }

    public int getUntil() {
        return until;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
