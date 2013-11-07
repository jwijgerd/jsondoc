package org.jsondoc.core;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiBodyObject;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiParam;
import org.jsondoc.core.annotation.ApiResponseObject;
import org.jsondoc.core.pojo.ApiVerb;

/**
 * @author Daniel Ostermeier
 */
@Api(name="Test3Controller", description="My test controller #1")
public class Test3Controller {

    @ApiMethod(
            path="/test1",
            verb= ApiVerb.GET,
            description="test method for controller 1",
            consumes={"application/xml"},
            produces={"application/xml"}
    )
    @ApiResponseObject
    public Typed<Child> get(@ApiParam(name="id", description="abc") String id, @ApiParam(name="count", description="xyz") Integer count, @ApiBodyObject String name) {
        return null;
    }
}