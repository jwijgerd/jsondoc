package org.jsondoc.core.visitor;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiVersion;

/**
 * @author Daniel Ostermeier
 */
@Api(name = "versioned controller", description = "versioned controller")
@ApiVersion(since = 3, until = 10)
public class VersionedController {

    @ApiVersion(since = 5, until = 7)
    @ApiMethod(path = "/endpoint/a")
    public void endpointA() {

    }
}
