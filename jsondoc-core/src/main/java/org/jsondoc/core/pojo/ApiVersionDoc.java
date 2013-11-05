package org.jsondoc.core.pojo;

import org.jsondoc.core.visitor.Visitable;
import org.jsondoc.core.visitor.Visitor;

/**
 * @author Daniel Ostermeier
 */
public class ApiVersionDoc implements Visitable {

    private int since;
    private int until;

    public ApiVersionDoc() {

    }

    public ApiVersionDoc(int since, int until) {
        this.since = since;
        this.until = until;
    }

    public int getSince() {
        return since;
    }

    public void setSince(int since) {
        this.since = since;
    }

    public int getUntil() {
        return until;
    }

    public void setUntil(int until) {
        this.until = until;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
