package org.jsondoc.core.visitor;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiObjectDoc;
import org.jsondoc.core.pojo.ApiObjectPropertyDoc;
import org.jsondoc.core.pojo.JSONDoc;

/**
 * An implementation of the {@link Visitor} interface that alphabetically sorts the
 * contents of the documentation.
 *
 * @author Daniel Ostermeier
 */
public class SortAlphabeticallyVisitor extends AbstractDocVisitor<Void> {

    public static final Collator COLLATOR = Collator.getInstance();

    private static final Comparator<ApiDoc> API_COMPARATOR = new ApiNameComparator();
    private static final Comparator<ApiMethodDoc> METHOD_COMPARATOR = new ApiMethodComparator();
    public static final Comparator<ApiObjectDoc> OBJECT_COMPARATOR = new ApiObjectComparator();
    private static final Comparator<ApiObjectPropertyDoc> PROPERTY_COMPARATOR = new ApiObjectPropertyComparator();

    @Override
    public Void visit(JSONDoc doc) {

        // Sort the APIs.
        Collections.sort(doc.getApis(), API_COMPARATOR);

        for (ApiDoc api : doc.getApis()) {
            api.accept(this);
        }

        for(Map.Entry<String,List<ApiObjectDoc>> entry: doc.getObjects().entrySet()) {
            Collections.sort(entry.getValue(), OBJECT_COMPARATOR);
            for (ApiObjectDoc object : entry.getValue()) {
                object.accept(this);
            }
        }

        return null;
    }

    @Override
    public Void visit(ApiDoc api) {
        Collections.sort(api.getMethods(), METHOD_COMPARATOR);
        return null;
    }

    @Override
    public Void visit(ApiObjectDoc object) {
        Collections.sort(object.getFields(), PROPERTY_COMPARATOR);
        return null;
    }

    private static final class ApiNameComparator implements Comparator<ApiDoc> {
        @Override
        public int compare(ApiDoc o1, ApiDoc o2) {
            return COLLATOR.compare(o1.getName(), o2.getName());
        }
    }

    private static final class ApiMethodComparator implements Comparator<ApiMethodDoc> {
        @Override
        public int compare(ApiMethodDoc methodA, ApiMethodDoc methodB) {
            int comparison = COLLATOR.compare(methodA.getPath(), methodB.getPath());
            if (comparison != 0) {
                return comparison;
            }

            return methodA.getVerb().ordinal() - methodB.getVerb().ordinal();
        }
    }

    private static final class ApiObjectPropertyComparator implements Comparator<ApiObjectPropertyDoc> {
        @Override
        public int compare(ApiObjectPropertyDoc o1, ApiObjectPropertyDoc o2) {
            return COLLATOR.compare(o1.getName(), o2.getName());
        }
    }

    private static final class ApiObjectComparator implements Comparator<ApiObjectDoc> {
        @Override
        public int compare(ApiObjectDoc o1, ApiObjectDoc o2) {
            return COLLATOR.compare(o1.getName(), o2.getName());
        }
    }

}
