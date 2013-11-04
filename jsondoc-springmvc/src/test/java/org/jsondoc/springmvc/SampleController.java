package org.jsondoc.springmvc;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Daniel Ostermeier
 */
@Controller
@RequestMapping(value = "/samples")
public class SampleController {

    @RequestMapping(method = RequestMethod.GET,value = "/")
    public List<Sample> getSamples() {
        return null;
    }

    @RequestMapping(method = RequestMethod.POST,value = "/")
    public void createSample(Sample sample) {

    }

    @RequestMapping(method = RequestMethod.PUT,value = "/")
    public void updateSample(Sample sample) throws SampleException {

    }

    @RequestMapping(method = RequestMethod.DELETE,value = "/")
    public void deleteSample(String id) throws Exception {

    }

    @RequestMapping(method = RequestMethod.GET,value = "/{sampleId}")
    public Sample getSample(@PathVariable String sampleId) {
        return null;
    }
}
