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

import com.alibaba.compileflow.idea.graph.nodeview.component.GlobalBasicPanel;
import com.alibaba.compileflow.idea.graph.nodeview.component.GlobalParamsPanel;
import com.alibaba.compileflow.idea.graph.mxgraph.Graph;
import com.alibaba.compileflow.idea.graph.model.BpmModel;
import com.alibaba.compileflow.idea.graph.model.VarModel;
import com.alibaba.compileflow.idea.graph.util.Checker;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.components.JBTabbedPane;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxUndoableEdit;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

import java.util.ArrayList;
import java.util.List;

/**
 * GlobalPropertyDialog
 *
 * @author wuxiang
 */
public class GlobalPropertyDialog extends DialogWrapper {
    private static final String TITLE = "GlobalSetting";

    private GlobalBasicPanel basicPanel = new GlobalBasicPanel();
    private GlobalParamsPanel paramsPanel;
    private JBTabbedPane tabbedPane = new JBTabbedPane();
    private Graph graph;

    public GlobalPropertyDialog(@Nullable Project project, @Nullable Graph graph) {
        super(project);
        this.graph = graph;
        this.paramsPanel = new GlobalParamsPanel(project);
        setTitle(TITLE);
        setModal(true);
        init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        initView();
        initData();
        return tabbedPane;
    }

    private void initView() {
        tabbedPane.addTab("global basic setting", new JLabel("global basic setting"));
        tabbedPane.addTab("global params setting", new JLabel("global params setting"));
        tabbedPane.setComponentAt(0, basicPanel);
        tabbedPane.setComponentAt(1, paramsPanel);
    }

    private void initData() {
        BpmModel bpmModel = BpmModel.getFromGraphModel(graph.getModel());
        if (null == bpmModel) {
            return;
        }
        //基本信息设置
        GlobalBasicPanel.Data data = new GlobalBasicPanel.Data();
        data.code = bpmModel.getCode();
        data.name = bpmModel.getName();
        data.type = bpmModel.getType();
        data.description = bpmModel.getDescription();
        basicPanel.setData(data);
        //参数设置
        List<VarModel> vars = bpmModel.getVars();
        for (int i = 0, n = vars.size(); i < n; i++) {
            VarModel var = vars.get(i);
            GlobalParamsPanel.Data pData = toGlobalParamsPanel(var);
            paramsPanel.insertData(i, pData);
        }
    }

    @Override
    public void doOKAction() {
        //convert vars and check
        List<GlobalParamsPanel.Data> dataList = paramsPanel.getDataList();
        List<VarModel> varList = new ArrayList<>();
        for (GlobalParamsPanel.Data d : dataList) {
            VarModel var = toVarModel(d);
            varList.add(var);
        }
        boolean canSave = Checker.checkVars(varList);
        if (!canSave) {
            return;
        }

        //convert global and check
        BpmModel bpmModel = BpmModel.getFromGraphModel(graph.getModel());
        GlobalBasicPanel.Data data = basicPanel.getData();
        bpmModel.setCode(data.code);
        bpmModel.setType(data.type);
        bpmModel.setDescription(data.description);
        bpmModel.setName(data.name);
        bpmModel.setVars(varList);

        //notify change
        graph.refresh();
        mxUndoableEdit edit = new mxUndoableEdit(graph.getModel());
        graph.getModel().fireEvent(new mxEventObject(mxEvent.CHANGE, "edit", edit, "changes", edit.getChanges()));

        super.doOKAction();
    }

    private static GlobalParamsPanel.Data toGlobalParamsPanel(VarModel varModel) {
        GlobalParamsPanel.Data pData = new GlobalParamsPanel.Data();
        pData.name = varModel.getName();
        pData.dataType = varModel.getDataType();
        pData.defaultValue = varModel.getDefaultValue();
        pData.inOutType = varModel.getInOutType();
        pData.description = varModel.getDescription();
        pData.contextVarName = varModel.getContextVarName();
        return pData;
    }

    private static VarModel toVarModel(GlobalParamsPanel.Data d) {
        VarModel var = VarModel.of();
        var.setName(d.name);
        var.setDataType(d.dataType);
        var.setDefaultValue(d.defaultValue);
        var.setInOutType(d.inOutType);
        var.setDescription(d.description);
        var.setContextVarName(d.contextVarName);
        return var;
    }

}
