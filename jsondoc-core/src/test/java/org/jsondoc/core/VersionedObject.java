package org.jsondoc.core;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectProperty;
import org.jsondoc.core.annotation.ApiVersion;

/**
 * @author Daniel Ostermeier
 */
@ApiVersion(since = 2)
@ApiObject(name = "versioned object")
public class VersionedObject {

    @ApiVersion(since = 5)
    @ApiObjectProperty(description = "versioned field")
    private String field;
}
