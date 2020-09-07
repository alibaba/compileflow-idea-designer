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

import com.alibaba.compileflow.idea.graph.util.xml.Node;
import com.alibaba.compileflow.idea.graph.model.VarModel;
import com.alibaba.compileflow.idea.graph.model.BaseNodeModel;

/**
 * @author xuan
 * @since 2020/5/23
 */
public class CodecUtil {
    public static void node2VarModel(Node node, VarModel varModel) {
        varModel.setName(node.getAttr("name"));
        varModel.setDescription(node.getAttr("description"));
        varModel.setDataType(node.getAttr("dataType"));
        varModel.setContextVarName(node.getAttr("contextVarName"));
        varModel.setDefaultValue(node.getAttr("defaultValue"));
        varModel.setInOutType(node.getAttr("inOutType"));
    }

    public static void varModel2Node(VarModel varModel, Node node) {
        node.addAttr("name", varModel.getName());
        node.addAttr("description", varModel.getDescription());
        node.addAttr("dataType", varModel.getDataType());
        node.addAttr("contextVarName", varModel.getContextVarName());
        node.addAttr("defaultValue", varModel.getDefaultValue());
        node.addAttr("inOutType", varModel.getInOutType());
    }

    public static void nodeToModel(Node node, BaseNodeModel model) {
        model.setName(node.getAttr("name"));
        model.setId(node.getAttr("id"));
        model.setG(node.getAttr("g"));
        model.setTag(node.getAttr("tag"));
    }

    public static void model2Node(BaseNodeModel model, Node node) {
        node.addAttr("id", model.getId());
        node.addAttr("tag", model.getTag());
        node.addAttr("name", model.getName());
        node.addAttr("g", model.getG());
    }

}
