package org.jsondoc.core;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiParam;
import org.jsondoc.core.annotation.ApiVersion;
import org.jsondoc.core.pojo.ApiVerb;

/**
 * @author Daniel Ostermeier
 */
@Api(name = "versioned api", description = "versioned")
@ApiVersion(since = 1)
public class VersionedApi {

    @ApiVersion(since = 4, until = 10)
    @ApiMethod(path = "/path", description = "method description", verb = ApiVerb.GET)
    public Object apiMethod(@ApiParam(name="a") @ApiVersion(since = 1) String paramA,
                            @ApiParam(name="b") @ApiVersion(until = 10) String paramB,
                            @ApiParam(name="c") String paramC) {
        return null;
    }
}
