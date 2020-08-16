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
package com.alibaba.compileflow.idea.graph.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.alibaba.compileflow.idea.graph.mxgraph.GraphModel;
import com.alibaba.compileflow.idea.graph.util.StringUtil;

/**
 * @author xuan
 * @since 2020/5/11
 */
public class BpmModel {

    public static String BPM_DEFINE_PROCESS = "process";
    public static String BPM_DEFINE_STATEMACHINE = "stateMachine";
    public static String BPM_DEFINE_WORKFLOW = "workflow";
    public static String BPM_DEFINE_STATELESS_WORKFLOW = "statelessWorkflow";

    private String code;
    private String name;
    private String type;
    private String description;
    private List<VarModel> vars;
    private List<BaseNodeModel> allNodes;

    protected BpmModel() {

    }

    public static BpmModel of() {
        return new BpmModel();
    }

    public static BpmModel getFromGraphModel(GraphModel graphModel) {
        return graphModel.getBpmModel();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<VarModel> getVars() {
        return vars;
    }

    public void setVars(List<VarModel> vars) {
        this.vars = vars;
    }

    public List<BaseNodeModel> getAllNodes() {
        return allNodes;
    }

    public void setAllNodes(List<BaseNodeModel> allNodes) {
        this.allNodes = allNodes;
    }

    ////////////////////////////// ability  //////////////////////////////

    public Set<String> getContextVarNameSet() {
        if (null == vars) {
            return new HashSet<>();
        }
        Set<String> itemSet = new HashSet<>();
        for (VarModel var : vars) {
            itemSet.add(var.getName());
        }
        return itemSet;
    }

    public void fixTransitionTo(String oldId, String newId) {
        if (StringUtil.isEquals(oldId, newId) || null == allNodes) {
            return;
        }

        allNodes.forEach((node) -> {
            List<TransitionModel> transitionList = node.getOutTransitions();
            if (null != transitionList) {
                transitionList.forEach((transition) -> {
                    if (StringUtil.isEquals(transition.getTo(), oldId)) {
                        transition.setTo(newId);
                    }
                });
            }
        });
    }

    public BaseNodeModel getNode(String nodeId) {
        if (null == allNodes || null == nodeId) {
            return null;
        }
        for (BaseNodeModel subModel : allNodes) {
            if (nodeId.equals(subModel.getId())) {
                return subModel;
            }
        }
        return null;
    }

}
