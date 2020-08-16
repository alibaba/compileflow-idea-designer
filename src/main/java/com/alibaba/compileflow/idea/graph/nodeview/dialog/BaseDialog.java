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

import com.alibaba.compileflow.idea.graph.nodeview.component.NodeBasicPanel;
import com.alibaba.compileflow.idea.graph.mxgraph.Graph;
import com.alibaba.compileflow.idea.graph.model.BpmModel;
import com.alibaba.compileflow.idea.graph.model.BaseNodeModel;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.components.JBTabbedPane;
import com.mxgraph.model.mxCell;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxUndoableEdit;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

import java.util.Set;

/**
 * Base node edit dialog
 *
 * @author xuan
 * @since 2019/3/10
 */
public abstract class BaseDialog extends DialogWrapper {

    protected mxCell cell;
    protected Graph graph;
    protected Project project;

    protected JBTabbedPane rootTab = new JBTabbedPane();
    protected NodeBasicPanel basicPanel = new NodeBasicPanel();
    protected JPanel paramPanel;

    public BaseDialog(@Nullable Project project, mxCell cell, Graph graph) {
        super(project);
        this.cell = cell;
        this.graph = graph;
        this.paramPanel = getParamPanel(project, graph, cell);
        this.project = project;
        setTitle(getDialogTitle());
        setModal(true);
        init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        initView();
        initData();
        return rootTab;
    }

    @Override
    public void doOKAction() {
        super.doOKAction();

        BaseNodeModel baseNodeModel = BaseNodeModel.getBaseNodeFromCellValue(cell.getValue());
        String oldId = baseNodeModel.getId();
        NodeBasicPanel.Data basicData = basicPanel.getData();
        baseNodeModel.setName(basicData.name);
        baseNodeModel.setId(basicData.id);
        baseNodeModel.setTag(basicData.tag);
        baseNodeModel.setG(basicData.g);
        //fix transitionTo if modify
        BpmModel bpmModel = BpmModel.getFromGraphModel(graph.getModel());
        bpmModel.fixTransitionTo(oldId, basicData.id);

        doParamSave();

        // notify change
        graph.refresh();
        mxUndoableEdit edit = new mxUndoableEdit(graph.getModel());
        graph.getModel().fireEvent(new mxEventObject(mxEvent.CHANGE, "edit", edit, "changes", edit.getChanges()));
    }

    protected void initView() {
        rootTab.addTab("basic setting", new JLabel("basic setting"));
        if (null != paramPanel) {
            rootTab.addTab("action setting", new JLabel("action setting"));
        }

        rootTab.setComponentAt(0, basicPanel);
        if (null != paramPanel) {
            rootTab.setComponentAt(1, paramPanel);
        }

        initParamPanelView();
    }

    private void initData() {
        if (null == cell) {
            return;
        }

        BaseNodeModel baseModel = BaseNodeModel.getBaseNodeFromCellValue(cell.getValue());
        NodeBasicPanel.Data basicData = new NodeBasicPanel.Data();
        basicData.name = baseModel.getName();
        basicData.id = baseModel.getId();
        basicData.tag = baseModel.getTag();
        basicData.g = baseModel.getG();
        basicPanel.setData(basicData);

        initParamPanelData();
    }

    /**
     * Get dialog title
     *
     * @return title
     */
    protected abstract String getDialogTitle();

    /**
     * Get param settings panel
     *
     * @param project project
     * @param graph   graph
     * @return jpanel
     */
    protected abstract JPanel getParamPanel(Project project, Graph graph, mxCell cell);

    /**
     * Init view
     */
    protected abstract void initParamPanelView();

    /**
     * Init data to view
     */
    protected abstract void initParamPanelData();

    /**
     * Called after click ok
     */
    protected abstract void doParamSave();

    protected Set<String> getContextVarNameSet() {
        BpmModel bpmModel = BpmModel.getFromGraphModel(graph.getModel());
        return bpmModel.getContextVarNameSet();
    }

}
