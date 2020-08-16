package com.alibaba.compileflow.idea.graph.model.checker;

import java.util.Optional;

import com.alibaba.compileflow.idea.graph.model.BaseNodeModel;

/**
 * @author xuan
 * @since 2020/7/29
 */
public interface ModelChecker {

    /**
     * Execute model check
     *
     * @param model node model
     * @return if has error return message else return Optional.empty()
     */
    Optional<String> execute(BaseNodeModel model);

    /**
     * Match the model is execute check
     *
     * @param model node model
     * @return true/false
     */
    boolean match(BaseNodeModel model);

}
