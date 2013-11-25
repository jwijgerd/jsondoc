package org.jsondoc.core.visitor;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;

import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.JSONDoc;

/**
 * @author Daniel Ostermeier
 */
public class MergeApiWithSameNameVisitor extends AbstractDocVisitor<Void> {

    @Override
    public Void visit(JSONDoc doc) {

        // Merge API doc objects of the same name.
        Map<String,ApiDoc> mergedDocs = newHashMap();
        for (ApiDoc apiDoc : doc.getApis()) {
            if (mergedDocs.containsKey(apiDoc.getName())) {
                ApiDoc existingDoc = mergedDocs.get(apiDoc.getName());
                existingDoc.getMethods().addAll(apiDoc.getMethods());
            } else {
                mergedDocs.put(apiDoc.getName(), apiDoc);
            }
        }
        doc.setApis(newArrayList(mergedDocs.values()));

        return null;
    }
}
