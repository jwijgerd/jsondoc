package org.jsondoc.core;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.pluggable.JsonDocGenerator;
import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiObjectDoc;
import org.jsondoc.core.pojo.ApiObjectPropertyDoc;
import org.jsondoc.core.pojo.JSONDoc;
import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

public class JSONDocUtilsTest {

	private String version = "1.0";
	private String basePath = "http://localhost:8080/api";
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Test
	public void testGetApi() throws Exception {
        Reflections reflections = new Reflections(
                new ConfigurationBuilder().setUrls(ClasspathHelper.forPackage("org.jsondoc.core"))
        );
		Set<Class<?>> relevantTypes = reflections.getTypesAnnotatedWith(Api.class);
        relevantTypes.addAll(reflections.getTypesAnnotatedWith(ApiObject.class));

        JsonDocGenerator generator = new JsonDocGenerator();
		JSONDoc apiDoc = generator.createJsonDoc(version, basePath, relevantTypes);

    	System.out.println(objectMapper.writeValueAsString(apiDoc));
	}

    @Test
    public void testApiVersionAnnotations() {
        JsonDocGenerator generator = new JsonDocGenerator();

        ApiObjectDoc object = generator.createObjectDoc(VersionedObject.class);
        assertThat(object.getName(), is("versioned object"));
        assertThat(object.getVersion().getSince(), is(2));

        ApiObjectPropertyDoc field = object.getFields().get(0);
        assertThat(field.getName(), is("field"));
        assertThat(field.getVersion().getSince(), is(5));

        ApiDoc api = generator.createApiDoc(VersionedApi.class);
        assertThat(api.getName(), is("versioned api"));
        assertThat(api.getDescription(), is("versioned"));
        assertThat(api.getVersion().getSince(), is(1));

        ApiMethodDoc method = api.getMethods().get(0);
        assertThat(method.getVersion().getSince(), is(4));
        assertThat(method.getVersion().getUntil(), is(10));
    }

}