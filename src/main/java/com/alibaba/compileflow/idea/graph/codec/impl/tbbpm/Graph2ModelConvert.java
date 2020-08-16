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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.alibaba.compileflow.idea.graph.model.BpmModel;
import com.alibaba.compileflow.idea.graph.model.BpmnModel;
import com.alibaba.compileflow.idea.graph.model.EdgeModel;
import com.alibaba.compileflow.idea.graph.model.TransitionModel;
import com.alibaba.compileflow.idea.graph.mxgraph.Graph;
import com.alibaba.compileflow.idea.graph.model.BaseNodeModel;
import com.alibaba.compileflow.idea.graph.model.LoopProcessNodeModel;
import com.alibaba.compileflow.idea.graph.util.StringUtil;

import com.intellij.openapi.diagnostic.Logger;
import com.mxgraph.model.mxCell;

/**
 * @author xuan
 * @since 2020/4/22
 */
public class Graph2ModelConvert {
    private static final Logger log = Logger.getInstance(Graph2ModelConvert.class);

    private Graph graph;

    public Graph2ModelConvert(Graph graph) {
        this.graph = graph;
    }

    public BpmModel toBpmModel() {
        BpmModel oldBpmModel = graph.getModel().getBpmModel();

        BpmModel newBpmModel;
        if (oldBpmModel instanceof BpmnModel) {
            newBpmModel = BpmnModel.of();
        } else {
            newBpmModel = BpmModel.of();
        }

        Map<String, Object> cellMap = graph.getModel().getCells();

        if (null != cellMap) {
            List<BaseNodeModel> nodeList = buildNodeList(cellMap);
            attachTransition(nodeList, cellMap);

            try {
                //For loopProcessNode
                assembleLoopProcessNode(nodeList);

                newBpmModel.setName(oldBpmModel.getName());
                newBpmModel.setType(oldBpmModel.getType());
                newBpmModel.setVars(oldBpmModel.getVars());
                newBpmModel.setCode(oldBpmModel.getCode());
                newBpmModel.setDescription(oldBpmModel.getDescription());
                newBpmModel.setAllNodes(nodeList);
                return newBpmModel;
            } catch (Exception e) {
                log.warn("save file exception", e);
            }
        }
        return null;
    }

    private List<BaseNodeModel> buildNodeList(Map<String, Object> cellMap) {
        List<BaseNodeModel> nodeList = new ArrayList<>();

        cellMap.forEach((k, v) -> {
            mxCell cell = (mxCell)v;
            if (cell != null && cell.isVertex()) {
                nodeList.add(BaseNodeModel.getBaseNodeFromCellValue(cell.getValue()));
            }
        });

        return nodeList;
    }

    private void attachTransition(List<BaseNodeModel> nodeList, Map<String, Object> cellMap) {

        //clear
        nodeList.forEach(node -> {
            node.setOutTransitions(null);
            node.setInTransitions(null);
        });

        //attach
        cellMap.forEach((k, v) -> {
            mxCell cell = (mxCell)v;
            if (cell != null && cell.isEdge()) {
                if (cell.getValue() instanceof EdgeModel) {
                    EdgeModel edgeModel = (EdgeModel)cell.getValue();
                    TransitionModel transitionModel = edgeModel.getTransition();
                    BaseNodeModel currentNode = edgeModel.getCurrentNode();
                    if (null != transitionModel && null != currentNode) {
                        nodeList.forEach(node -> {
                            if (node == currentNode) {
                                node.addOutTransition(transitionModel);
                            }
                            if (Objects.equals(node.getId(), transitionModel.getTo())) {
                                node.addInTransition(transitionModel);
                            }
                        });
                    }
                }
            }
        });
    }

    private void assembleLoopProcessNode(List<BaseNodeModel> nodeList) {
        if (null == nodeList) {
            return;
        }

        Map<String, BaseNodeModel> id2NodeMap = new HashMap<>();
        nodeList.forEach(node -> id2NodeMap.put(node.getId(), node));

        Set<String> needDeleteNodeIdSet = new HashSet<>();
        nodeList.forEach((node -> {
            if (node instanceof LoopProcessNodeModel) {
                List<BaseNodeModel> subList = new ArrayList<>();
                String startNodeId = ((LoopProcessNodeModel)node).getStartNodeId();
                fetchSubList(subList, needDeleteNodeIdSet, id2NodeMap, startNodeId);
                ((LoopProcessNodeModel)node).setAllNodes(subList);
            }
        }));
        nodeList.removeIf(s -> needDeleteNodeIdSet.contains(s.getId()));
    }

    private void fetchSubList(List<BaseNodeModel> subList, Set<String> needDeleteNodeIdSet,
        Map<String, BaseNodeModel> id2NodeMap, String nodeId) {

        if (StringUtil.isEmpty(nodeId)) {
            return;
        }

        BaseNodeModel baseNodeModel = id2NodeMap.get(nodeId);
        if (null == baseNodeModel) {
            return;
        }

        subList.add(baseNodeModel);
        needDeleteNodeIdSet.add(baseNodeModel.getId());

        List<TransitionModel> transitionList = baseNodeModel.getOutTransitions();
        if (null != transitionList) {
            transitionList.forEach(transitionModel -> {
                fetchSubList(subList, needDeleteNodeIdSet, id2NodeMap, transitionModel.getTo());
            });
        }
    }

}
