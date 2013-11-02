package org.jsondoc.springmvc;

import org.jsondoc.core.annotation.Api;
import org.springframework.stereotype.Controller;

/**
 * @author Daniel Ostermeier
 */
@Api(name = "annotated name", description = "annotated description")
@Controller
public class AnnotatedController {
}
