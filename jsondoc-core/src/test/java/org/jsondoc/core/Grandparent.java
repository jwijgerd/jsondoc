package org.jsondoc.core;

import java.util.Date;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectProperty;

@ApiObject(name="grandparent", show=false)
public class Grandparent {
	
	@ApiObjectProperty(description="the test surname")
	private String surname;
	
	@ApiObjectProperty(description="the date of birth", format="yyyy-MM-dd HH:mm:ss")
	private Date dob;
	
}
