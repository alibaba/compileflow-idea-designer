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

import com.alibaba.compileflow.idea.graph.FlowChartComponent;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Revocation operation
 *
 * @author wuxiang
 */
public class HistoryAction extends AbstractAction {

    private boolean undo;

    public HistoryAction(boolean undo) {
        this.undo = undo;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        FlowChartComponent editor = getEditor(e);

        if (editor != null) {
            if (undo) {
                if (editor.getUndoManager().canUndo()) {
                    editor.getUndoManager().undo();
                }
            } else {
                if (editor.getUndoManager().canRedo()) {
                    editor.getUndoManager().redo();
                }
            }
        }
    }

    private static FlowChartComponent getEditor(ActionEvent e) {
        if (e.getSource() instanceof Component) {
            Component component = (Component)e.getSource();

            while (component != null
                && !(component instanceof FlowChartComponent)) {
                component = component.getParent();
            }

            return (FlowChartComponent)component;
        }

        return null;
    }

}
