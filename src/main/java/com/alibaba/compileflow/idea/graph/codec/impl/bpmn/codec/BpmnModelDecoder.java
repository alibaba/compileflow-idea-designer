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
package com.alibaba.compileflow.idea.graph.codec.impl.bpmn.codec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.compileflow.idea.graph.util.xml.Node;
import com.alibaba.compileflow.idea.graph.util.xml.XmlUtil;
import com.alibaba.compileflow.idea.graph.model.BpmnModel;
import com.alibaba.compileflow.idea.graph.model.TransitionModel;
import com.alibaba.compileflow.idea.graph.model.VarModel;
import com.alibaba.compileflow.idea.graph.model.AutoTaskNodeModel;
import com.alibaba.compileflow.idea.graph.model.BaseNodeModel;
import com.alibaba.compileflow.idea.graph.model.DecisionNodeModel;
import com.alibaba.compileflow.idea.graph.model.EndNodeModel;
import com.alibaba.compileflow.idea.graph.model.ScriptTaskNodeModel;
import com.alibaba.compileflow.idea.graph.model.StartNodeModel;

/**
 * @author xuan
 * @since 2020/2/5
 */
public class BpmnModelDecoder {

    public static BpmnModel toBpmnModel(String xml) {
        Node root = XmlUtil.xml2Node(xml);
        Node processNode = root.getChildList().get(0);

        BpmnModel bpmnModel = BpmnModel.of();

        //bpm
        bpmnModel.setCode(processNode.getAttr("code"));
        bpmnModel.setName(processNode.getAttr("name"));
        bpmnModel.setType(processNode.getAttr("type"));
        bpmnModel.setDescription(processNode.getAttr("description"));
        bpmnModel.setIsExecutable(processNode.getAttr("isExecutable"));

        //transition, node, var
        List<Node> childList = processNode.getChildList();
        if (null == childList) {
            return bpmnModel;
        }

        List<VarModel> varModelList = new ArrayList<>();
        List<Node> transitionNodeList = new ArrayList<>();
        List<BaseNodeModel> nodeList = new ArrayList<>();
        for (Node child : childList) {
            //global vars
            if ("extensionElements".equals(child.getType())) {
                List<Node> varNodeList = child.getChildList();
                if (null != varNodeList) {
                    for (Node varNode : varNodeList) {
                        VarModel varModel = VarModel.of();
                        CodecUtil.node2VarModel(varNode, varModel);
                        varModelList.add(varModel);
                    }
                }
            }
            //transition
            else if ("sequenceFlow".equals(child.getType())) {
                transitionNodeList.add(child);
            }
            // node
            else {
                if ("startEvent".equals(child.getType())) {
                    StartNodeModel startNode = StartNodeModel.of();
                    CodecUtil.nodeToModel(child, startNode);
                    nodeList.add(startNode);
                } else if ("endEvent".equals(child.getType())) {
                    EndNodeModel endNode = EndNodeModel.of();
                    CodecUtil.nodeToModel(child, endNode);
                    nodeList.add(endNode);
                } else if ("serviceTask".equals(child.getType())) {
                    AutoTaskNodeModel autoTaskNode = AutoTaskNodeModel.of();
                    CodecUtil.nodeToModel(child, autoTaskNode);
                    nodeList.add(autoTaskNode);
                } else if ("scriptTask".equals(child.getType())) {
                    ScriptTaskNodeModel scriptTaskNode = ScriptTaskNodeModel.of();
                    CodecUtil.nodeToModel(child, scriptTaskNode);
                    nodeList.add(scriptTaskNode);
                } else if ("exclusiveGateway".equals(child.getType())) {
                    DecisionNodeModel decisionNode = DecisionNodeModel.of();
                    CodecUtil.nodeToModel(child, decisionNode);
                    nodeList.add(decisionNode);
                }
            }
        }
        bpmnModel.setVars(varModelList);
        bpmnModel.setAllNodes(nodeList);
        fillTransitionToNode(nodeList, transitionNodeList);
        return bpmnModel;
    }

    private static void fillTransitionToNode(List<BaseNodeModel> nodeList, List<Node> transitionNodeList) {
        if (null == nodeList || null == transitionNodeList) {
            return;
        }

        // key:fromId, value=transitionNodeList
        Map<String, List<Node>> fromId2NodeMap = new HashMap<>();
        for (Node node : transitionNodeList) {
            //from
            List<Node> fromList = fromId2NodeMap.get(node.getAttr("sourceRef"));
            if (null == fromList) {
                fromList = new ArrayList<>();
                fromId2NodeMap.put(node.getAttr("sourceRef"), fromList);
            }
            fromList.add(node);
        }

        //fill and convert transitionNode to node
        for (BaseNodeModel nodeModel : nodeList) {
            List<Node> fromList = fromId2NodeMap.get(nodeModel.getId());
            if (null == fromList) {
                continue;
            }
            List<TransitionModel> transitionList = new ArrayList<>();
            for (Node n : fromList) {
                TransitionModel transition = TransitionModel.of();
                transition.setTo(n.getAttr("targetRef"));
                transition.setName(n.getAttr("id"));
                transition.setPriority("1");
                Node conditionExpressionNode = n.getFistChild("conditionExpression");
                if (null != conditionExpressionNode) {
                    transition.setExpression(conditionExpressionNode.getAttr("value"));
                }
                transitionList.add(transition);
            }
            nodeModel.setOutTransitions(transitionList);
        }
    }

}
