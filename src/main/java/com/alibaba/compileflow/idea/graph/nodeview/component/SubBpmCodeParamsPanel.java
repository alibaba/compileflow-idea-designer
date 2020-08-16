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

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

import com.alibaba.compileflow.idea.graph.model.BpmModel;

/**
 * SubBpm params setting view
 *
 * @author xuan
 * @since 2019/3/18
 */
public class SubBpmCodeParamsPanel extends JPanel {

    /**
     * subBpmCode
     */
    private JLabel subBpmCodeLabel = new JLabel("SubBpmCode:");
    private JTextField subBpmCodeLabelField = new JTextField(30);
    /**
     * type
     */
    private JLabel typeLabel = new JLabel("Type:");
    private ComboBox<String> typeComboBox = new ComboBox<>(
        new String[] {BpmModel.BPM_DEFINE_PROCESS, BpmModel.BPM_DEFINE_STATELESS_WORKFLOW}, 100);
    /**
     * waitForCompletion
     */
    private JLabel waitForCompletionLabel = new JLabel("WaitForCompletion:");
    private ComboBox<String> waitForCompletionComboBox = new ComboBox<>(new String[] {"true", "false"}, 100);
    /**
     * waitForTrigger
     */
    private JLabel waitForTriggerLabel = new JLabel("WaitForTrigger:");
    private ComboBox<String> waitForTriggerComboBox = new ComboBox<>(new String[] {"true", "false"}, 100);
    /**
     * Panel for vars
     */
    private TableWithAddBtnPanel tableWithAddBtnPanel;

    public SubBpmCodeParamsPanel(Project project) {
        super(new MigLayout("inset 20"));
        tableWithAddBtnPanel = new TableWithAddBtnPanel(project);
        initView();
    }

    private void initView() {
        //subBpmCode
        this.add(subBpmCodeLabel, "gap para");
        this.add(subBpmCodeLabelField, "wrap");
        //type
        this.add(typeLabel, "gap para");
        this.add(typeComboBox, "wrap");
        //waitForCompletion
        this.add(waitForCompletionLabel, "gap para");
        this.add(waitForCompletionComboBox, "wrap");
        //waitForTrigger
        //this.add(waitForTriggerLabel, "gap para");
        //this.add(waitForTriggerComboBox, "wrap");
        //vars
        this.add(tableWithAddBtnPanel, "span, growx, wrap 10");
    }

    public JTextField getSubBpmCodeLabelField() {
        return subBpmCodeLabelField;
    }

    public ComboBox<String> getTypeComboBox() {
        return typeComboBox;
    }

    public ComboBox<String> getWaitForCompletionComboBox() {
        return waitForCompletionComboBox;
    }

    public ComboBox<String> getWaitForTriggerComboBox() {
        return waitForTriggerComboBox;
    }

    public TableWithAddBtnPanel getTableWithAddBtnPanel() {
        return tableWithAddBtnPanel;
    }

    public static void main(String[] args) {

        SubBpmCodeParamsPanel panel = new SubBpmCodeParamsPanel(null);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

}
