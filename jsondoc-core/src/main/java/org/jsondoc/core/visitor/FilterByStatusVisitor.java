package org.jsondoc.core.visitor;

import java.util.ArrayList;
import java.util.List;

import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiStatus;
import org.jsondoc.core.pojo.JSONDoc;

/**
 * A filtering visitor that filters out any APIs and API Methods whose
 * defined status is not a part of the given list.
 *
 * @author Daniel Ostermeier
 */
public class FilterByStatusVisitor extends AbstractDocVisitor<Void> {

    private final List<ApiStatus> accepted;

    public FilterByStatusVisitor(List<ApiStatus> accepted) {
        this.accepted = accepted;
    }

    @Override
    public Void visit(JSONDoc doc) {

        List<ApiDoc> apis = doc.getApis();
        for (ApiDoc api : new ArrayList<ApiDoc>(apis)) {
            if (accepted.contains(api.getStatus())) {
                api.accept(this);
            } else {
                apis.remove(api);
            }
        }
        return null;
    }

    @Override
    public Void visit(ApiDoc api) {
        List<ApiMethodDoc> methods = api.getMethods();
        for (ApiMethodDoc method : new ArrayList<ApiMethodDoc>(methods)) {
            if (accepted.contains(method.getStatus())) {
                method.accept(this);
            } else {
                methods.remove(method);
            }
        }
        return null;
    }
}