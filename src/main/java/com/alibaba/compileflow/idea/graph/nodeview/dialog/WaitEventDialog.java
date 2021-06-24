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

import com.alibaba.compileflow.idea.graph.model.WaitEventModel;
import com.alibaba.compileflow.idea.graph.mxgraph.Graph;
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
public class WaitEventDialog extends BaseDialog {

    public WaitEventDialog(@Nullable Project project, mxCell cell, Graph graph) {
        super(project, cell, graph);
    }

    @Override
    protected String getDialogTitle() {
        return "WaitEvent setting";
    }

    @Override
    protected JPanel getParamPanel(Project project, Graph graph, mxCell cell) {
        return new WaitEventPanel();
    }

    @Override
    protected void initParamPanelView() {

    }

    @Override
    protected void initParamPanelData() {
        WaitEventPanel waitEventPanel = (WaitEventPanel)paramPanel;

        WaitEventModel waitEventModel = WaitEventModel.getFromCellValue(cell.getValue());

        waitEventPanel.getEventNameField().setText(StringUtil.trimToEmpty(waitEventModel.getEventName()));
    }

    @Override
    protected void doParamSave() {
        WaitEventPanel waitEventPanel = (WaitEventPanel)paramPanel;

        WaitEventModel waitEventModel = WaitEventModel.getFromCellValue(cell.getValue());

        waitEventModel.setEventName(StringUtil.trimToEmpty(waitEventPanel.getEventNameField().getText()));
    }

}
