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

import com.alibaba.compileflow.idea.graph.util.StringUtil;
import com.alibaba.compileflow.idea.graph.model.UserTaskNodeModel;

import com.intellij.openapi.ui.ComboBox;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

/**
 * UserTask-timeout params setting view
 *
 * @author xuan
 * @since 2019/3/20
 */
public class UserTaskTimeoutPanel extends JPanel {

    /**
     * timerType
     */
    private JLabel timerTypeLabel = new JLabel("TimerType:");
    private ComboBox<String> timerTypeBox = new ComboBox<>(
        new String[] {UserTaskNodeModel.TIME_TYPE_ABSOLUTE, UserTaskNodeModel.TIME_TYPE_RELATIVE,
            UserTaskNodeModel.TIME_TYPE_USER_DEFINE});
    /**
     * timerExpress
     */
    private JLabel timerExpressLabel = new JLabel("TimerExpress:");
    private JTextField timerExpressField = new JTextField(30);
    /**
     * tip1
     */
    private JLabel tip11Label = new JLabel("For Example:");
    private JLabel tip12Label = new JLabel("");
    /**
     * tip2
     */
    private JLabel tip21Label = new JLabel("Absolute Time:");
    private JLabel tip22Label = new JLabel("2019-10-01 00:30:30");
    /**
     * tip3
     */
    private JLabel tip31Label = new JLabel("Relative Time:");
    private JLabel tip32Label = new JLabel("1:DAY(Keywords: YEAR,MONTH,WEEK,DAY,HOUR,MINUTE,SECOND)");

    public UserTaskTimeoutPanel() {
        super(new MigLayout("inset 20"));
        initView();
    }

    private void initView() {
        //timerType
        this.add(timerTypeLabel, "gap para");
        this.add(timerTypeBox, "wrap");
        //timerExpress
        this.add(timerExpressLabel, "gap para");
        this.add(timerExpressField, "wrap");
        //tips
        this.add(tip11Label, "gap para");
        this.add(tip12Label, "wrap");
        this.add(tip21Label, "gap para");
        this.add(tip22Label, "wrap");
        this.add(tip31Label, "gap para");
        this.add(tip32Label, "wrap");
    }

    public ComboBox<String> getTimerTypeBox() {
        return timerTypeBox;
    }

    public JTextField getTimerExpressField() {
        return timerExpressField;
    }

    ///////////////// Utils //////////////////////////

    public static void data2View(JPanel panel, UserTaskNodeModel userTaskNodeModel) {
        UserTaskTimeoutPanel userTaskPanel = (UserTaskTimeoutPanel)panel;
        String timerType = userTaskNodeModel.getTimerType();
        if (null == timerType || timerType.trim().length() == 0) {
            userTaskPanel.getTimerTypeBox().setSelectedItem(UserTaskNodeModel.TIME_TYPE_ABSOLUTE);
        } else {
            userTaskPanel.getTimerTypeBox().setSelectedItem(timerType);
        }
        if (null != userTaskNodeModel.getTimeExpress()) {
            userTaskPanel.getTimerExpressField().setText(StringUtil.trimToEmpty(userTaskNodeModel.getTimeExpress()));
        }
    }

    public static void view2Data(JPanel panel, UserTaskNodeModel userTaskNodeModel) {
        UserTaskTimeoutPanel userTaskPanel = (UserTaskTimeoutPanel)panel;
        userTaskNodeModel.setTimerType((String)userTaskPanel.getTimerTypeBox().getSelectedItem());
        userTaskNodeModel.setTimeExpress(StringUtil.trimToEmpty(userTaskPanel.getTimerExpressField().getText()));
    }

    public static void main(String[] args) {
        UserTaskTimeoutPanel panel = new UserTaskTimeoutPanel();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

}
