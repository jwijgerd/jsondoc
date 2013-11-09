package org.jsondoc.core.visitor;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.jsondoc.core.pluggable.JsonDocGenerator;
import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.JSONDoc;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Daniel Ostermeier
 */
public class FilterByVersionVisitorTest {

    private JsonDocGenerator generator;

    @Before
    public void setUp() {
        generator = new JsonDocGenerator();
    }

    @Test
    public void testVersionedApiWithVersionInRange() {
        assertApiVersionFiltering(5, 1, 1);
    }

    @Test
    public void testVersionedApiWithVersionBelowRange() {
        assertApiVersionFiltering(1, 0, 0);
    }

    @Test
    public void testVersionedApiWithVersionAboveRange() {
        assertApiVersionFiltering(11, 0, 0);
    }

    @Test
    public void testVersionedApiWithVersionAtLowBoundary() {
        assertApiVersionFiltering(3, 1, 0);
    }

    @Test
    public void testVersionedApiWithVersionAtHighBoundary() {
        assertApiVersionFiltering(10, 1, 0);
    }

    private void assertApiVersionFiltering(int version, int expectedApiCount, int expectedMethodCount) {

        JSONDoc doc = generator.createJsonDoc("", "", VersionedController.class);

        doc.accept(new FilterByVersionVisitor(version));
        assertThat(doc.getApis().size(), is(expectedApiCount));

        if (!doc.getApis().isEmpty()) {
            ApiDoc api = doc.getApis().get(0);
            assertThat(api.getMethods().size(), is(expectedMethodCount));
        }
    }
}
