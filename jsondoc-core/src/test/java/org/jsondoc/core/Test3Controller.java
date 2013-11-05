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
import org.springframework.http.MediaType;

/**
 * @author Daniel Ostermeier
 */
@Api(name="Test3Controller", description="My test controller #1")
public class Test3Controller {

    @ApiMethod(
            path="/test1",
            verb= ApiVerb.GET,
            description="test method for controller 1",
            consumes={MediaType.APPLICATION_JSON_VALUE},
            produces={MediaType.APPLICATION_JSON_VALUE}
    )
    @ApiResponseObject
    public Typed<Child> get(@ApiParam(name="id", description="abc") String id, @ApiParam(name="count", description="xyz") Integer count, @ApiBodyObject String name) {
        return null;
    }
}