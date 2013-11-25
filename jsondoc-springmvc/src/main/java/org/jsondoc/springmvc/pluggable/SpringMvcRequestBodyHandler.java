package org.jsondoc.springmvc.pluggable;

import static org.jsondoc.core.util.JSONDocSupport.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

import org.jsondoc.core.pluggable.ApiMethodAnnotationHandler;
import org.jsondoc.core.pojo.ApiBodyObjectDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Daniel Ostermeier
 * @author Geert Vos
 */
public class SpringMvcRequestBodyHandler implements ApiMethodAnnotationHandler {

    @Override
    public boolean canHandle(AnnotatedElement candidate, Annotation annotation) {
    	return candidate instanceof Method && findRequestBodyType((Method)candidate) >= 0;
    }


	@Override
	public void handle(AnnotatedElement element, ApiMethodDoc doc) {
		Method m = (Method)element;
		int index = findRequestBodyType(m);

        String[] objectDetails = getBodyObject(m, index);
        ApiBodyObjectDoc body = new ApiBodyObjectDoc();
        body.setObject(objectDetails[0]);
        body.setMapKeyObject(objectDetails[1]);
        body.setMapValueObject(objectDetails[2]);
        body.setMap(objectDetails[3]);
        body.setMultiple("false");
		doc.setBodyobject(body);
	}
	
	private int findRequestBodyType(Method m) {
		Annotation[][] annotations = m.getParameterAnnotations();
		for(int i=0;i<annotations.length;i++) {
			for(Annotation a : annotations[i]) {
				if(a instanceof RequestBody) {
					return i;
				}
			}
		}
		return -1;
	}

    
}


