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

import net.miginfocom.swing.MigLayout;

import javax.swing.*;

/**
 * UserTask-basic params setting view
 *
 * @author xuan
 * @since 2019/3/19
 */
public class UserTaskBasicParamsPanel extends JPanel {
    /**
     * retryMax
     */
    private JLabel retryMaxLabel = new JLabel("RetryMax:");
    private JTextField retryMaxField = new JTextField(30);
    /**
     * retryInterVal
     */
    private JLabel retryInterValLabel = new JLabel("RetryInterVal:");
    private JTextField retryInterValField = new JTextField(30);
    /**
     * signType
     */
    private JLabel signTypeLabel = new JLabel("SignType:");
    private JTextField signTypeField = new JTextField(30);
    /**
     * signPercent
     */
    private JLabel signPercentLabel = new JLabel("SignPercent:");
    private JTextField signPercentField = new JTextField(30);
    /**
     * priority
     */
    private JLabel priorityLabel = new JLabel("Priority:");
    private JTextField priorityField = new JTextField(30);
    /**
     * formKey
     */
    private JLabel formKeyLabel = new JLabel("FormKey:");
    private JTextField formKeyField = new JTextField(30);

    public UserTaskBasicParamsPanel() {
        super(new MigLayout("inset 20"));
        initView();
    }

    private void initView() {
        //retryMax
        this.add(retryMaxLabel, "gap para");
        this.add(retryMaxField, "wrap");
        //retryInterVal
        this.add(retryInterValLabel, "gap para");
        this.add(retryInterValField, "wrap");
        //signType
        this.add(signTypeLabel, "gap para");
        this.add(signTypeField, "wrap");
        //signPercent
        this.add(signPercentLabel, "gap para");
        this.add(signPercentField, "wrap");
        //priority
        this.add(priorityLabel, "gap para");
        this.add(priorityField, "wrap");
        //formKey
        this.add(formKeyLabel, "gap para");
        this.add(formKeyField, "wrap");
    }

    public JTextField getRetryMaxField() {
        return retryMaxField;
    }

    public JTextField getRetryInterValField() {
        return retryInterValField;
    }

    public JTextField getSignTypeField() {
        return signTypeField;
    }

    public JTextField getSignPercentField() {
        return signPercentField;
    }

    public JTextField getPriorityField() {
        return priorityField;
    }

    public JTextField getFormKeyField() {
        return formKeyField;
    }

    public static void main(String[] args) {
        UserTaskBasicParamsPanel panel = new UserTaskBasicParamsPanel();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

}
