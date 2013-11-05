package org.jsondoc.core.visitor;

import static com.google.common.collect.Lists.newArrayList;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiObjectDoc;
import org.jsondoc.core.pojo.ApiObjectFieldDoc;
import org.jsondoc.core.pojo.JSONDoc;

/**
 * @author Daniel Ostermeier
 */
public class SortAlphabeticallyVisitor extends AbstractDocVisitor<Void> {

    @Override
    public Void visit(JSONDoc doc) {

        // Sort the APIs.
        List<ApiDoc> apis = newArrayList(doc.getApis());
        Collections.sort(apis, new ApiNameComparator());
        doc.setApis(new TreeSet<ApiDoc>(apis));

        // Sort the objects.
        List<ApiObjectDoc> objects = newArrayList(doc.getObjects());
        Collections.sort(objects, new ApiObjectComparator());
        doc.setObjects(new TreeSet<ApiObjectDoc>(objects));

        for (ApiDoc api : apis) {
            api.accept(this);
        }
        for (ApiObjectDoc object : objects) {
            object.accept(this);
        }
        return null;
    }

    @Override
    public Void visit(ApiDoc api) {
        List<ApiMethodDoc> methods = api.getMethods();
        Collections.sort(methods, new ApiMethodComparator());
        return null;
    }

    @Override
    public Void visit(ApiObjectDoc object) {
        List<ApiObjectFieldDoc> fields = object.getFields();
        Collections.sort(fields, new ApiObjectFieldComparator());
        return null;
    }

    private static final class ApiNameComparator implements Comparator<ApiDoc> {
        private final Collator collator = Collator.getInstance();

        @Override
        public int compare(ApiDoc o1, ApiDoc o2) {
            return collator.compare(o1.getName(), o2.getName());
        }
    }

    private static final class ApiMethodComparator implements Comparator<ApiMethodDoc> {
        private final Collator collator = Collator.getInstance();

        @Override
        public int compare(ApiMethodDoc methodA, ApiMethodDoc methodB) {
            int comparison = collator.compare(methodA.getPath(), methodB.getPath());
            if (comparison != 0) {
                return comparison;
            }

            return methodA.getVerb().ordinal() - methodB.getVerb().ordinal();
        }
    }

    private static final class ApiObjectFieldComparator implements Comparator<ApiObjectFieldDoc> {
        private final Collator collator = Collator.getInstance();

        @Override
        public int compare(ApiObjectFieldDoc o1, ApiObjectFieldDoc o2) {
            return collator.compare(o1.getName(), o2.getName());
        }
    }

    private static final class ApiObjectComparator implements Comparator<ApiObjectDoc> {
        private final Collator collator = Collator.getInstance();

        @Override
        public int compare(ApiObjectDoc o1, ApiObjectDoc o2) {
            return collator.compare(o1.getName(), o2.getName());
        }
    }

}
