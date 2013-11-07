package org.jsondoc.core;

import java.util.List;
import java.util.Map;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiBodyObject;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiParam;
import org.jsondoc.core.annotation.ApiResponseObject;
import org.jsondoc.core.pojo.ApiVerb;

@Api(name="Test2Controller", description="My test controller #2")
public class Test2Controller {
	
	@ApiMethod(
			path="/test2", 
			verb=ApiVerb.POST, 
			description="test method for controller 2", 
			consumes={"application/json", "application/xml"},
			produces={"application/json", "application/xml"}
	)
	public @ApiResponseObject String save(@ApiBodyObject List<String> names) {
		return null;
	}
	
	@ApiMethod(
			path="/testMap", 
			verb=ApiVerb.GET, 
			description="map method for controller 2", 
			consumes={"application/json", "application/xml"},
			produces={"application/json", "application/xml"}
	)
	public @ApiResponseObject Map<String, Parent> map(@ApiBodyObject List<String> names) {
		return null;
	}
	
	@ApiMethod(
			path="/testMapBody", 
			verb=ApiVerb.GET, 
			description="map body method for controller 2", 
			consumes={"application/json", "application/xml"},
			produces={"application/json", "application/xml"}
	)
	public @ApiResponseObject String map(@ApiBodyObject Map<String, Parent> names) {
		return null;
	}
	
	@ApiMethod(
			path="/testDelete", 
			verb=ApiVerb.DELETE, 
			description="delete test method for controller 2", 
			consumes={},
			produces={"application/json", "application/xml"}
	)
	public @ApiResponseObject void delete(@ApiParam(name="parent", description="A parent object") Parent parent) {
		
	}

}
