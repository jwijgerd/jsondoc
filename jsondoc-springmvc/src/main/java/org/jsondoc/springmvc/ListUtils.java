package org.jsondoc.springmvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Daniel Ostermeier
 */
public class ListUtils {

    public static <T> List<T> merge(T[] arrayA, T[] arrayB) {
        Set<T> result = new HashSet<T>();
        result.addAll(Arrays.asList(arrayA));
        result.addAll(Arrays.asList(arrayB));
        return new ArrayList<T>(result);
    }
}
