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
package com.alibaba.compileflow.idea.graph.toolbar;

import com.alibaba.compileflow.idea.graph.toolbar.action.DeleteCellAction;
import com.alibaba.compileflow.idea.graph.toolbar.action.HelpAction;
import com.alibaba.compileflow.idea.graph.toolbar.action.MoreBigAction;
import com.alibaba.compileflow.idea.graph.toolbar.action.MoreSmallAction;
import com.alibaba.compileflow.idea.graph.toolbar.action.PngEncodeAction;
import com.alibaba.compileflow.idea.graph.toolbar.action.SettingsAction;
import com.alibaba.compileflow.idea.graph.toolbar.action.SwitchModelAction;
import com.alibaba.compileflow.idea.graph.toolbar.action.VersionAction;
import com.alibaba.compileflow.idea.graph.FlowChartComponent;
import com.alibaba.compileflow.idea.graph.util.ImageIconUtil;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @author xuan
 */
public class FlowChartToolBar extends JToolBar {

    public FlowChartToolBar(FlowChartComponent editor, int orientation) {

        super(orientation);
        setFloatable(false);

        add(bind("Home", new HelpAction(),
            "/com/alibaba/compileflow/idea/graph/images/toolbar/home.png", editor));
        add(bind("Version", new VersionAction(),
            "/com/alibaba/compileflow/idea/graph/images/toolbar/version.png", editor));
        addSeparator();

        add(bind("Cut", TransferHandler.getCutAction(),
            "/com/alibaba/compileflow/idea/graph/images/toolbar/cut.png",
            editor));
        add(bind("Copy", TransferHandler.getCopyAction(),
            "/com/alibaba/compileflow/idea/graph/images/toolbar/copy.png",
            editor));
        add(bind("Paste", TransferHandler.getPasteAction(),
            "/com/alibaba/compileflow/idea/graph/images/toolbar/paste.png", editor));
        add(bind("Delete", new DeleteCellAction(editor.getGraph()),
            "/com/alibaba/compileflow/idea/graph/images/toolbar/delete.png", editor));
        add(bind("Png", new PngEncodeAction(editor.getGraph(), editor.getGraphComponent(), editor.getFile()),
            "/com/alibaba/compileflow/idea/graph/images/toolbar/png.png", editor));

        add(bind("MoreBig", new MoreBigAction(editor.getGraphComponent()),
            "/com/alibaba/compileflow/idea/graph/images/toolbar/more_big.png", editor));
        add(bind("MoreSmall", new MoreSmallAction(editor.getGraphComponent()),
            "/com/alibaba/compileflow/idea/graph/images/toolbar/more_small.png", editor));
        add(bind("Settings", new SettingsAction(editor.getProject(), editor.getGraph()),
            "/com/alibaba/compileflow/idea/graph/images/toolbar/settings.png", editor));
        addSeparator();

        SwitchModelAction switchModelAction = new SwitchModelAction();
        JButton switchBtn = add(bind(
            editor.getSwitchModel().getBtnTextStr(),
            switchModelAction,
            editor.getSwitchModel().getIconStr(),
            editor));
        switchModelAction.setBtn(switchBtn);
        switchModelAction.setSwitchModel(editor.getSwitchModel());
    }

    @Override
    public void addSeparator() {
        addSeparator(null);
    }

    @Override
    public void addSeparator(Dimension size) {
        JToolBar.Separator s = new JToolBar.Separator(size);
        add(s);
    }

    @Override
    protected JButton createActionComponent(Action a) {
        JButton button = super.createActionComponent(a);
        button.setPreferredSize(new Dimension(30, 30));
        return button;
    }

    private Action bind(String name, final Action action, String iconUrl, FlowChartComponent editor) {
        Icon icon = ImageIconUtil.buildImageIcon(iconUrl, 15);

        AbstractAction newAction = new AbstractAction(name, icon) {
            @Override
            public void actionPerformed(ActionEvent e) {
                action.actionPerformed(new ActionEvent(editor.getGraphComponent(), e
                    .getID(), e.getActionCommand()));
            }
        };

        newAction.putValue(Action.SHORT_DESCRIPTION, action.getValue(Action.SHORT_DESCRIPTION));

        return newAction;
    }

}
