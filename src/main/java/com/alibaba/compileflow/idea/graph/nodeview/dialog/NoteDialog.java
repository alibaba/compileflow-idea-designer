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

import com.alibaba.compileflow.idea.graph.nodeview.component.NotePanel;
import com.alibaba.compileflow.idea.graph.mxgraph.Graph;
import com.alibaba.compileflow.idea.graph.util.StringUtil;
import com.alibaba.compileflow.idea.graph.model.NoteNodeModel;

import com.intellij.openapi.project.Project;
import com.mxgraph.model.mxCell;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Note Node edit dialog
 *
 * @author xuan
 * @since 2019/3/21
 */
public class NoteDialog extends BaseDialog {

    public NoteDialog(@Nullable Project project, mxCell cell, Graph graph) {
        super(project, cell, graph);
    }

    @Override
    protected String getDialogTitle() {
        return "Note setting";
    }

    @Override
    protected JPanel getParamPanel(Project project, Graph graph, mxCell cell) {
        return new NotePanel();
    }

    @Override
    protected void initParamPanelView() {

    }

    @Override
    protected void initParamPanelData() {
        NotePanel noteSettingPanel = (NotePanel)paramPanel;

        NoteNodeModel noteModel = NoteNodeModel.getFromCellValue(cell.getValue());

        noteSettingPanel.getCommitField().setText(StringUtil.trimToEmpty(noteModel.getComment()));
    }

    @Override
    protected void doParamSave() {
        NotePanel noteSettingPanel = (NotePanel)paramPanel;

        NoteNodeModel noteModel = NoteNodeModel.getFromCellValue(cell.getValue());

        noteModel.setComment(StringUtil.trimToEmpty(noteSettingPanel.getCommitField().getText()));
    }

}
