package org.jsondoc.core.pojo;

/**
 * @author Daniel Ostermeier
 */
public class ApiVersionDoc {

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
}
