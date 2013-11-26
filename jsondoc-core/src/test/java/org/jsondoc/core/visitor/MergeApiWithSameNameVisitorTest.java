package org.jsondoc.core.visitor;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.pluggable.JsonDocGenerator;
import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.JSONDoc;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Daniel Ostermeier
 */
public class MergeApiWithSameNameVisitorTest {

    private JsonDocGenerator generator;

    @Before
    public void setUp() {
        generator = new JsonDocGenerator();
    }

    @Test
    public void testMerge() {
        JSONDoc jsonDoc = generator.createJsonDoc("", "", ApiA.class, ApiB.class);
        assertThat(jsonDoc.getApis().size(), is(2));

        jsonDoc.accept(new MergeApiWithSameNameVisitor());

        assertThat(jsonDoc.getApis().size(), is(1));

        ApiDoc apiDoc = jsonDoc.getApis().get(0);
        assertThat(apiDoc.getMethods().size(), is(2));
    }

    @Api(name = "api")
    private static class ApiA {
        @ApiMethod(path = "/a")
        public void method() {

        }
    }

    @Api(name = "api")
    private static class ApiB {
        @ApiMethod(path = "/b")
        public void method() {

        }
    }
}
