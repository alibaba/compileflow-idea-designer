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

import com.intellij.openapi.ui.ComboBox;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

import com.alibaba.compileflow.idea.graph.model.BpmModel;

/**
 * Global basic setting
 *
 * @author xuan
 * @since 2019/2/20
 */
public class GlobalBasicPanel extends JPanel {
    /**
     * code
     */
    private JLabel codeLabel = new JLabel("code:");
    private JTextField codeField = new JTextField(20);
    /**
     * type
     */
    private JLabel typeLabel = new JLabel("type:");
    private ComboBox<String> typeBox = new ComboBox<>();
    private DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
    /**
     * name
     */
    private JLabel nameLabel = new JLabel("name:");
    private JTextField nameField = new JTextField(20);
    /**
     * description
     */
    private JLabel descriptionLabel = new JLabel("description");
    private JTextField descriptionField = new JTextField(20);

    public GlobalBasicPanel() {
        super(new MigLayout("inset 20"));
        initView();
    }

    public Data getData() {
        Data data = new Data();
        data.code = codeField.getText();
        data.name = nameField.getText();
        data.type = (String) comboBoxModel.getSelectedItem();
        data.description = descriptionField.getText();
        return data;
    }

    public void setData(Data data) {
        codeField.setText(data.code);
        nameField.setText(data.name);
        descriptionField.setText(data.description);
        typeBox.setSelectedItem(data.type);
    }

    private void initView() {
        this.setOpaque(false);
        this.add(codeLabel, "gap para");
        this.add(codeField, "span, growx");
        this.add(typeLabel, "gap para");
        this.add(typeBox, "span, growx, wrap");
        this.add(nameLabel, "gap para");
        this.add(nameField, "span, growx");
        this.add(descriptionLabel, "gap para");
        this.add(descriptionField, "span, growx, wrap");

        comboBoxModel.insertElementAt(BpmModel.BPM_DEFINE_PROCESS, 0);
        comboBoxModel.insertElementAt(BpmModel.BPM_DEFINE_STATEMACHINE, 1);
        comboBoxModel.insertElementAt(BpmModel.BPM_DEFINE_WORKFLOW, 2);
        comboBoxModel.insertElementAt(BpmModel.BPM_DEFINE_STATELESS_WORKFLOW, 3);
        typeBox.setModel(comboBoxModel);
    }

    public static class Data {
        public String code;
        public String name;
        public String type;
        public String description;
    }

    public static void main(String[] args) {
        GlobalBasicPanel panel = new GlobalBasicPanel();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

}
