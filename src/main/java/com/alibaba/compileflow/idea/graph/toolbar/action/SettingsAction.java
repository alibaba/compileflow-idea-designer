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
package com.alibaba.compileflow.idea.graph.toolbar.action;

import java.awt.event.ActionEvent;

import javax.swing.*;

import com.alibaba.compileflow.idea.graph.mxgraph.Graph;

import com.intellij.openapi.project.Project;

/**
 * @author xuan
 * @since 2020/11/6
 */
public class SettingsAction extends AbstractAction {

    private Project project;

    private Graph graph;

    public SettingsAction(Project project, Graph graph) {
        this.project = project;
        this.graph = graph;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SettingsActionDialog settingsActionDialog = new SettingsActionDialog(project, graph);
        settingsActionDialog.show();
    }

}
