package org.jsondoc.core;

import java.util.Map;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectProperty;

@ApiObject(name="child")
public class Child extends Parent {
	
	@ApiObjectProperty(description="the test age")
	private Integer age;
	
	@ApiObjectProperty(description="the test games")
	private Long[] games;
	
	@ApiObjectProperty(description="the scores for each game")
	private Map<String, Integer> scores;

}
