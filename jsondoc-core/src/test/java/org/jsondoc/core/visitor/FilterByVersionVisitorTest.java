package org.jsondoc.core.visitor;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.HashSet;

import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.JSONDoc;
import org.jsondoc.core.util.JSONDocUtils;
import org.junit.Test;

/**
 * @author Daniel Ostermeier
 */
public class FilterByVersionVisitorTest {

    @Test
    public void testVersionedApiWithVersionInRange() {
        assertApiVersionFiltering(5, 1);
    }

    @Test
    public void testVersionedApiWithVersionBelowRange() {
        assertApiVersionFiltering(1, 0);
    }

    @Test
    public void testVersionedApiWithVersionAboveRange() {
        assertApiVersionFiltering(11, 0);
    }

    @Test
    public void testVersionedApiWithVersionAtLowBoundary() {
        assertApiVersionFiltering(3, 1);
    }

    @Test
    public void testVersionedApiWithVersionAtHighBoundary() {
        assertApiVersionFiltering(10, 1);
    }

    private void assertApiVersionFiltering(int version, int expectedApiSize) {
        ApiDoc api = JSONDocUtils.createApiDoc(VersionedController.class);
        JSONDoc doc = createJSONDoc(api);

        filterByVersion(doc, version);
        assertThat(doc.getApis().size(), is(expectedApiSize));
    }

    private void filterByVersion(JSONDoc doc, int version) {
        doc.accept(new FilterByVersionVisitor(version));
    }

    private JSONDoc createJSONDoc(ApiDoc... apis) {
        JSONDoc doc = new JSONDoc("", "");
        doc.setApis(new HashSet<ApiDoc>(Arrays.asList(apis)));
        return doc;
    }
}
