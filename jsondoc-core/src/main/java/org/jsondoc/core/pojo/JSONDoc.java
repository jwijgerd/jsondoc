package org.jsondoc.core.pojo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.jsondoc.core.visitor.Visitable;
import org.jsondoc.core.visitor.Visitor;

import com.google.common.collect.Maps;

public final class JSONDoc implements Visitable {

	private String version;
	private String basePath;

    private List<ApiDoc> apis = new ArrayList<ApiDoc>();
	private Map<String,List<ApiObjectDoc>> objects = Maps.newHashMap();

	public JSONDoc(String version, String basePath) {
		this.version = version;
		this.basePath = basePath;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public List<ApiDoc> getApis() {
		return apis;
	}

	public void setApis(List<ApiDoc> apis) {
		this.apis = apis;
	}

	public Map<String,List<ApiObjectDoc>> getObjects() {
		return objects;
	}

	public void setObjects(Map<String,List<ApiObjectDoc>> objects) {
		this.objects = objects;
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
