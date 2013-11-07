package org.jsondoc.springmvc;

import java.util.List;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectProperty;

/**
 * @author Daniel Ostermeier
 */
@ApiObject(name = "sample")
public class Sample {

    @ApiObjectProperty(description = "parametrized field")
    private List<?> parametrized;
}
