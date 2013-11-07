package org.jsondoc.sample.pojo;

import javax.xml.bind.annotation.XmlElement;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectProperty;

@ApiObject(name = "location", show = false)
public class Location {

	@ApiObjectProperty(description = "The population of the location")
	@XmlElement
	private Integer population;
	@ApiObjectProperty(description = "The square km of the location")
	@XmlElement
	private Integer squarekm;

	public Location() {

	}

	public Location(Integer population, Integer squarekm) {
		super();
		this.population = population;
		this.squarekm = squarekm;
	}

	public Integer getPopulation() {
		return population;
	}

	public Integer getSquarekm() {
		return squarekm;
	}

}
