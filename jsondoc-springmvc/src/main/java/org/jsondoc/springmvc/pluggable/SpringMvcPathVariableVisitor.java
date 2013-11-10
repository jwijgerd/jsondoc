package org.jsondoc.springmvc.pluggable;

import static org.jsondoc.core.pojo.ApiParamType.PATH;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiParamDoc;
import org.jsondoc.core.pojo.JSONDoc;
import org.jsondoc.core.visitor.AbstractDocVisitor;

/**
 * This doc visitor extracts the path variables from the method paths, creating associated
 * {@link org.jsondoc.core.pojo.ApiParamDoc} instances.
 */
public class SpringMvcPathVariableVisitor extends AbstractDocVisitor<Void> {

    private static final char VARIABLE_START = '{';
    private static final char VARIABLE_END = '}';

    @Override
    public Void visit(JSONDoc doc) {
        for (ApiDoc api : doc.getApis()) {
            api.accept(this);
        }
        return null;
    }

    @Override
    public Void visit(ApiDoc api) {
        for (ApiMethodDoc methodDoc : api.getMethods()) {
            methodDoc.accept(this);
        }
        return null;
    }

    @Override
    public Void visit(ApiMethodDoc method) {

        List<String> pathVariables = extractPathVariables(method.getPath());

        for (String pathVariable : pathVariables) {
            ApiParamDoc pathParameter = method.getPathparameter(pathVariable);
            if (pathParameter == null) {
                pathParameter = new ApiParamDoc();
                pathParameter.setName(pathVariable);
                pathParameter.setType("string");
                pathParameter.setRequired(String.valueOf(true));
                pathParameter.setParamType(PATH);
                method.getPathparameters().add(pathParameter);
            }
        }
        return null;
    }

    private List<String> extractPathVariables(CharSequence path) {

        int current = 0;
        int nested = 0;

        Collection<String> variableDeclarations = new ArrayList<String>();
        StringBuilder currentVariable = null;
        while (current < path.length()) {
            char c = path.charAt(current);

            if (c == VARIABLE_START) {
                if (currentVariable == null) {
                    currentVariable = new StringBuilder();
                } else {
                    currentVariable.append(VARIABLE_START);
                    nested += 1;
                }
            } else if (c == VARIABLE_END) {
                if (currentVariable != null) {
                    if (nested == 0) {
                        variableDeclarations.add(currentVariable.toString());
                        currentVariable = null;
                    } else {
                        currentVariable.append(VARIABLE_END);
                        nested -= 1;
                    }
                }
            } else {
                if (currentVariable != null) {
                    currentVariable.append(c);
                }
            }
            current += 1;
        }

        // trim out everything following ':' as these are directives.
        List<String> variables = new ArrayList<String>();
        for (String variableDeclaration : variableDeclarations) {
            if (variableDeclaration.contains(":")) {
                variables.add(variableDeclaration.substring(0, variableDeclaration.indexOf(':')));
            } else {
                variables.add(variableDeclaration);
            }
        }

        return variables;
    }
}