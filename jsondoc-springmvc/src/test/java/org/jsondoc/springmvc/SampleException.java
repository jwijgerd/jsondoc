package org.jsondoc.springmvc;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Daniel Ostermeier
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SampleException extends Exception {
}
