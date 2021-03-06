package org.jsondoc.core.pluggable;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.internal.matchers.IsCollectionContaining.hasItems;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectProperty;
import org.jsondoc.core.pojo.ApiObjectDoc;
import org.jsondoc.core.pojo.ApiObjectPropertyDoc;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Daniel Ostermeier
 */
public class ApiObjectDocGenerationTest {

    private JsonDocGenerator generator;

    @Before
    public void setUp() {
        generator = new JsonDocGenerator();
    }

    @Test
    public void testInheritPropertiesFromNonApiObjects() {

        ApiObjectDoc doc = generator.createObjectDoc(InheritObjectA.class);
        assertThat(doc, is(notNullValue()));
        assertThat(doc.getName(), is(InheritObjectA.class.getSimpleName()));
        assertThat(doc.getFields().size(), is(2));
        assertThat(doc.getField("objectProperty"), is(notNullValue()));
        assertThat(doc.getField("baseProperty"), is(notNullValue()));
    }

    @Test
    public void testPropertyDefinitionAnnotatedField() {
        ApiObjectDoc doc = generator.createObjectDoc(PropertyDefinition.class);
        assertThat(doc, is(notNullValue()));

        ApiObjectPropertyDoc field = doc.getField("field");
        assertThat(field, is(notNullValue()));
        assertThat(field.getName(), is("field"));
        assertThat(field.getType(), is("string"));
    }

    @Test
    public void testPropertyDefinitionAnnotatedGetter() {
        ApiObjectDoc doc = generator.createObjectDoc(PropertyDefinition.class);
        assertThat(doc, is(notNullValue()));

        ApiObjectPropertyDoc field = doc.getField("getter");
        assertThat(field, is(notNullValue()));
        assertThat(field.getType(), is("string"));
    }

    @Test
    public void testPropertyDefinitionAnnotatedSetter() {
        ApiObjectDoc doc = generator.createObjectDoc(PropertyDefinition.class);
        assertThat(doc, is(notNullValue()));

        ApiObjectPropertyDoc field = doc.getField("setter");
        assertThat(field, is(notNullValue()));
        assertThat(field.getType(), is("string"));
    }

    @Test
    public void testAnnotationOverridesDefaultValues() {
        ApiObjectDoc doc = generator.createObjectDoc(AnnotationOverridesDefaults.class);
        assertThat(doc, is(notNullValue()));
        assertThat(doc.getName(), is("object"));

        ApiObjectPropertyDoc field = doc.getField("annotationOverridesFieldName");
        assertThat(field, is(notNullValue()));
        assertThat(field.getName(), is("field"));
        assertThat(field.getType(), is("notastring"));
    }

    @Test
    public void testAllowedValuesViaAnnotation() {
        ApiObjectDoc doc = generator.createObjectDoc(AllowedValuesObject.class);
        ApiObjectPropertyDoc field = doc.getField("valueByAnnotation");
        assertThat(field.getAllowedvalues(), hasItems("a", "b"));
    }

    @Test
    public void testAllowedValuesViaEnum() {
        ApiObjectDoc doc = generator.createObjectDoc(AllowedValuesObject.class);
        ApiObjectPropertyDoc field = doc.getField("valueByEnum");
        assertThat(field.getAllowedvalues(), hasItems("C", "D"));
    }

    @Test
    public void testFieldFormatAnnotation() {
        ApiObjectDoc doc = generator.createObjectDoc(ObjectWithFormattedFields.class);
        ApiObjectPropertyDoc field = doc.getField("format");
        assertThat(field.getFormat(), is("blah"));
    }

    @Test
    public void testObjectCategories() {
        ApiObjectDoc docA = generator.createObjectDoc(CategoryAObject.class);
        assertThat(docA.getCategory(), is("a"));
        ApiObjectDoc docB = generator.createObjectDoc(CategoryBObject.class);
        assertThat(docB.getCategory(), is("b"));
    }

