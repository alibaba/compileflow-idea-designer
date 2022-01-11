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

import javax.swing.*;

import com.alibaba.compileflow.idea.graph.model.ActionModel;
import com.alibaba.compileflow.idea.graph.model.WaitEventModel;
import com.alibaba.compileflow.idea.graph.mxgraph.Graph;
import com.alibaba.compileflow.idea.graph.nodeview.component.ActionPanel;
import com.alibaba.compileflow.idea.graph.nodeview.component.WaitEventPanel;
import com.alibaba.compileflow.idea.graph.util.StringUtil;

import com.intellij.openapi.project.Project;
import com.mxgraph.model.mxCell;
import org.jetbrains.annotations.Nullable;

/**
 * WaitEvent edit dialog
 *
 * @author xuan
 * @since 2019/3/21
 */
public class WaitEventDialog extends BaseMultiTabDialog {

    public WaitEventDialog(@Nullable Project project, mxCell cell, Graph graph) {
        super(project, cell, graph);
    }

    @Override
    protected String getDialogTitle() {
        return "WaitEvent Setting";
    }

    @Override
    protected void initParamPanelView() {
        ActionPanel.initContextVarNameComboBox(panels[1], graph);
        ((ActionPanel)panels[1]).setJumpToSourceActionCallback((s) -> {
            doCancelAction();
            return null;
        });
    }

    @Override
    protected void initParamPanelData() {

        WaitEventModel waitEventModel = WaitEventModel.getFromCellValue(cell.getValue());

        //EventName
        WaitEventPanel waitEventPanel = (WaitEventPanel)panels[0];
        waitEventPanel.getEventNameField().setText(StringUtil.trimToEmpty(waitEventModel.getEventName()));

        //inAction
        ActionPanel.data2View(panels[1], waitEventModel.getInAction());
    }

    @Override
    protected void doParamSave() {
        WaitEventModel waitEventModel = WaitEventModel.getFromCellValue(cell.getValue());

        //EventName
        WaitEventPanel waitEventPanel = (WaitEventPanel)panels[0];
        waitEventModel.setEventName(StringUtil.trimToEmpty(waitEventPanel.getEventNameField().getText()));

        //inAction
        ActionModel inAction = waitEventModel.getInAction();
        if (null == inAction) {
            inAction = ActionModel.of();
            waitEventModel.setInAction(inAction);
        }
        ActionPanel.view2Data(panels[1], inAction);
        waitEventModel.setInAction(ActionPanel.isActionSettingPanelEmpty(panels[1]) ? null : inAction);
    }

    @Override
    protected JPanel[] getPanels() {
        return new JPanel[] {
            new WaitEventPanel(), new ActionPanel(project)};
    }

    @Override
    protected String[] getTabNames() {
        return new String[] {"EventName Setting", "PreAction Setting"};
    }

}
