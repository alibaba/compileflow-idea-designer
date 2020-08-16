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
import com.alibaba.compileflow.idea.graph.model.ActionModel;
import com.alibaba.compileflow.idea.graph.model.UserTaskNodeModel;

import com.intellij.openapi.project.Project;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;

/**
 * UserTask-usertype params setting view
 *
 * @author xuan
 * @since 2019/3/20
 */
public class UserTaskUsertypePanel extends JPanel {

    private UserTaskUsertypeLeftPanel leftPanel = new UserTaskUsertypeLeftPanel();
    private UserTaskUsertypeRightGroupPanel groupPanel = new UserTaskUsertypeRightGroupPanel();
    private UserTaskUsertypeRightUserPanel userPanel = new UserTaskUsertypeRightUserPanel();
    private ActionPanel customPanel;

    private String userType = UserTaskNodeModel.GROUP_TYPE;
    private boolean isCustom = false;

    public UserTaskUsertypePanel(Project project) {
        customPanel = new ActionPanel(project);
        BoxLayout layout = new BoxLayout(this, BoxLayout.X_AXIS);
        this.setLayout(layout);
        initView();
        initListener();
    }

    private void initView() {
        Border border = BorderFactory.createLineBorder(Color.GRAY, 1);
        leftPanel.setBorder(border);
        groupPanel.setBorder(border);
        userPanel.setBorder(border);
        customPanel.setBorder(border);

        this.add(leftPanel);
        this.add(groupPanel);
        this.add(userPanel);
        this.add(customPanel);
        refresh();
    }

    private void initListener() {
        leftPanel.getGroupBtn().addChangeListener((e) -> {
            if (leftPanel.getGroupBtn().isSelected()) {
                userType = UserTaskNodeModel.GROUP_TYPE;
                isCustom = false;
                refresh();
            }
        });
        leftPanel.getUserBtn().addChangeListener((e) -> {
            if (leftPanel.getUserBtn().isSelected()) {
                userType = UserTaskNodeModel.USER_TYPE;
                isCustom = false;
                refresh();
            }
        });
        leftPanel.getCustomBtn().addActionListener((e) -> {
            isCustom = true;
            refresh();
        });
    }

    private void refresh() {
        clearRight();
        if (UserTaskNodeModel.GROUP_TYPE.equals(userType) && isCustom) {
            leftPanel.getGroupBtn().setSelected(true);
            customPanel.setVisible(true);
        }
        if (UserTaskNodeModel.GROUP_TYPE.equals(userType) && !isCustom) {
            leftPanel.getGroupBtn().setSelected(true);
            groupPanel.setVisible(true);
        }
        if (UserTaskNodeModel.USER_TYPE.equals(userType) && isCustom) {
            leftPanel.getUserBtn().setSelected(true);
            customPanel.setVisible(true);
        }
        if (UserTaskNodeModel.USER_TYPE.equals(userType) && !isCustom) {
            leftPanel.getUserBtn().setSelected(true);
            userPanel.setVisible(true);
        }
    }

    private void clearRight() {
        groupPanel.setVisible(false);
        userPanel.setVisible(false);
        customPanel.setVisible(false);
    }

    public UserTaskUsertypeLeftPanel getLeftPanel() {
        return leftPanel;
    }

    public UserTaskUsertypeRightGroupPanel getGroupPanel() {
        return groupPanel;
    }

    public UserTaskUsertypeRightUserPanel getUserPanel() {
        return userPanel;
    }

    public ActionPanel getCustomPanel() {
        return customPanel;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public boolean isCustom() {
        return isCustom;
    }

    public void setCustom(boolean custom) {
        isCustom = custom;
    }

    ///////////////// Utils //////////////////////////

    public static void data2View(JPanel panel, UserTaskNodeModel model) {
        UserTaskUsertypePanel userTaskPanel = (UserTaskUsertypePanel)panel;

        //groupType && groupId
        if (UserTaskNodeModel.GROUP_TYPE.equals(model.getUserType())
            && null == model.getUserAction()) {
            userTaskPanel.setUserType(UserTaskNodeModel.GROUP_TYPE);
            userTaskPanel.setCustom(false);
            userTaskPanel.getGroupPanel().getGroupIdField().setText(
                StringUtil.trimToEmpty(model.getGroupId()));
            userTaskPanel.refresh();
        }
        //groupType && custom
        else if (UserTaskNodeModel.GROUP_TYPE.equals(model.getUserType())
            && null != model.getUserAction()) {
            userTaskPanel.setUserType(UserTaskNodeModel.GROUP_TYPE);
            userTaskPanel.setCustom(true);
            ActionPanel.data2View(userTaskPanel.getCustomPanel(), model.getUserAction());
            userTaskPanel.refresh();
        }
        //userType && userId
        else if (UserTaskNodeModel.USER_TYPE.equals(model.getUserType())
            && null == model.getUserAction()) {
            userTaskPanel.setUserType(UserTaskNodeModel.USER_TYPE);
            userTaskPanel.setCustom(false);
            userTaskPanel.getUserPanel().getUserIdField().setText(
                StringUtil.trimToEmpty(model.getUserId()));
            userTaskPanel.refresh();
        }
        //userType && custom
        else if (UserTaskNodeModel.USER_TYPE.equals(model.getUserType())
            && null != model.getUserAction()) {
            userTaskPanel.setUserType(UserTaskNodeModel.USER_TYPE);
            userTaskPanel.setCustom(true);
            ActionPanel.data2View(userTaskPanel.getCustomPanel(), model.getUserAction());
            userTaskPanel.refresh();
        }
    }

    public static void view2Data(JPanel panel, UserTaskNodeModel model) {
        UserTaskUsertypePanel userTaskPanel = (UserTaskUsertypePanel)panel;

        if (null == userTaskPanel.getUserType() || userTaskPanel.getUserType().trim().length() == 0) {
            model.setUserType(UserTaskNodeModel.USER_TYPE);
        } else {
            model.setUserType(userTaskPanel.getUserType());
        }

        //groupType && groupId
        if (UserTaskNodeModel.GROUP_TYPE.equals(userTaskPanel.getUserType())
            && !userTaskPanel.isCustom) {
            model.setGroupId(
                StringUtil.trimToEmpty(userTaskPanel.getGroupPanel().getGroupIdField().getText()));
        }
        //groupType && custom
        else if (UserTaskNodeModel.GROUP_TYPE.equals(userTaskPanel.getUserType())
            && userTaskPanel.isCustom) {
            ActionModel userAction = model.getUserAction();
            if (null == userAction) {
                model.setUserAction(ActionModel.of());
            }
            ActionPanel.view2Data(userTaskPanel.getCustomPanel(), model.getUserAction());
        }
        //userType && userId
        else if (UserTaskNodeModel.USER_TYPE.equals(userTaskPanel.getUserType())
            && !userTaskPanel.isCustom) {
            model.setUserId(
                StringUtil.trimToEmpty(userTaskPanel.getUserPanel().getUserIdField().getText()));
        }
        //userType && custom
        else if (UserTaskNodeModel.USER_TYPE.equals(userTaskPanel.getUserType())
            && userTaskPanel.isCustom) {
            ActionModel userAction = model.getUserAction();
            if (null == userAction) {
                model.setUserAction(ActionModel.of());
            }
            ActionPanel.view2Data(userTaskPanel.getCustomPanel(), model.getUserAction());
        }
    }

}
