package org.jsondoc.core.visitor;

import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiParamDoc;
import org.jsondoc.core.pojo.JSONDoc;

/**
 * @author Daniel Ostermeier
 */
public class RemovePathParameterVisitor extends AbstractDocVisitor<Void> {

    private final String parameterName;

    public RemovePathParameterVisitor(String parameterName) {
        this.parameterName = parameterName;
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
        for (ApiMethodDoc methodDoc : api.getMethods()) {
            methodDoc.accept(this);
        }
        return null;
    }

    @Override
    public Void visit(ApiMethodDoc method) {
        ApiParamDoc paramDoc = method.getPathparameter(parameterName);
        if (paramDoc != null) {
            method.getPathparameters().remove(paramDoc);
        }
        return null;
    }
}