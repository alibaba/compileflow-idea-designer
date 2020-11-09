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

import javax.swing.*;

import com.alibaba.compileflow.idea.graph.mxgraph.Graph;
import com.alibaba.compileflow.idea.graph.styles.StyleSheetLoader;
import com.alibaba.compileflow.idea.graph.util.SettingsUtils;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

/**
 * @author xuan
 * @since 2020/11/6
 */
public class SettingsActionDialog extends DialogWrapper {

    private Graph graph;

    /**
     * style
     */
    private JLabel styleTips = new JLabel("Choice the style");
    private ComboBox<String> styleCb = new ComboBox<>(
        new String[] {SettingsUtils.STYLE_VALUE_CLASSIC, SettingsUtils.STYLE_VALUE_COLOR});

    public SettingsActionDialog(@Nullable Project project, Graph graph) {
        super(project);
        setTitle("Settings");
        setModal(true);
        init();
        this.graph = graph;
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel root = new JPanel();
        //style
        root.add(styleTips);
        styleCb.setSelectedItem(SettingsUtils.getStyle());
        root.add(styleCb);
        return root;
    }

    @Override
    public void doOKAction() {
        super.doOKAction();
        //style
        Object styleSelectItem = styleCb.getSelectedItem();
        SettingsUtils.setStyle(
            null != styleSelectItem ? styleSelectItem.toString() : SettingsUtils.STYLE_VALUE_CLASSIC);
        graph.setStylesheet(new StyleSheetLoader().load());
        graph.refresh();
    }

}