    @Test
    public void testAnnotatedCategoryInheritAndOverride() {
        ApiObjectDoc override = generator.createObjectDoc(CategoryOverride.class);
        assertThat(override.getCategory(), is("b"));

        ApiObjectDoc inherit = generator.createObjectDoc(CategoryInherit.class);
        assertThat(inherit.getCategory(), is("a"));
    }


    @Test
    public void testAnnotatedApiObjectsInheritedInterfaces() {
        ApiObjectDoc apiObjectDoc = generator.createObjectDoc(InterfaceInheritOutputAdapter.class);
        assertNotNull(apiObjectDoc.getField("valueB"));
        assertNotNull(apiObjectDoc.getField("valueA"));
        assertNotNull(apiObjectDoc.getField("valueC"));
        assertNotNull(apiObjectDoc.getField("valueD"));
    }

    private static class InheritBaseA {
        @ApiObjectProperty
        private String baseProperty;
    }

    @ApiObject
    private static class InheritObjectA extends InheritBaseA {
        @ApiObjectProperty
        private String objectProperty;
    }

    @ApiObject(category = "a")
    private static class InheritCategory {

    }

    @ApiObject(category = "b")
    private static class CategoryOverride extends InheritCategory {

    }

    @ApiObject
    private static class CategoryInherit extends InheritCategory {

    }

    @ApiObject
    private static class PropertyDefinition {
        @ApiObjectProperty
        private String field;
        @ApiObjectProperty
        public String getGetter() {
            return null;
        }
        @ApiObjectProperty
        public void setSetter(String s) {
        }
    }

    @ApiObject(name = "object")
    private static class AnnotationOverridesDefaults {
        @ApiObjectProperty(name = "field", type="notastring")
        private String annotationOverridesFieldName;
    }

    @ApiObject
    private static class AllowedValuesObject {
        @ApiObjectProperty(allowedvalues = {"a", "b"})
        private String valueByAnnotation;
        @ApiObjectProperty
        private AllowedValue valueByEnum;
    }

    private enum AllowedValue {
        C, D
    }

    @ApiObject
    private static class ObjectWithFormattedFields {
        @ApiObjectProperty(format = "blah")
        private String format;
    }

    @ApiObject(category = "a")
    private static class CategoryAObject {
    }
    @ApiObject(category = "b")
    private static class CategoryBObject {
    }


    @ApiObject(name = "Interface Inherit Output Adapter", category = "Z")
    private  static class InterfaceInheritOutputAdapter extends InterfaceInheritBaseAdapter implements InterfaceStandAloneOneLevelUp {
        public String getValueB() {
            return "valueB";
        }
        @Override
        public String getValueA() {
            return "valueA";
        }
        @Override
        public String getValueC() {
            return "valueC";
        }
        @Override
        public String getValueD() {
            return "valueD";
        }
    }


    private  static class InterfaceInheritBaseAdapter implements InterfaceInheritChild, InterfaceStandAlone{
        @Override
        public String getValueB() {
            return null;
        }
        @Override
        public String getValueA() {
            return null;
        }
        @Override
        public String getValueC() {
            return null;
        }
    }

    @ApiObject(name = "InterfaceInheritChild")
    private  static interface InterfaceInheritChild extends InterfaceInheritParent {
        @ApiObjectProperty(description = "value b")
        String getValueB();
    }

    @ApiObject(name = "InterfaceStandAloneOneLevelUp")
    private  static interface InterfaceStandAloneOneLevelUp {
        @ApiObjectProperty(description = "value d")
        String getValueD();
    }

    @ApiObject(name = "InterfaceStandAlone")
    private  static interface InterfaceStandAlone {
        @ApiObjectProperty(description = "value c")
        String getValueC();
    }
    @ApiObject(name = "InterfaceInheritParent")
    private  static interface InterfaceInheritParent{
        @ApiObjectProperty(description = "value a")
        String getValueA();

    }



}
