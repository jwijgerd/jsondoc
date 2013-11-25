package org.jsondoc.core.pluggable;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

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

    @Api
    private static class SampleAPI {

    }
}
