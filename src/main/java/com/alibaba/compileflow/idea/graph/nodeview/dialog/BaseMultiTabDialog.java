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
package com.alibaba.compileflow.idea.graph.nodeview.dialog;

import com.alibaba.compileflow.idea.graph.mxgraph.Graph;
import com.intellij.openapi.project.Project;
import com.mxgraph.model.mxCell;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Multi tab base dialog
 *
 * @author xuan
 * @since 2019/3/19
 */
public abstract class BaseMultiTabDialog extends BaseDialog {

    protected JPanel[] panels;
    private String[] tabNames;

    public BaseMultiTabDialog(@Nullable Project project, mxCell cell, Graph graph) {
        super(project, cell, graph);
    }

    @Override
    protected void initView() {
        rootTab.addTab("basic setting", new JLabel("basic setting"));
        rootTab.setComponentAt(0, basicPanel);

        panels = getPanels();
        tabNames = getTabNames();

        if (null != panels && panels.length > 0) {
            for (int i = 0, n = panels.length; i < n; i++) {
                rootTab.addTab(tabNames[i], new JLabel(tabNames[i]));
                rootTab.setComponentAt(i + 1, panels[i]);
            }
        }

        initParamPanelView();
    }

    @Override
    protected JPanel getParamPanel(Project project, Graph graph, mxCell cell) {
        return null;
    }

    /**
     * Return panels
     *
     * @return JPanel[]
     */
    protected abstract JPanel[] getPanels();

    /**
     * Return tabNames
     *
     * @return String[]
     */
    protected abstract String[] getTabNames();

}
