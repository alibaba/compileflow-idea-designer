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

import com.alibaba.compileflow.idea.graph.nodeview.component.ActionPanel;
import com.alibaba.compileflow.idea.graph.mxgraph.Graph;
import com.alibaba.compileflow.idea.graph.model.ActionModel;

import com.intellij.openapi.project.Project;
import com.mxgraph.model.mxCell;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * AutoTask and Decision Node edit dialog
 *
 * @author xuan
 * @since 2019/3/17
 */
public class AutoTaskDialog extends BaseDialog {

    public AutoTaskDialog(@Nullable Project project, mxCell cell, Graph graph) {
        super(project, cell, graph);
    }

    @Override
    protected String getDialogTitle() {
        return "AutoTask Setting";
    }

    @Override
    protected JPanel getParamPanel(Project project, Graph graph, mxCell cell) {
        return new ActionPanel(project);
    }

    @Override
    protected void initParamPanelView() {
        ActionPanel.initContextVarNameComboBox(paramPanel, graph);
        ((ActionPanel)paramPanel).setJumpToSourceActionCallback((s) -> {
            doCancelAction();
            return null;
        });
    }

    @Override
    protected void initParamPanelData() {
        ActionPanel.data2View(paramPanel, ActionModel.getActionFromCellValue(cell.getValue()));
    }

    @Override
    protected void doParamSave() {
        ActionModel actionModel = ActionModel.getActionFromCellValue(cell.getValue());
        if (null == actionModel) {
            actionModel = ActionModel.of();
        }

        ActionModel.setActionToCellValue(cell.getValue(), actionModel);
        ActionPanel.view2Data(paramPanel, actionModel);
    }

}
