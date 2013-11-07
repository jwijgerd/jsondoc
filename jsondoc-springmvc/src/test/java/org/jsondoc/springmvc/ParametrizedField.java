package org.jsondoc.springmvc;

import java.util.List;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectProperty;

/**
 * @author Daniel Ostermeier
 */
@ApiObject(name = "parametrized")
public class ParametrizedField {

    @ApiObjectProperty(description = "parametrized field")
    private List<?> parametrized;
}
