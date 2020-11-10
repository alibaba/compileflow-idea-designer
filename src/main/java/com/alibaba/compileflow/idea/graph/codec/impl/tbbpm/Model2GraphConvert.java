/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.compileflow.idea.graph.codec.impl.tbbpm;

import com.alibaba.compileflow.idea.graph.model.BreakNodeModel;
import com.alibaba.compileflow.idea.graph.model.ContinueNodeModel;
import com.alibaba.compileflow.idea.graph.model.EdgeModel;
import com.alibaba.compileflow.idea.graph.model.TransitionModel;
import com.alibaba.compileflow.idea.graph.model.GeolocationModel;
import com.alibaba.compileflow.idea.graph.mxgraph.Graph;
import com.alibaba.compileflow.idea.graph.model.BpmModel;
import com.alibaba.compileflow.idea.graph.model.AutoTaskNodeModel;
import com.alibaba.compileflow.idea.graph.model.BaseNodeModel;
import com.alibaba.compileflow.idea.graph.model.DecisionNodeModel;
import com.alibaba.compileflow.idea.graph.model.EndNodeModel;
import com.alibaba.compileflow.idea.graph.model.LoopProcessNodeModel;
import com.alibaba.compileflow.idea.graph.model.NoteNodeModel;
import com.alibaba.compileflow.idea.graph.model.ScriptTaskNodeModel;
import com.alibaba.compileflow.idea.graph.model.StartNodeModel;
import com.alibaba.compileflow.idea.graph.model.SubBpmNodeModel;
import com.alibaba.compileflow.idea.graph.model.UserTaskNodeModel;
import com.alibaba.compileflow.idea.graph.model.WaitTaskNodeModel;

import com.intellij.openapi.diagnostic.Logger;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.layout.*;
import com.mxgraph.layout.orthogonal.mxOrthogonalLayout;

import javax.swing.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wuxiang
 * @author xuan
 */
public class Model2GraphConvert {

    private static final Logger logger = Logger.getInstance(Model2GraphConvert.class);

    private Graph graph;
    private BpmModel bpmModel;

    public Model2GraphConvert(Graph graph, BpmModel bpmModel) {

        this.graph = graph;
        this.bpmModel = bpmModel;
        this.graph.getModel().setBpmModel(bpmModel);
    }

    public void drawGraph() {

        Object parent = graph.getDefaultParent();
        graph.getModel().beginUpdate();
        try {
            doDraw(bpmModel.getAllNodes(), parent);
            mxIGraphLayout layout = createLayout("mxOrthogonalLayout");
            layout.execute(parent);
        } catch (Exception e) {
            logger.error("error=====", e);
        } finally {
            graph.getModel().endUpdate();
        }
    }

    private mxIGraphLayout createLayout(String ident) {
        mxIGraphLayout layout = null;
        if (ident != null) {
            if ("verticalHierarchical".equals(ident)) {
                layout = new mxHierarchicalLayout(graph);
            } else if ("mxOrthogonalLayout".equals(ident)) {
                layout = new mxOrthogonalLayout(graph);
            } else if ("horizontalHierarchical".equals(ident)) {
                layout = new mxHierarchicalLayout(graph, JLabel.WEST);
            } else if ("verticalTree".equals(ident)) {
                layout = new mxCompactTreeLayout(graph, false);
            } else if ("horizontalTree".equals(ident)) {
                layout = new mxCompactTreeLayout(graph, true);
            } else if ("parallelEdges".equals(ident)) {
                layout = new mxParallelEdgeLayout(graph);
            } else if ("placeEdgeLabels".equals(ident)) {
                layout = new mxEdgeLabelLayout(graph);
            } else if ("organicLayout".equals(ident)) {
                layout = new mxOrganicLayout(graph);
            } else if ("circleLayout".equals(ident)) {
                layout = new mxCircleLayout(graph);
            }
        }
        return layout;
    }

