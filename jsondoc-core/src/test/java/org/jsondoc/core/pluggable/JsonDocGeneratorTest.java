package org.jsondoc.core.pluggable;

import org.jsondoc.core.Child;
import org.jsondoc.core.Parent;
import org.jsondoc.core.Test1Controller;
import org.jsondoc.core.Test3Controller;
import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiObjectDoc;
import org.jsondoc.core.visitor.VersionedController;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Daniel Ostermeier
 */
public class JsonDocGeneratorTest {

    private JsonDocGenerator generator;

    @Before
    public void setUp() {
        generator = new JsonDocGenerator();
    }

    @Test
    public void testSomething() {
        ApiDoc apiA = generator.createApiDoc(Test1Controller.class);
        apiA.getName();

        ApiDoc apiB = generator.createApiDoc(Test3Controller.class);
        apiB.getName();

        ApiDoc apiC = generator.createApiDoc(VersionedController.class);
        apiC.getName();

        ApiObjectDoc objectA = generator.createObjectDoc(Child.class);
        objectA.getName();

        ApiObjectDoc objectB = generator.createObjectDoc(Parent.class);
        objectB.getName();
    }
}
