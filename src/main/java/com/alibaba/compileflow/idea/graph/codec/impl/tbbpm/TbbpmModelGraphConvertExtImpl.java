package com.alibaba.compileflow.idea.graph.codec.impl.tbbpm;

import com.alibaba.compileflow.idea.graph.codec.ModelGraphConvertExt;
import com.alibaba.compileflow.idea.graph.model.BpmModel;
import com.alibaba.compileflow.idea.graph.mxgraph.Graph;

/**
 * @author xuan
 * @since 2020/7/25
 */
public class TbbpmModelGraphConvertExtImpl implements ModelGraphConvertExt {

    @Override
    public BpmModel toModel(Graph graph) {
        return new Graph2ModelConvert(graph).toBpmModel();
    }

    @Override
    public void drawGraph(Graph graph, BpmModel bpmModel) {
        new Model2GraphConvert(graph, bpmModel).drawGraph();
    }

}
