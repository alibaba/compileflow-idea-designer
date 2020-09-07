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
import com.alibaba.compileflow.idea.graph.model.ActionHandleModel;
import com.alibaba.compileflow.idea.graph.model.ActionModel;
import com.alibaba.compileflow.idea.graph.model.JavaActionHandleModel;
import com.alibaba.compileflow.idea.graph.model.ScriptActionHandleModel;
import com.alibaba.compileflow.idea.graph.model.SpringBeanActionHandleModel;
import com.alibaba.compileflow.idea.graph.model.VarModel;

/**
 * @author xuan
 * @since 2020/2/5
 */
public class AbstractBpmnActionCodec {

    protected static void action2Node(ActionModel action, Node currentNode) {
        if (null == action) {
            return;
        }

        //type
        currentNode.addAttr("nature:type", action.getType());
        ActionHandleModel actionHandle = action.getActionHandle();
        if (null == actionHandle) {
            return;
        }

        if (actionHandle instanceof SpringBeanActionHandleModel) {
            SpringBeanActionHandleModel model = (SpringBeanActionHandleModel)actionHandle;
            currentNode.addAttr("nature:class", model.getClazz());
            currentNode.addAttr("nature:method", model.getMethod());
            currentNode.addAttr("nature:bean", model.getBean());
        } else if (actionHandle instanceof JavaActionHandleModel) {
            JavaActionHandleModel model = (JavaActionHandleModel)actionHandle;
            currentNode.addAttr("nature:class", model.getClazz());
            currentNode.addAttr("nature:method", model.getMethod());
        } else if (actionHandle instanceof ScriptActionHandleModel) {
            ScriptActionHandleModel model = (ScriptActionHandleModel)actionHandle;
            currentNode.addAttr("scriptFormat", model.getExpression());
        }

        //vars
        List<VarModel> varList = actionHandle.getVars();
        if (null == varList || varList.size() == 0) {
            return;
        }

        Node extensionElements = Node.buildNode("extensionElements", currentNode);
        for (VarModel varModel : varList) {
            Node var = Node.buildNode("nature:var", extensionElements);
            CodecUtil.varModel2Node(varModel, var);
        }
    }

    protected static ActionModel node2Action(Node currentNode) {
        ActionModel action = ActionModel.of();

        String type = currentNode.getAttr("nature:type");
        String bean = currentNode.getAttr("nature:bean");
        String clazz = currentNode.getAttr("nature:class");
        String method = currentNode.getAttr("nature:method");
        action.setType(type);
        if (ActionModel.JAVA_BEAN.equals(type)) {
            JavaActionHandleModel javaActionHandle = JavaActionHandleModel.of();
            javaActionHandle.setMethod(method);
            javaActionHandle.setClazz(clazz);
            action.setActionHandle(javaActionHandle);
        } else if (ActionModel.SPRING_BEAN.equals(type)) {
            SpringBeanActionHandleModel springBeanActionHandle = SpringBeanActionHandleModel.of();
            springBeanActionHandle.setMethod(method);
            springBeanActionHandle.setClazz(clazz);
            springBeanActionHandle.setBean(bean);
            action.setActionHandle(springBeanActionHandle);
        } else {
            return action;
        }

        //vars
        Node extensionElements = currentNode.getFistChild("extensionElements");
        if (null == extensionElements) {
            return action;
        }
        List<Node> varList = extensionElements.getChildList("nature:var");
        if (null == varList) {
            return action;
        }

        List<VarModel> varModelList = new ArrayList<>();
        for (Node var : varList) {
            VarModel varModel = VarModel.of();
            CodecUtil.node2VarModel(var, varModel);
            varModelList.add(varModel);
        }
        ActionHandleModel actionHandle = action.getActionHandle();
        actionHandle.setVars(varModelList);
        return action;
    }

}
