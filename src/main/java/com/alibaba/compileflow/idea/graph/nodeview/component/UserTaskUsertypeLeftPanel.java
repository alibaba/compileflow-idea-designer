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
 * UserTask-usertype-left
 *
 * @author xuan
 * @since 2019/3/20
 */
public class UserTaskUsertypeLeftPanel extends JPanel {

    private JRadioButton groupBtn = new JRadioButton("Group allocation", true);
    private JRadioButton userBtn = new JRadioButton("User allocation");
    private ButtonGroup btnGroup = new ButtonGroup();

    private JButton customBtn = new JButton("Custom");

    public UserTaskUsertypeLeftPanel() {
        super(new MigLayout("inset 20"));
        initView();
    }

    private void initView() {
        btnGroup.add(groupBtn);
        btnGroup.add(userBtn);
        this.add(groupBtn, "wrap");
        this.add(userBtn, "wrap");
        this.add(customBtn, "wrap");
    }

    public JRadioButton getGroupBtn() {
        return groupBtn;
    }

    public JRadioButton getUserBtn() {
        return userBtn;
    }

    public JButton getCustomBtn() {
        return customBtn;
    }

    public static void main(String[] args) {
        UserTaskUsertypeLeftPanel panel = new UserTaskUsertypeLeftPanel();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

}
