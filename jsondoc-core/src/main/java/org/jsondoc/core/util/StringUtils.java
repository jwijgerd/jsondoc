package org.jsondoc.core.util;

import static com.google.common.base.CharMatcher.JAVA_UPPER_CASE;
import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;

/**
 * Local string related utilities.  Yes, some of these things are available elsewhere,
 * but why import another dependency for only a few lines of code?
 */
public class StringUtils {

    public static boolean hasText(String str) {
        return str != null && str.trim().length() > 0;
    }

    public static String splitCamelCase(String str) {

        List<String> result = newArrayList();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (JAVA_UPPER_CASE.matches(c)) {
                if (builder.length() > 0) {
                    result.add(builder.toString());
                }
                builder = new StringBuilder();
                builder.append(c);
            } else {
                builder.append(c);
            }
        }

        if (builder.length() > 0) {
            result.add(builder.toString());
        }

        return Joiner.on(" ").join(Iterables.transform(result, new LowerCaseFunction()));
    }

    private static class LowerCaseFunction implements Function<String,String> {
        @Override
        public String apply(String s) {
            return s.toLowerCase();
        }
    }
}
