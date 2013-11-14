package org.jsondoc.sample.pojo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectProperty;

@ApiObject(name = "city",category = "Geodata")
@XmlRootElement
public class City extends Location {

	@ApiObjectProperty(description = "The name of the city")
	@XmlElement
	private String name;

	public City() {

	}

	public City(String name, Integer population, Integer squarekm) {
		super(population, squarekm);
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
