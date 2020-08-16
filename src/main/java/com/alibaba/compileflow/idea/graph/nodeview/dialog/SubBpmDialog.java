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

import com.alibaba.compileflow.idea.graph.nodeview.component.GlobalParamsPanel;
import com.alibaba.compileflow.idea.graph.nodeview.component.SubBpmCodeParamsPanel;
import com.alibaba.compileflow.idea.graph.nodeview.component.TableWithAddBtnPanel;
import com.alibaba.compileflow.idea.graph.mxgraph.Graph;
import com.alibaba.compileflow.idea.graph.model.BpmModel;
import com.alibaba.compileflow.idea.graph.model.VarModel;
import com.alibaba.compileflow.idea.graph.model.SubBpmNodeModel;

import com.intellij.openapi.project.Project;
import com.mxgraph.model.mxCell;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

/**
 * SubBpm Node edit dialog
 *
 * @author xuan
 * @since 2019/3/18
 */
public class SubBpmDialog extends BaseDialog {

    public SubBpmDialog(@Nullable Project project, mxCell cell, Graph graph) {
        super(project, cell, graph);
    }

    @Override
    protected String getDialogTitle() {
        return "SubBpm Setting";
    }

    @Override
    protected JPanel getParamPanel(Project project, Graph graph, mxCell cell) {
        return new SubBpmCodeParamsPanel(project);
    }

    @Override
    protected void initParamPanelView() {
        // init contextVarNames for select
        Set<String> nameSet = getContextVarNameSet();
        ((SubBpmCodeParamsPanel)paramPanel).getTableWithAddBtnPanel().initContextVarNameToComboBox(nameSet);
    }

    @Override
    protected void initParamPanelData() {
        //Display data to view
        SubBpmCodeParamsPanel panel = (SubBpmCodeParamsPanel)paramPanel;

        SubBpmNodeModel subBpmModel = SubBpmNodeModel.getFromCellValue(cell.getValue());

        panel.getSubBpmCodeLabelField().setText(subBpmModel.getSubBpmCode());

        if (null == subBpmModel.getType() || subBpmModel.getType().trim().length() == 0) {
            panel.getTypeComboBox().setSelectedItem(BpmModel.BPM_DEFINE_PROCESS);
        } else {
            panel.getTypeComboBox().setSelectedItem(subBpmModel.getType());
        }
        panel.getWaitForCompletionComboBox().setSelectedItem(String.valueOf(subBpmModel.isWaitForCompletion()));

        List<VarModel> varList = subBpmModel.getVars();
        List<Vector<String>> vectorList = VarModel.toVectorList(varList);

        for (Vector<String> vector : vectorList) {
            panel.getTableWithAddBtnPanel().getTableModel().addRow(vector);
        }

    }

    @Override
    protected void doParamSave() {
        //Collect data from view
        SubBpmCodeParamsPanel panel = (SubBpmCodeParamsPanel)paramPanel;
        //SubBpmNode node = (SubBpmNode)cell.getValue();

        SubBpmNodeModel subBpmModel = SubBpmNodeModel.getFromCellValue(cell.getValue());

        //subBpmCode
        subBpmModel.setSubBpmCode(panel.getSubBpmCodeLabelField().getText());

        //type
        Object typeObj = panel.getTypeComboBox().getSelectedItem();
        subBpmModel.setType(null == typeObj ? BpmModel.BPM_DEFINE_PROCESS : typeObj.toString());

        //waitForCompletion
        Object waitForCompletionObj = panel.getWaitForCompletionComboBox().getSelectedItem();
        subBpmModel.setWaitForCompletion(
            null != waitForCompletionObj && "true".equalsIgnoreCase(waitForCompletionObj.toString()));

        // vars
        List<VarModel> varList = new ArrayList<>();
        List<TableWithAddBtnPanel.Data> dataList = panel.getTableWithAddBtnPanel().getDataList();
        for (TableWithAddBtnPanel.Data data : dataList) {
            if (StringUtils.isBlank(data.name) || StringUtils.isBlank(data.dataType)) {
                continue;
            }
            VarModel var = toVarModel(data);
            varList.add(var);
        }
        subBpmModel.setVars(varList);
    }

    private VarModel toVarModel(GlobalParamsPanel.Data d) {
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
