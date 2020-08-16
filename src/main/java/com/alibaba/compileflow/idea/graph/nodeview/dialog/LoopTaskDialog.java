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
package com.alibaba.compileflow.idea.graph.nodeview.dialog;

import com.alibaba.compileflow.idea.graph.nodeview.component.LoopTaskParamsPanel;
import com.alibaba.compileflow.idea.graph.mxgraph.Graph;
import com.alibaba.compileflow.idea.graph.model.BpmModel;
import com.alibaba.compileflow.idea.graph.model.LoopProcessNodeModel;

import com.intellij.openapi.project.Project;
import com.mxgraph.model.mxCell;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * LoopTask Node edit dialog
 *
 * @author xuan
 * @since 2019/3/16
 */
public class LoopTaskDialog extends BaseDialog {

    public LoopTaskDialog(@Nullable Project project, mxCell cell, Graph graph) {
        super(project, cell, graph);
    }

    @Override
    protected String getDialogTitle() {
        return "LoopTask Setting";
    }

    @Override
    protected JPanel getParamPanel(Project project, Graph graph, mxCell cell) {

        BpmModel bpmModel = BpmModel.getFromGraphModel(graph.getModel());
        LoopProcessNodeModel loopModel = LoopProcessNodeModel.getFromCellValue(cell.getValue());

        return new LoopTaskParamsPanel(project, bpmModel.getContextVarNameSet());
    }

    @Override
    protected void initParamPanelView() {

    }

    @Override
    protected void initParamPanelData() {
        //Display data to view
        LoopTaskParamsPanel panel = (LoopTaskParamsPanel)paramPanel;
        LoopProcessNodeModel loopModel = LoopProcessNodeModel.getFromCellValue(cell.getValue());

        panel.getCollectionVarNameField().setText(loopModel.getCollectionVarName());
        panel.getVariableNameField().setText(loopModel.getVariableName());
        panel.getIndexVarNameField().setText(loopModel.getIndexVarName());
        panel.getVariableClassField().setText(loopModel.getVariableClass());
        panel.getStartNodeIdField().setText(loopModel.getStartNodeId());
        panel.getEndNodeIdField().setText(loopModel.getEndNodeId());
    }

    @Override
    protected void doParamSave() {
        //Collect data from view
        LoopTaskParamsPanel panel = (LoopTaskParamsPanel)paramPanel;
        LoopProcessNodeModel loopModel = LoopProcessNodeModel.getFromCellValue(cell.getValue());

        loopModel.setCollectionVarName(panel.getCollectionVarNameField().getText());
        loopModel.setVariableName(panel.getVariableNameField().getText());
        loopModel.setIndexVarName(panel.getIndexVarNameField().getText());
        loopModel.setVariableClass(panel.getVariableClassField().getText());
        loopModel.setStartNodeId(panel.getStartNodeIdField().getText());
        loopModel.setEndNodeId(panel.getEndNodeIdField().getText());
    }

}
