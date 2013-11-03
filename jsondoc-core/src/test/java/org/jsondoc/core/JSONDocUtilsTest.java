package org.jsondoc.core;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiObjectDoc;
import org.jsondoc.core.pojo.ApiObjectFieldDoc;
import org.jsondoc.core.pojo.ApiParamDoc;
import org.jsondoc.core.pojo.JSONDoc;
import org.jsondoc.core.util.JSONDocUtils;
import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

public class JSONDocUtilsTest {
	private static Reflections reflections = null;
	private String version = "1.0";
	private String basePath = "http://localhost:8080/api";
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Test
	public void testGetApi() throws Exception {
		reflections = new Reflections(new ConfigurationBuilder().setUrls(ClasspathHelper.forPackage("org.jsondoc.core")));
		Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Api.class);
		
		JSONDoc apiDoc = new JSONDoc(version, basePath);
		apiDoc.setApis(JSONDocUtils.createApiDocs(classes));
		
		classes = reflections.getTypesAnnotatedWith(ApiObject.class);
		apiDoc.setObjects(JSONDocUtils.createApiObjectDocs(classes));
		
    	System.out.println(objectMapper.writeValueAsString(apiDoc));
	}

    @Test
    public void testApiVersionAnnotations() {
        ApiObjectDoc object = JSONDocUtils.createApiObjectDoc(VersionedObject.class);
        assertThat(object.getName(), is("versioned object"));
        assertThat(object.getVersion().getSince(), is(2));

        ApiObjectFieldDoc field = object.getFields().get(0);
        assertThat(field.getName(), is("field"));
        assertThat(field.getVersion().getSince(), is(5));

        ApiDoc api = JSONDocUtils.createApiDoc(VersionedApi.class);
        assertThat(api.getName(), is("versioned api"));
        assertThat(api.getDescription(), is("versioned"));
        assertThat(api.getVersion().getSince(), is(1));

        ApiMethodDoc method = api.getMethods().get(0);
        assertThat(method.getVersion().getSince(), is(4));
        assertThat(method.getVersion().getUntil(), is(10));

        List<ApiParamDoc> parameters = method.getUrlparameters();
        ApiParamDoc paramA = parameters.get(0);
        assertThat(paramA.getName(), is("a"));
        assertThat(paramA.getVersion().getSince(), is(1));

        ApiParamDoc paramB = parameters.get(1);
        assertThat(paramB.getName(), is("b"));
        assertThat(paramB.getVersion().getSince(), is(0));
        assertThat(paramB.getVersion().getUntil(), is(10));

        ApiParamDoc paramC = parameters.get(2);
        assertThat(paramC.getName(), is("c"));
        assertThat(paramC.getVersion(), is(nullValue()));
    }

}