    private void doDraw(List<BaseNodeModel> nodeList, Object parent) {
        if (null == nodeList) {
            return;
        }
        Map<String, BaseNodeModel> id2NodeMap = new HashMap<>();
        Map<String, Object> id2CellMap = new HashMap<>();

        //step1: draw nodes
        for (BaseNodeModel node : nodeList) {
            Object cell = doVertexDraw(parent, node);
            id2NodeMap.put(node.getId(), node);
            id2CellMap.put(node.getId(), cell);
            if (node instanceof LoopProcessNodeModel) {
                doDraw(((LoopProcessNodeModel)node).getAllNodes(), cell);
            }
        }

        //step2: draw edges
        for (BaseNodeModel node : nodeList) {
            List<TransitionModel> transitionList = node.getOutTransitions();
            if (null != transitionList) {
                for (TransitionModel transition : transitionList) {
                    doEdgeDraw(node, id2NodeMap.get(transition.getTo()), transition, parent,
                        id2CellMap.get(node.getId()), id2CellMap.get(transition.getTo()));
                }
            }
        }
    }

    private void doEdgeDraw(BaseNodeModel fromNode, BaseNodeModel toNode, TransitionModel transition,
        Object parent, Object currentCell, Object toCell) {

        if (null == fromNode || null == toNode || null == transition || null == currentCell || null == toCell) {
            return;
        }

        EdgeModel edgeModel = new EdgeModel(fromNode, transition);
        graph.insertEdge(parent, toNode.getId(), edgeModel, currentCell, toCell);
    }

    private Object doVertexDraw(Object parent, BaseNodeModel model) {

        Object v;
        if (model instanceof DecisionNodeModel) {

            GeolocationModel g1 = new GeolocationModel(model.getG());
            v = graph.insertVertex(parent, model.getId(), model, g1.x, g1.y, g1.w,
                g1.h, "gateway");
        } else if (model instanceof SubBpmNodeModel) {
            GeolocationModel g1 = new GeolocationModel(model.getG());
            v = graph.insertVertex(parent, model.getId(), model, g1.x, g1.y, g1.w,
                g1.h, "subprocess");
        } else if (model instanceof LoopProcessNodeModel) {
            LoopProcessNodeModel loopModel = (LoopProcessNodeModel)model;
            GeolocationModel g1 = new GeolocationModel(loopModel.getG());
            v = graph.insertVertex(parent, loopModel.getId(), loopModel, g1.x, g1.y, g1.w,
                g1.h, "loop");
        } else if (model instanceof StartNodeModel) {
            model.setName("开始");
            GeolocationModel g1 = new GeolocationModel(model.getG());
            v = graph.insertVertex(parent, null, model, g1.x, g1.y, g1.w,
                g1.h, "start");
        } else if (model instanceof EndNodeModel) {
            model.setName("结束");
            GeolocationModel g1 = new GeolocationModel(model.getG());
            v = graph.insertVertex(parent, null, model, g1.x, g1.y, g1.w,
                g1.h, "end");
        } else if (model instanceof NoteNodeModel) {
            GeolocationModel g1 = new GeolocationModel(model.getG());
            v = graph.insertVertex(parent, null, model, g1.x, g1.y, g1.w,
                g1.h, "note");
        } else if (model instanceof ScriptTaskNodeModel) {
            GeolocationModel g1 = new GeolocationModel(model.getG());
            v = graph.insertVertex(parent, null, model, g1.x, g1.y, g1.w,
                g1.h, "script");
        } else if (model instanceof UserTaskNodeModel) {
            GeolocationModel g1 = new GeolocationModel(model.getG());
            v = graph.insertVertex(parent, null, model, g1.x, g1.y, g1.w,
                g1.h, "default");
        } else if (model instanceof WaitTaskNodeModel) {
            GeolocationModel g1 = new GeolocationModel(model.getG());
            v = graph.insertVertex(parent, null, model, g1.x, g1.y, g1.w,
                g1.h, "default");
        } else if (model instanceof AutoTaskNodeModel) {
            GeolocationModel g1 = new GeolocationModel(model.getG());
            v = graph.insertVertex(parent, null, model, g1.x, g1.y, g1.w,
                g1.h, "auto");
        } else if (model instanceof ContinueNodeModel) {
            GeolocationModel g1 = new GeolocationModel(model.getG());
            v = graph.insertVertex(parent, null, model, g1.x, g1.y, g1.w,
                g1.h, "continue");
        } else if (model instanceof BreakNodeModel) {
            GeolocationModel g1 = new GeolocationModel(model.getG());
            v = graph.insertVertex(parent, null, model, g1.x, g1.y, g1.w,
                g1.h, "break");
        } else {
            GeolocationModel g1 = new GeolocationModel(model.getG());
            v = graph.insertVertex(parent, null, model, g1.x, g1.y, g1.w,
                g1.h, "default");
        }
        return v;
    }

}
