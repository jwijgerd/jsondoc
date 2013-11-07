package org.jsondoc.core;

import java.util.List;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiBodyObject;
import org.jsondoc.core.annotation.ApiError;
import org.jsondoc.core.annotation.ApiErrors;
import org.jsondoc.core.annotation.ApiHeader;
import org.jsondoc.core.annotation.ApiHeaders;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiParam;
import org.jsondoc.core.annotation.ApiResponseObject;
import org.jsondoc.core.pojo.ApiVerb;

@Api(name="Test1Controller", description="My test controller #1")
public class Test1Controller {
	
	@ApiMethod(
			path="/test1", 
			verb=ApiVerb.GET, 
			description="test method for controller 1", 
			consumes={"application/json"},
			produces={"application/json"}
	)
	@ApiHeaders(headers={
			@ApiHeader(name="application_id", description="The application's ID")
	})
	@ApiErrors(apierrors={
			@ApiError(code="1000", description="A test error #1"),
			@ApiError(code="2000", description="A test error #2")
	})
	public @ApiResponseObject List<Child> get(@ApiParam(name="id", description="abc") String id, @ApiParam(name="count", description="xyz") Integer count, @ApiBodyObject String name) {
		return null;
	}

}
