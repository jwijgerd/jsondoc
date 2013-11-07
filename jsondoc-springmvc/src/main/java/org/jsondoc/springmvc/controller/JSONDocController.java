package org.jsondoc.springmvc.controller;

import static org.jsondoc.core.util.JSONDocUtils.createApiDocs;
import static org.jsondoc.core.util.JSONDocUtils.createApiObjectDocs;

import javax.servlet.ServletContext;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.pojo.JSONDoc;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/jsondoc")
public class JSONDocController {
	@Autowired
	private ServletContext servletContext;
	private String version;
	private String basePath;

	public void setVersion(String version) {
		this.version = version;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	@RequestMapping(method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
	public JSONDoc getApi() {
        Reflections reflections = new Reflections(new ConfigurationBuilder().setUrls(ClasspathHelper.forWebInfClasses(servletContext)));
        JSONDoc apiDoc = new JSONDoc(version, basePath);
        apiDoc.setApis(createApiDocs(reflections.getTypesAnnotatedWith(Api.class)));
        apiDoc.setObjects(createApiObjectDocs(reflections.getTypesAnnotatedWith(ApiObject.class)));
        return apiDoc;
	}
}
