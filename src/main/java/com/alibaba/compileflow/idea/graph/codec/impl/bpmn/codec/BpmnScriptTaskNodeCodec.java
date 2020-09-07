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
import com.alibaba.compileflow.idea.graph.util.StringUtil;
import com.alibaba.compileflow.idea.graph.model.ActionHandleModel;
import com.alibaba.compileflow.idea.graph.model.ActionModel;
import com.alibaba.compileflow.idea.graph.model.QlActionHandleModel;
import com.alibaba.compileflow.idea.graph.model.ScriptActionHandleModel;
import com.alibaba.compileflow.idea.graph.model.VarModel;
import com.alibaba.compileflow.idea.graph.model.ScriptTaskNodeModel;

/**
 * @author xuan
 * @since 2020/2/5
 */
public class BpmnScriptTaskNodeCodec {

    public static void data2Node(ScriptTaskNodeModel scriptTaskNode, Node currentNode) {
        if (null == scriptTaskNode || null == scriptTaskNode.getAction()) {
            return;
        }
        action2Node(scriptTaskNode.getAction(), currentNode);
    }

    public static void node2Data(Node currentNode, ScriptTaskNodeModel scriptTaskNode) {
        if (null == scriptTaskNode) {
            return;
        }
        scriptTaskNode.setAction(node2Action(currentNode));
    }

    private static ActionModel node2Action(Node currentNode) {
        ActionModel action = ActionModel.of();
        action.setType(ActionModel.QL);

        QlActionHandleModel qlActionHandle = QlActionHandleModel.of();
        qlActionHandle.setExpression(currentNode.getAttr("scriptFormat"));
        action.setActionHandle(qlActionHandle);

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

    private static void action2Node(ActionModel action, Node currentNode) {
        if (null == action) {
            return;
        }

        //scriptFormat
        currentNode.addAttr("scriptFormat", ActionModel.QL);
        ActionHandleModel actionHandle = action.getActionHandle();
        if (null == actionHandle) {
            return;
        }

        //vars
        List<VarModel> varModelList = actionHandle.getVars();
        if (null == varModelList || varModelList.size() == 0) {
            return;
        }
        Node extensionElements = Node.buildNode("extensionElements", currentNode);
        for (VarModel varModel : varModelList) {
            Node var = Node.buildNode("nature:var", extensionElements);
            CodecUtil.varModel2Node(varModel, var);
        }

        //script
        ScriptActionHandleModel scriptActionHandle = (ScriptActionHandleModel)actionHandle;
        if (!StringUtil.isEmpty(scriptActionHandle.getExpression())) {
            Node script = Node.buildNode("script", currentNode);
            script.setValue(scriptActionHandle.getExpression());
        }
    }

}
