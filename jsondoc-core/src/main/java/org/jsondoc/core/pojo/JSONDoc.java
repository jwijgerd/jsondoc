package org.jsondoc.core.pojo;

import java.util.Set;
import java.util.TreeSet;

import org.jsondoc.core.visitor.Visitable;
import org.jsondoc.core.visitor.Visitor;

public final class JSONDoc implements Visitable {

	private String version;
	private String basePath;

    private Set<ApiDoc> apis = new TreeSet<ApiDoc>();
	private Set<ApiObjectDoc> objects = new TreeSet<ApiObjectDoc>();

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

	public Set<ApiDoc> getApis() {
		return apis;
	}

	public void setApis(Set<ApiDoc> apis) {
		this.apis = apis;
	}

	public Set<ApiObjectDoc> getObjects() {
		return objects;
	}

	public void setObjects(Set<ApiObjectDoc> objects) {
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
