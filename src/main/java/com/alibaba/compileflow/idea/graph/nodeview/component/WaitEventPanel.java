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

import javax.swing.*;

import net.miginfocom.swing.MigLayout;

/**
 * Note Edit
 *
 * @author xuan
 * @since 2019/3/20
 */
public class WaitEventPanel extends JPanel {

    /**
     * commit
     */
    private JLabel eventNameLabel = new JLabel("EventName:");
    private JTextField eventNameField = new JTextField(30);

    public WaitEventPanel() {
        super(new MigLayout("inset 20"));
        initView();
    }

    private void initView() {
        this.add(eventNameLabel, "gap para");
        this.add(eventNameField, "wrap");
    }

    public JTextField getEventNameField() {
        return eventNameField;
    }
}
