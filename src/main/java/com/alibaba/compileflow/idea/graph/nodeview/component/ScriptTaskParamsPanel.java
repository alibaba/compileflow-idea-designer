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
package com.alibaba.compileflow.idea.graph.nodeview.component;

import com.alibaba.compileflow.idea.graph.model.ActionModel;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

/**
 * ScriptTask params setting view
 *
 * @author xuan
 * @since 2019/3/10
 */
public class ScriptTaskParamsPanel extends JPanel {
    /**
     * Select script type
     */
    private JLabel typeLabel = new JLabel("Type:");
    private ComboBox typeComboBox = new ComboBox(new Object[] {ActionModel.QL}, 100);

    /**
     * Expression
     */
    private JLabel expressionLable = new JLabel("Expression:");
    private JTextField expressionField = new JTextField(30);

    /**
     * Panel for vars
     */
    private TableWithAddBtnPanel tableWithAddBtnPanel;

    public ScriptTaskParamsPanel(Project project) {
        super(new MigLayout("inset 20"));
        tableWithAddBtnPanel = new TableWithAddBtnPanel(project);
        initView();
    }

    private void initView() {
        //type
        this.add(typeLabel, "gap para");
        typeComboBox.setFocusable(true);
        typeComboBox.setEditable(true);
        this.add(typeComboBox, "gap para");

        //expression
        this.add(expressionLable, "gap para");
        this.add(expressionField, "wrap");

        //vars
        this.add(tableWithAddBtnPanel, "span, growx, wrap 10");
    }

    public ComboBox getTypeComboBox() {
        return typeComboBox;
    }

    public JTextField getExpressionField() {
        return expressionField;
    }

    public TableWithAddBtnPanel getTableWithAddBtnPanel() {
        return tableWithAddBtnPanel;
    }

    public static void main(String[] args) {
        ScriptTaskParamsPanel panel = new ScriptTaskParamsPanel(null);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

}
