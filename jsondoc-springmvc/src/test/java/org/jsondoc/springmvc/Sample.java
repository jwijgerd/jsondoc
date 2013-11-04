package org.jsondoc.springmvc;

import java.util.List;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

/**
 * @author Daniel Ostermeier
 */
@ApiObject(name = "sample")
public class Sample {

    @ApiObjectField(description = "parametrized field")
    private List<?> parametrized;
}
