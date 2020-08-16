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
import com.alibaba.compileflow.idea.graph.nodeview.component.UserTaskBasicParamsPanel;
import com.alibaba.compileflow.idea.graph.nodeview.component.UserTaskTimeoutPanel;
import com.alibaba.compileflow.idea.graph.nodeview.component.UserTaskUsertypePanel;
import com.alibaba.compileflow.idea.graph.mxgraph.Graph;
import com.alibaba.compileflow.idea.graph.util.NumberUtil;
import com.alibaba.compileflow.idea.graph.util.StringUtil;
import com.alibaba.compileflow.idea.graph.model.ActionModel;
import com.alibaba.compileflow.idea.graph.model.UserTaskNodeModel;

import com.intellij.openapi.project.Project;
import com.mxgraph.model.mxCell;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * UserTask Node edit dialog
 *
 * @author xuan
 * @since 2019/3/19
 */
public class UserTaskDialog extends BaseMultiTabDialog {

    public UserTaskDialog(@Nullable Project project, mxCell cell, Graph graph) {
        super(project, cell, graph);
    }

    @Override
    protected JPanel[] getPanels() {
        return new JPanel[] {
            new UserTaskBasicParamsPanel(),
            new ActionPanel(project),
            new ActionPanel(project),
            new UserTaskUsertypePanel(project),
            new UserTaskTimeoutPanel()};
    }

    @Override
    protected String[] getTabNames() {
        return new String[] {
            "retry setting",
            "inAction setting",
            "outAction setting",
            "user setting",
            "duetime setting"};
    }

    @Override
    protected String getDialogTitle() {
        return "UserTask Setting";
    }

    @Override
    protected void initParamPanelView() {
        ActionPanel.initContextVarNameComboBox(panels[1], graph);
        ActionPanel.initContextVarNameComboBox(panels[2], graph);

        ((ActionPanel)panels[1]).setJumpToSourceActionCallback((s) -> {
            doCancelAction();
            return null;
        });
        ((ActionPanel)panels[2]).setJumpToSourceActionCallback((s) -> {
            doCancelAction();
            return null;
        });
    }

    @Override
    protected void initParamPanelData() {
        UserTaskNodeModel userTaskModel = UserTaskNodeModel.getFromCellValue(cell.getValue());

        //tab1
        UserTaskBasicParamsPanel retryParamsSettingPanel = (UserTaskBasicParamsPanel)panels[0];
        retryParamsSettingPanel.getRetryMaxField().setText(NumberUtil.long2Str(userTaskModel.getRetryMax()));
        retryParamsSettingPanel.getRetryInterValField().setText(NumberUtil.long2Str(userTaskModel.getRetryInterVal()));
        retryParamsSettingPanel.getSignPercentField().setText(userTaskModel.getSignPercent());
        retryParamsSettingPanel.getSignTypeField().setText(userTaskModel.getSignType());
        retryParamsSettingPanel.getFormKeyField().setText(userTaskModel.getFormKey());
        retryParamsSettingPanel.getPriorityField().setText(String.valueOf(userTaskModel.getPriority()));
        //tab2
        ActionPanel.data2View(panels[1], userTaskModel.getInAction());
        //tab3
        ActionPanel.data2View(panels[2], userTaskModel.getOutAction());
        //tab4
        UserTaskUsertypePanel.data2View(panels[3], userTaskModel);
        //tab5
        UserTaskTimeoutPanel.data2View(panels[4], userTaskModel);
    }

    @Override
    protected void doParamSave() {
        UserTaskNodeModel userTaskModel = UserTaskNodeModel.getFromCellValue(cell.getValue());

        //tab1
        UserTaskBasicParamsPanel retryParamsSettingPanel = (UserTaskBasicParamsPanel)panels[0];
        userTaskModel.setRetryMax(NumberUtil.str2Long(retryParamsSettingPanel.getRetryMaxField().getText()));
        userTaskModel.setRetryInterVal(NumberUtil.str2Long(retryParamsSettingPanel.getRetryInterValField().getText()));
        userTaskModel.setSignPercent(StringUtil.emptyToNull(retryParamsSettingPanel.getSignPercentField().getText()));
        userTaskModel.setSignType(StringUtil.emptyToNull(retryParamsSettingPanel.getSignTypeField().getText()));
        userTaskModel.setFormKey(StringUtil.emptyToNull(retryParamsSettingPanel.getFormKeyField().getText()));
        userTaskModel.setPriority(NumberUtil.str2Integer(retryParamsSettingPanel.getPriorityField().getText()));

        //tab2
        ActionModel inAction = userTaskModel.getInAction();
        if (null == inAction) {
            inAction = ActionModel.of();
            userTaskModel.setInAction(inAction);
        }
        ActionPanel.view2Data(panels[1], inAction);
        //tab3
        ActionModel outAction = userTaskModel.getOutAction();
        if (null == outAction) {
            outAction = ActionModel.of();
            userTaskModel.setOutAction(outAction);
        }
        userTaskModel.setOutAction(outAction);
        ActionPanel.view2Data(panels[2], outAction);
        //tab4
        UserTaskUsertypePanel.view2Data(panels[3], userTaskModel);
        //tab5
        UserTaskTimeoutPanel.view2Data(panels[4], userTaskModel);
    }

}
