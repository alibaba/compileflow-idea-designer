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
import com.alibaba.compileflow.idea.graph.model.WaitTaskNodeModel;

import com.intellij.openapi.project.Project;
import com.mxgraph.model.mxCell;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * WaitTask Node edit dialog
 *
 * @author xuan
 * @since 2019/3/21
 */
public class WaitTaskDialog extends BaseMultiTabDialog {

    public WaitTaskDialog(@Nullable Project project, mxCell cell, Graph graph) {
        super(project, cell, graph);
    }

    @Override
    protected JPanel[] getPanels() {
        return new JPanel[] {
            new ActionPanel(project), new ActionPanel(project)};
    }

    @Override
    protected String[] getTabNames() {
        return new String[] {"inAction setting", "outAction setting"};
    }

    @Override
    protected String getDialogTitle() {
        return "WaitTask Setting";
    }

    @Override
    protected void initParamPanelView() {
        ActionPanel.initContextVarNameComboBox(panels[0], graph);
        ActionPanel.initContextVarNameComboBox(panels[1], graph);

        ((ActionPanel)panels[0]).setJumpToSourceActionCallback((s) -> {
            doCancelAction();
            return null;
        });
        ((ActionPanel)panels[1]).setJumpToSourceActionCallback((s) -> {
            doCancelAction();
            return null;
        });
    }

    @Override
    protected void initParamPanelData() {
        WaitTaskNodeModel waitModel = WaitTaskNodeModel.getFromCellValue(cell.getValue());
        ActionPanel.data2View(panels[0], waitModel.getInAction());
        ActionPanel.data2View(panels[1], waitModel.getOutAction());
    }

    @Override
    protected void doParamSave() {
        WaitTaskNodeModel waitModel = WaitTaskNodeModel.getFromCellValue(cell.getValue());

        //inAction
        ActionModel inAction = waitModel.getInAction();
        if (null == inAction) {
            inAction = ActionModel.of();
            waitModel.setInAction(inAction);
        }
        ActionPanel.view2Data(panels[0], inAction);
        waitModel.setInAction(ActionPanel.isActionSettingPanelEmpty(panels[0]) ? null : inAction);

        //outAction
        ActionModel outAction = waitModel.getOutAction();
        if (null == outAction) {
            outAction = ActionModel.of();
            waitModel.setOutAction(outAction);
        }
        ActionPanel.view2Data(panels[1], outAction);
        waitModel.setOutAction(ActionPanel.isActionSettingPanelEmpty(panels[1]) ? null : outAction);
    }

}
