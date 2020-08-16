package com.alibaba.compileflow.idea.graph.util;

/**
 * @author xuan
 * @since 2020/7/17
 */
public interface Callback<P, R> {

    /**
     * callback action
     *
     * @param param param
     * @return result
     */
    R call(P param);
}
