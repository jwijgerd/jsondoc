package org.jsondoc.core;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;
import org.jsondoc.core.annotation.ApiVersion;

/**
 * @author Daniel Ostermeier
 */
@ApiVersion(since = 2)
@ApiObject(name = "versioned object")
public class VersionedObject {

    @ApiVersion(since = 5)
    @ApiObjectField(description = "versioned field")
    private String field;
}
