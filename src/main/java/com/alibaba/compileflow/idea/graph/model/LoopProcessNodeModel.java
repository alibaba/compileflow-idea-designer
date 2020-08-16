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

import java.util.List;

/**
 * @author xuan
 * @since 2020/5/17
 */
public class LoopProcessNodeModel extends BaseNodeModel {

    private String collectionVarName;
    private String variableName;
    private String indexVarName;
    private String variableClass;
    private String startNodeId;
    private String endNodeId;
    private List<BaseNodeModel> allNodes;

    private LoopProcessNodeModel() {
    }

    public static LoopProcessNodeModel of() {
        return new LoopProcessNodeModel();
    }

    public static LoopProcessNodeModel getFromCellValue(Object cellValue) {
        return (LoopProcessNodeModel)cellValue;
    }

    ////////////////////////////// get set //////////////////////////////

    public String getCollectionVarName() {
        return collectionVarName;
    }

    public void setCollectionVarName(String collectionVarName) {
        this.collectionVarName = collectionVarName;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public String getIndexVarName() {
        return indexVarName;
    }

    public void setIndexVarName(String indexVarName) {
        this.indexVarName = indexVarName;
    }

    public String getVariableClass() {
        return variableClass;
    }

    public void setVariableClass(String variableClass) {
        this.variableClass = variableClass;
    }

    public String getStartNodeId() {
        return startNodeId;
    }

    public void setStartNodeId(String startNodeId) {
        this.startNodeId = startNodeId;
    }

    public String getEndNodeId() {
        return endNodeId;
    }

    public void setEndNodeId(String endNodeId) {
        this.endNodeId = endNodeId;
    }

    public List<BaseNodeModel> getAllNodes() {
        return allNodes;
    }

    public void setAllNodes(List<BaseNodeModel> allNodes) {
        this.allNodes = allNodes;
    }

    ////////////////////////////// ability //////////////////////////////

    public String[] getSubNodeIds() {
        String[] nodeIds = null;
        if (null != allNodes && allNodes.size() > 0) {
            nodeIds = new String[allNodes.size()];
            for (int i = 0, n = allNodes.size(); i < n; i++) {
                nodeIds[i] = allNodes.get(i).getId();
            }
        }
        return nodeIds;
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
