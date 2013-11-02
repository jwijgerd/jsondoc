package org.jsondoc.springmvc;

import static org.hamcrest.core.Is.is;
import static org.jsondoc.core.pojo.ApiVerb.DELETE;
import static org.jsondoc.core.pojo.ApiVerb.GET;
import static org.jsondoc.core.pojo.ApiVerb.POST;
import static org.jsondoc.core.pojo.ApiVerb.PUT;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiObjectDoc;
import org.jsondoc.core.pojo.ApiObjectFieldDoc;
import org.jsondoc.core.pojo.ApiVerb;
import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;

/**
 *
 */
public class SpringMvcJSONDocUtilsTest {

    @Test
    public void testSampleResource() {
        ApiDoc apiDoc = SpringMvcJSONDocUtils.createApiDoc(SampleController.class);

        assertThat(apiDoc.getName(), is("SampleController"));
        assertThat(apiDoc.getDescription(), is("SampleController"));

        List<ApiMethodDoc> methods = apiDoc.getMethods();
        assertThat(methods.size(), is(5));

        // should be ordered alphabetically by path.
        Iterable<String> paths = Iterables.transform(methods, new ApiMethodPathFunction());
        assertAlphabeticalSorting(paths);

        // should be ordered by method when the path is the same.
        assertThat(methods.get(0).getVerb(), is(GET));
        assertThat(methods.get(1).getVerb(), is(POST));
        assertThat(methods.get(2).getVerb(), is(PUT));
        assertThat(methods.get(3).getVerb(), is(DELETE));
    }

    @Test
    public void testParametrizedField() {
        Collection<Class<?>> objects = new ArrayList<Class<?>>();
        objects.add(ParametrizedField.class);
        Set<ApiObjectDoc> docs = SpringMvcJSONDocUtils.createApiObjectDocs(objects);
        assertThat(docs.size(), is(1));

        ApiObjectDoc object = Iterables.getFirst(docs, null);
        assertThat(object.getName(), is("parametrized"));
        assertThat(object.getDescription(), is(""));
        assertThat(object.getFields().size(), is(1));

        ApiObjectFieldDoc field = Iterables.getFirst(object.getFields(), null);
        assertThat(field.getName(), is("parametrized"));
        assertThat(field.getDescription(), is("parametrized field"));
        assertThat(field.getType(), is("undefined"));
        assertThat(field.getMultiple(), is("true"));
    }

    @Test
    public void testApiAnnotationRespected() {
        ApiDoc apiDoc = SpringMvcJSONDocUtils.createApiDoc(AnnotatedController.class);
        assertThat(apiDoc.getName(), is("annotated name"));
        assertThat(apiDoc.getDescription(), is("annotated description"));
    }

    private void assertAlphabeticalSorting(Iterable<String> items) {
        Collator collator = Collator.getInstance();

        String previous = null;
        for (String item : items) {
            if (previous != null) {
                int compare = collator.compare(previous, item);
                if (compare > 0) {
                    fail("previous: " + previous + " current: " + item);
                }
            }
            previous = item;
        }
    }

    private static class ApiMethodPathFunction implements Function<ApiMethodDoc,String> {
        @Override
        public String apply(ApiMethodDoc apiMethodDoc) {
            return apiMethodDoc.getPath();
        }
    }
}
