package com.alibaba.compileflow.idea.graph.model.checker.impl;

import java.util.Optional;

import com.alibaba.compileflow.idea.graph.model.BaseNodeModel;
import com.alibaba.compileflow.idea.graph.model.SubBpmNodeModel;
import com.alibaba.compileflow.idea.graph.model.checker.ModelChecker;
import com.alibaba.compileflow.idea.graph.util.CollectionUtil;
import com.alibaba.compileflow.idea.graph.util.StringUtil;

/**
 * @author xuan
 * @since 2020/7/29
 */
public class SubBpmNodeModelChecker implements ModelChecker {

    @Override
    public Optional<String> execute(BaseNodeModel model) {
        SubBpmNodeModel subBpm = (SubBpmNodeModel)model;

        StringBuilder message = new StringBuilder();
        if (StringUtil.isEmpty(subBpm.getId())) {
            message.append("Id is empty;");
        }
        if (StringUtil.isEmpty(subBpm.getType())) {
            message.append("Type is empty;");
        }
        if (StringUtil.isEmpty(subBpm.getSubBpmCode())) {
            message.append("SubBpmCode is empty;");
        }
        if (CollectionUtil.isEmpty(subBpm.getOutTransitions())) {
            message.append("No next node;");
        }

        if (message.length() > 0) {
            return Optional.of("subBpm[id=" + subBpm.getId() + "]has problem:" + message.toString());
        }
        return Optional.empty();
    }

    @Override
    public boolean match(BaseNodeModel model) {
        return model instanceof SubBpmNodeModel;
    }

}
