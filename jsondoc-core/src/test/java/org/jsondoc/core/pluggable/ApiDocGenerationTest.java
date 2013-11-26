package org.jsondoc.core.pluggable;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.pojo.ApiDoc;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Daniel Ostermeier
 */
public class ApiDocGenerationTest {

    private JsonDocGenerator generator;

    @Before
    public void setUp() {
        generator = new JsonDocGenerator();
    }

    @Test
    public void testSampleAPI() {
        ApiDoc apiDoc = generator.createApiDoc(SampleAPI.class);
        assertThat(apiDoc, is(notNullValue()));
    }

    @Test
    public void testDuplicateApiNamesAllowed() {
        List<ApiDoc> apis = generator.createApiDocs(DuplicateApiNameA.class, DuplicateApiNameB.class);
        assertThat(apis.size(), is(2));
    }

    @Api
    private static class SampleAPI {

    }

    @Api(name = "a")
    private static class DuplicateApiNameA {

    }

    @Api(name = "a")
    private static class DuplicateApiNameB {

    }

    // test the overriding of the name of the api.

    // base defines a name or not
    // child defines name, or not.
    // each time, the lowest 'defined' name should be used.

    // test default name taken from the class simple name.
}
