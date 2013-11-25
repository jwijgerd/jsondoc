package org.jsondoc.core.pluggable;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
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
    public void testMergeAPIsWithSameName() {
        List<ApiDoc> docs = generator.createApiDocs(MergeApiA.class, MergeApiB.class);
        assertThat(docs.size(), is(1));

        ApiDoc doc = docs.get(0);
        assertThat(doc.getMethods().size(), is(2));
    }

    @Api(name = "merge")
    private static class MergeApiA {
        @ApiMethod(path = "/a")
        public void methodA() {
        }
    }

    @Api(name = "merge")
    private static class MergeApiB {
        @ApiMethod(path = "/b")
        public void methodB() {
        }
    }
}
