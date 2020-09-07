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
import java.util.List;

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
public class BpmnModelEncoder {

    public static String toXml(BpmnModel bpmnModel) {
        return XmlUtil.node2Xml(toXmlNode(bpmnModel));
    }

    private static Node toXmlNode(BpmnModel bpmnModel) {

        Node definitions = new Node("definitions");
        definitions.addAttr("xmlns:nature", "http://natureflow.alibaba-inc.com"); // FIXME later 
        definitions.addAttr("xmlns", "http://www.omg.org/spec/BPMN/20100524/MODEL");
        definitions.addAttr("typeLanguage", "http://www.w3.org/2001/XMLSchema");
        definitions.addAttr("expressionLanguage", "http://www.w3.org/1999/XPath");
        definitions.addAttr("targetNamespace", "http://natureflow.alibaba-inc.com");

        Node process = Node.buildNode("process", definitions);
        process.addAttr("code", bpmnModel.getCode());
        process.addAttr("name", bpmnModel.getName());
        process.addAttr("type", bpmnModel.getType());
        process.addAttr("description", bpmnModel.getDescription());
        process.addAttr("isExecutable", bpmnModel.getIsExecutable());

        Node extensionElements = Node.buildNode("extensionElements", process);
        //global vars
        List<VarModel> varList = bpmnModel.getVars();
        if (null != varList) {
            for (VarModel var : varList) {
                Node natureVar = Node.buildNode("nature:var", extensionElements);
                CodecUtil.varModel2Node(var, natureVar);
            }
        }
        //node
        List<BaseNodeModel> nodeList = bpmnModel.getAllNodes();
        if (null != nodeList) {
            for (BaseNodeModel nodeModel : nodeList) {

                Node node = null;
                if (nodeModel instanceof StartNodeModel) {
                    node = new Node("startEvent");
                    CodecUtil.model2Node(nodeModel, node);
                } else if (nodeModel instanceof EndNodeModel) {
                    node = new Node("endEvent");
                    CodecUtil.model2Node(nodeModel, node);
                } else if (nodeModel instanceof AutoTaskNodeModel) {
                    node = new Node("serviceTask");
                    CodecUtil.model2Node(nodeModel, node);
                    BpmnServiceTaskNodeCodec.data2Node((AutoTaskNodeModel)nodeModel, node);
                } else if (nodeModel instanceof ScriptTaskNodeModel) {
                    node = new Node("scriptTask");
                    CodecUtil.model2Node(nodeModel, node);
                    BpmnScriptTaskNodeCodec.data2Node((ScriptTaskNodeModel)nodeModel, node);
                } else if (nodeModel instanceof DecisionNodeModel) {
                    node = new Node("exclusiveGateway");
                    CodecUtil.model2Node(nodeModel, node);
                    BpmnDecisionNodeCodec.data2Node((DecisionNodeModel)nodeModel, node);
                }

                if (null != node) {
                    process.addChild(node);
                }
            }
        }

        //transition
        List<TransitionWraper> transitionList = getTransitionList(bpmnModel);
        if (null != transitionList) {
            for (TransitionWraper transitionWraper : transitionList) {
                Node sequenceFlow = Node.buildNode("sequenceFlow", process);
                sequenceFlow.addAttr("id", transitionWraper.getTransition().getName());
                sequenceFlow.addAttr("sourceRef", transitionWraper.getFrom());
                sequenceFlow.addAttr("targetRef", transitionWraper.getTransition().getTo());
                //
                Node conditionExpression = Node.buildNode("conditionExpression", sequenceFlow);
                conditionExpression.addAttr("nature:type", "java");
                conditionExpression.addAttr("value", transitionWraper.getTransition().getExpression());
            }
        }

        return definitions;
    }

    private static List<TransitionWraper> getTransitionList(BpmnModel bpmnModel) {
        List<BaseNodeModel> nodeList = bpmnModel.getAllNodes();
        if (null == nodeList) {
            return null;
        }

        List<TransitionWraper> transitionList = new ArrayList<>();
        for (BaseNodeModel nodeModel : nodeList) {
            List<TransitionModel> tList = nodeModel.getOutTransitions();
            if (null != tList) {
                for (TransitionModel t : tList) {
                    TransitionWraper transitionWraper = new TransitionWraper();
                    transitionWraper.setFrom(nodeModel.getId());
                    transitionWraper.setTransition(t);
                    transitionList.add(transitionWraper);
                }
            }
        }

        return transitionList;
    }

    public static class TransitionWraper {
        private TransitionModel transition;
        private String from;

        public TransitionModel getTransition() {
            return transition;
        }

        public void setTransition(TransitionModel transition) {
            this.transition = transition;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }
    }

}
