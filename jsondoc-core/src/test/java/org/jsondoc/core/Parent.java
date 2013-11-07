package org.jsondoc.core;

import java.util.List;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectProperty;

@ApiObject(name="parent")
public class Parent extends Grandparent {
	
	@ApiObjectProperty(description="the test name")
	private String name;

	@ApiObjectProperty(description="the test name")
	private List<Child> children;
}
