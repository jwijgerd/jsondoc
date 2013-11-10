package org.jsondoc.springmvc.pluggable;

import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.JSONDoc;
import org.jsondoc.core.visitor.AbstractDocVisitor;

/**
 * A doc visitor that re-writes the api paths, replacing {version:*} references with the
 * actual version of the documentation being requested.
 */
public class SpringMvcPathVariableRewriteVisitor extends AbstractDocVisitor<Void> {

    // Spring path variables are of the format {variableName:...}

    private static final char VARIABLE_START = '{';
    private static final char VARIABLE_END = '}';

    /**
     * The string to substitute into the variable.
     */
    private final String variableValue;

    /**
     * The name of the variable within the path.
     */
    private final String variableName;

    public SpringMvcPathVariableRewriteVisitor(String variableName, String variableValue) {
        this.variableName = variableName;
        this.variableValue = variableValue;
    }

    @Override
    public Void visit(JSONDoc doc) {
        for (ApiDoc api : doc.getApis()) {
            api.accept(this);
        }
        return null;
    }

    @Override
    public Void visit(ApiDoc api) {
        for (ApiMethodDoc method : api.getMethods()) {
            method.accept(this);
        }
        return null;
    }

    @Override
    public Void visit(ApiMethodDoc api) {

        String path = api.getPath();
        if (path.contains(VARIABLE_START + variableName)) {
            int start = path.indexOf(VARIABLE_START + variableName);
            int current = start + 8;
            int nested = 0;
            while (true) {
                char c = path.charAt(current);

                if (c == VARIABLE_END && nested == 0) {
                    break;
                }

                if (c == VARIABLE_START) {
                    nested += 1;
                }
                if (c == VARIABLE_END) {
                    nested -= 1;
                }
                current += 1;
            }

            String updatedPath = path.substring(0, start) + variableValue + path.substring(current + 1);
            api.setPath(updatedPath);
        }
        return null;
    }
}