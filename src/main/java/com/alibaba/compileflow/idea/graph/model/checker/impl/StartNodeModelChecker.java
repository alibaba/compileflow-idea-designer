package com.alibaba.compileflow.idea.graph.model.checker.impl;

import java.util.Optional;

import com.alibaba.compileflow.idea.graph.model.BaseNodeModel;
import com.alibaba.compileflow.idea.graph.model.StartNodeModel;
import com.alibaba.compileflow.idea.graph.model.checker.ModelChecker;
import com.alibaba.compileflow.idea.graph.util.CollectionUtil;
import com.alibaba.compileflow.idea.graph.util.StringUtil;

/**
 * @author xuan
 * @since 2020/7/29
 */
public class StartNodeModelChecker implements ModelChecker {

    @Override
    public Optional<String> execute(BaseNodeModel model) {
        StartNodeModel start = (StartNodeModel)model;

        StringBuilder message = new StringBuilder();
        if (StringUtil.isEmpty(start.getId())) {
            message.append("Id is empty;");
        }
        if (CollectionUtil.isEmpty(start.getOutTransitions())) {
            message.append("No next node;");
        }
        if (message.length() > 0) {
            return Optional.of("start[id=" + start.getId() + "]has problem:" + message.toString());
        }
        return Optional.empty();
    }

    @Override
    public boolean match(BaseNodeModel model) {
        return model instanceof StartNodeModel;
    }

}
