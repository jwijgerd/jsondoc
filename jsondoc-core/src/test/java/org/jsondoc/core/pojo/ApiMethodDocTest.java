package org.jsondoc.core.pojo;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class ApiMethodDocTest {

    private ApiMethodDoc method;

    @Before
    public void setUp() {
        method = new ApiMethodDoc();
    }

    @Test
    public void testGetErrorByStatusAndCode() {
        method.getApierrors().add(apiError("a", "b", null));
        method.getApierrors().add(apiError("c", null, null));
        method.getApierrors().add(apiError(null, "d", null));

        assertThat(method.getError("a", "b"), is(notNullValue()));
        assertThat(method.getError("a", null), is(nullValue()));
        assertThat(method.getError("c", null), is(notNullValue()));
        assertThat(method.getError(null, "b"), is(nullValue()));
        assertThat(method.getError(null, "d"), is(notNullValue()));
    }

    private ApiErrorDoc apiError(String status, String code, String description) {
        ApiErrorDoc error = new ApiErrorDoc();
        error.setStatus(status);
        error.setCode(code);
        error.setDescription(description);
        return error;
    }
}
