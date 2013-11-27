package org.jsondoc.ebuddy.pluggable;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectProperty;
import org.jsondoc.core.pluggable.JsonDocGenerator;
import org.jsondoc.core.pojo.ApiObjectDoc;
import org.jsondoc.core.pojo.ApiObjectPropertyDoc;
import org.jsondoc.ebuddy.annotation.EbuddyApiObjectOptions;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Daniel Ostermeier
 */
public class EbuddyApiObjectOptionsHandlerTest {

    private JsonDocGenerator generator;

    @Before
    public void setUp() {
        generator = new JsonDocGenerator();
        generator.register(new EbuddyApiObjectOptionsHandler());
    }

    @Test
    public void testLocallyDefinedOption() {
        ApiObjectDoc objectDoc = generator.createObjectDoc(CreateGroupAdapter.class);

        assertThat(objectDoc.getCategory(), is("adapters"));
        assertThat(objectDoc.getName(), is("create group adapter"));

        List<ApiObjectPropertyDoc> fields = objectDoc.getFields();
        assertThat(fields.size(), is(1));

        ApiObjectPropertyDoc field = fields.get(0);
        assertThat(field.getName(), is("name"));
    }

    @ApiObject(name = "Group")
    private interface Group {
        @ApiObjectProperty(description = "The group type.")
        String getType();
        @ApiObjectProperty(description = "The name of this group.")
        String getName();
    }

    @ApiObject(category = "adapters")
    private static class GroupBaseAdapter implements Group {
        @Override
        public String getType() {
            return null;
        }
        @Override
        public String getName() {
            return null;
        }
    }

    @ApiObject(name = "create group adapter")
    @EbuddyApiObjectOptions(useDeclaredOnly = true)
    private static class CreateGroupAdapter extends GroupBaseAdapter {
        @Override
        public String getName() {
            return null;
        }
    }
}
