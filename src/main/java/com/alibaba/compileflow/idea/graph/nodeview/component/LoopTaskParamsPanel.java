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

import com.alibaba.compileflow.idea.graph.util.DialogUtil;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

/**
 * LoopTask params setting view
 *
 * @author xuan
 * @since 2019/3/16
 */
public class LoopTaskParamsPanel extends JPanel {

    /**
     * collectionVarName
     */
    private JLabel collectionVarNameLabel = new JLabel("collectionVarName:");
    private JComboxTextField collectionVarNameField;
    /**
     * variableName
     */
    private JLabel variableNameLabel = new JLabel("variableName:");
    private JTextField variableNameField = new JTextField(20);
    /**
     * indexVarName
     */
    private JLabel indexVarNameLabel = new JLabel("indexVarName:");
    private JTextField indexVarNameField = new JTextField(20);
    /**
     * variableClass
     */
    private JLabel variableClassLabel = new JLabel("variableClass:");
    private JTextField variableClassField = new JTextField(20);
    /**
     * startNodeId
     */
    private JLabel startNodeIdLabel = new JLabel("startNodeId:");
    private JTextField startNodeIdField = new JTextField(20);
    /**
     * endNodeId
     */
    private JLabel endNodeIdLabel = new JLabel("endNodeId:");
    private JTextField endNodeIdField = new JTextField(20);

    private Project project;

    public LoopTaskParamsPanel(Project project, Set<String> contextVarNameSet) {
        super(new MigLayout("inset 20"));
        this.project = project;
        collectionVarNameField = new JComboxTextField(contextVarNameSet, 20);
        initView();
        initListener();
    }

    private void initView() {
        //collectionVarName
        this.add(collectionVarNameLabel, "gap para");
        this.add(collectionVarNameField, "wrap");
        //variableName
        this.add(variableNameLabel, "gap para");
        this.add(variableNameField, "wrap");
        //indexVarName
        this.add(indexVarNameLabel, "gap para");
        this.add(indexVarNameField, "wrap");
        //variableClass
        this.add(variableClassLabel, "gap para");
        this.add(variableClassField, "wrap");
        //startNodeId
        this.add(startNodeIdLabel, "gap para");
        this.add(startNodeIdField, "wrap");
        //endNodeId
        this.add(endNodeIdLabel, "gap para");
        this.add(endNodeIdField, "wrap");
    }

    private void initListener() {
        //Click select className fill to variableClassField
        variableClassField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mouseClicked(e);
                String selectedClassName = DialogUtil.selectClassName(project);
                if (null != selectedClassName) {
                    variableClassField.setText(selectedClassName);
                }
            }
        });
    }

    public JComboxTextField getCollectionVarNameField() {
        return collectionVarNameField;
    }

    public JTextField getVariableNameField() {
        return variableNameField;
    }

    public JTextField getIndexVarNameField() {
        return indexVarNameField;
    }

    public JTextField getVariableClassField() {
        return variableClassField;
    }

    public JTextField getStartNodeIdField() {
        return startNodeIdField;
    }

    public JTextField getEndNodeIdField() {
        return endNodeIdField;
    }

}
