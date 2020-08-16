package com.alibaba.compileflow.idea.graph.util;

import java.util.Collection;

/**
 * @author xuan
 * @since 2020/7/29
 */
public class CollectionUtil {

    public static boolean isEmpty(Collection<?> coll) {
        return coll == null || coll.isEmpty();
    }
}
