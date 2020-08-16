package com.alibaba.compileflow.idea.graph.model.checker.impl;

import java.util.Optional;

import com.alibaba.compileflow.idea.graph.model.BaseNodeModel;
import com.alibaba.compileflow.idea.graph.model.DecisionNodeModel;
import com.alibaba.compileflow.idea.graph.model.checker.ModelChecker;
import com.alibaba.compileflow.idea.graph.util.CollectionUtil;
import com.alibaba.compileflow.idea.graph.util.StringUtil;

/**
 * @author xuan
 * @since 2020/7/29
 */
public class DecisionNodeModelChecker implements ModelChecker {

    @Override
    public Optional<String> execute(BaseNodeModel model) {
        DecisionNodeModel decision = (DecisionNodeModel)model;

        StringBuilder message = new StringBuilder();
        if (StringUtil.isEmpty(decision.getId())) {
            message.append("Id is empty;");
        }

        if (CollectionUtil.isEmpty(decision.getOutTransitions())) {
            message.append("No next node;");
        }

        if (message.length() > 0) {
            return Optional.of("decision[id=" + decision.getId() + "]has problem:" + message.toString());
        }
        return Optional.empty();
    }

    @Override
    public boolean match(BaseNodeModel model) {
        return model instanceof DecisionNodeModel;
    }

}
