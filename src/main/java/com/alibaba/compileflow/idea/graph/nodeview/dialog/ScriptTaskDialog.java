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
import com.alibaba.compileflow.idea.graph.nodeview.component.ScriptTaskParamsPanel;
import com.alibaba.compileflow.idea.graph.nodeview.component.TableWithAddBtnPanel;
import com.alibaba.compileflow.idea.graph.mxgraph.Graph;
import com.alibaba.compileflow.idea.graph.model.ActionHandleModel;
import com.alibaba.compileflow.idea.graph.model.ActionModel;
import com.alibaba.compileflow.idea.graph.model.QlActionHandleModel;
import com.alibaba.compileflow.idea.graph.model.VarModel;
import com.alibaba.compileflow.idea.graph.model.ScriptTaskNodeModel;

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
 * ScriptTask Node edit dialog
 *
 * @author xuan
 * @since 2019/3/10
 */
public class ScriptTaskDialog extends BaseDialog {

    public ScriptTaskDialog(@Nullable Project project, mxCell cell, Graph graph) {
        super(project, cell, graph);
    }

    @Override
    protected String getDialogTitle() {
        return "ScriptTask Setting";
    }

    @Override
    protected JPanel getParamPanel(Project project, Graph graph, mxCell cell) {
        return new ScriptTaskParamsPanel(project);
    }

    @Override
    protected void initParamPanelView() {
        // init contextVarNames for select
        Set<String> nameSet = getContextVarNameSet();
        ((ScriptTaskParamsPanel)paramPanel).getTableWithAddBtnPanel().initContextVarNameToComboBox(nameSet);
    }

    @Override
    protected void initParamPanelData() {
        //Display data to view
        ScriptTaskParamsPanel panel = (ScriptTaskParamsPanel)paramPanel;

        ScriptTaskNodeModel scriptModel = ScriptTaskNodeModel.getFromCellValue(cell.getValue());

        // type
        ActionModel actionModel = scriptModel.getAction();
        if (null == actionModel) {
            return;
        }
        panel.getTypeComboBox().setSelectedItem(actionModel.getType());

        //expression
        ActionHandleModel actionHandle = actionModel.getActionHandle();
        if (null == actionHandle) {
            return;
        }

        if (actionHandle instanceof QlActionHandleModel) {
            QlActionHandleModel qlActionHandleModel = (QlActionHandleModel)actionHandle;

            panel.getExpressionField().setText(qlActionHandleModel.getExpression());

            //vars
            List<VarModel> varList = qlActionHandleModel.getVars();
            List<Vector<String>> vectorList = VarModel.toVectorList(varList);
            for (Vector<String> vector : vectorList) {
                panel.getTableWithAddBtnPanel().getTableModel().addRow(vector);
            }
        }
    }

    @Override
    protected void doParamSave() {
        //Collect data from view
        ScriptTaskParamsPanel panel = (ScriptTaskParamsPanel)paramPanel;

        ScriptTaskNodeModel scriptModel = ScriptTaskNodeModel.getFromCellValue(cell.getValue());

        // type
        ActionModel action = scriptModel.getAction();
        if (null == action) {
            action = ActionModel.of();
            scriptModel.setAction(action);
        }
        Object typeItem = panel.getTypeComboBox().getSelectedItem();
        action.setType(null == typeItem ? ActionModel.QL : typeItem.toString());

        if (ActionModel.QL.equals(action.getType())) {
            //expression
            QlActionHandleModel qlActionHandleModel = (QlActionHandleModel)action.getActionHandle();
            if (null == qlActionHandleModel) {
                qlActionHandleModel = QlActionHandleModel.of();
                action.setActionHandle(qlActionHandleModel);
            }
            qlActionHandleModel.setExpression(panel.getExpressionField().getText());

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
            qlActionHandleModel.setVars(varList);
        }
    }

    private static VarModel toVarModel(GlobalParamsPanel.Data d) {
        VarModel var = VarModel.of();
        var.setName(d.name);
        var.setDefaultValue(d.defaultValue);
        var.setInOutType(d.inOutType);
        var.setDescription(d.description);
        var.setContextVarName(d.contextVarName);
        var.setDataType(d.dataType);
        return var;
    }

}
