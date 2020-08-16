package com.alibaba.compileflow.idea.graph.model.checker.impl;

import java.util.Optional;

import com.alibaba.compileflow.idea.graph.model.BaseNodeModel;
import com.alibaba.compileflow.idea.graph.model.EndNodeModel;
import com.alibaba.compileflow.idea.graph.model.checker.ModelChecker;
import com.alibaba.compileflow.idea.graph.util.CollectionUtil;
import com.alibaba.compileflow.idea.graph.util.StringUtil;

/**
 * @author xuan
 * @since 2020/7/29
 */
public class EndNodeModelChecker implements ModelChecker {

    @Override
    public Optional<String> execute(BaseNodeModel model) {
        EndNodeModel end = (EndNodeModel)model;

        StringBuilder message = new StringBuilder();
        if (StringUtil.isEmpty(end.getId())) {
            message.append("Id is empty;");
        }

        if (message.length() > 0) {
            return Optional.of("end[id=" + end.getId() + "]has problem:" + message.toString());
        }
        return Optional.empty();
    }

    @Override
    public boolean match(BaseNodeModel model) {
        return model instanceof EndNodeModel;
    }

}
