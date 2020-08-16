package com.alibaba.compileflow.idea.graph.codec;

import com.alibaba.compileflow.idea.graph.model.BpmModel;
import com.alibaba.compileflow.idea.graph.mxgraph.Graph;

/**
 * @author xuan
 * @since 2020/7/25
 */
public interface ModelGraphConvertExt {

    BpmModel toModel(Graph graph);

    void drawGraph(Graph graph, BpmModel bpmModel);
}
