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

import java.awt.*;

import javax.swing.*;

import com.alibaba.compileflow.idea.graph.mxgraph.Graph;
import com.alibaba.compileflow.idea.graph.styles.StyleSheetLoader;
import com.alibaba.compileflow.idea.graph.util.DialogUtil;
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
     * layout
     */
    private JPanel root = new JPanel();
    private BoxLayout boxLayout = new BoxLayout(root, BoxLayout.Y_AXIS);

    /**
     * style
     */
    private JPanel line1 = new JPanel(new FlowLayout());
    private JLabel styleTips = new JLabel("Choice the style");
    private ComboBox<String> styleCb = new ComboBox<>(
        new String[] {"classic", "color"});
    /**
     * layout
     */
    private JPanel line2 = new JPanel(new FlowLayout());
    private JLabel layoutTips = new JLabel("Choice the layout");
    private ComboBox<String> layoutCb = new ComboBox<>(
        new String[] {"HierarchicalLayout", "OrthogonalLayout", "CompactTreeLayout", "ParallelEdgeLayout",
            "EdgeLabelLayout", "OrganicLayout", "CircleLayout"});

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
        root.setLayout(boxLayout);
        //style
        line1.add(styleTips);
        line1.add(styleCb);
        root.add(line1);
        styleCb.setSelectedItem(SettingsUtils.getStyle());

        //layout
        line2.add(layoutTips);
        line2.add(layoutCb);
        root.add(line2);
        layoutCb.setSelectedItem(SettingsUtils.getLayout());
        return root;
    }

    @Override
    public void doOKAction() {
        super.doOKAction();
        //style
        Object styleSelectItem = styleCb.getSelectedItem();
        SettingsUtils.setStyle(
            null != styleSelectItem ? styleSelectItem.toString() : SettingsUtils.STYLE_DEFAULT);
        //layout
        Object layoutSelectItem = layoutCb.getSelectedItem();
        String layoutSelectItemStr = null != layoutSelectItem ? layoutSelectItem.toString()
            : SettingsUtils.LAYOUT_DEFALUT;
        if (!SettingsUtils.getLayout().equals(layoutSelectItemStr)) {
            DialogUtil.alert("Please reopen the file");
        }
        SettingsUtils.setLayout(
            null != layoutSelectItem ? layoutSelectItem.toString() : SettingsUtils.LAYOUT_DEFALUT);
        //
        graph.setStylesheet(new StyleSheetLoader().load());
        graph.refresh();
    }

}
