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
package com.alibaba.compileflow.idea.graph.mxgraph;

import com.intellij.ui.JBColor;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxUtils;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.print.PageFormat;

/**
 * GraphComponent
 *
 * @author wuxiang
 */
public class GraphComponent extends mxGraphComponent {

    public GraphComponent(Graph graph) {

        super(graph);
        setPageVisible(true);
        setGridVisible(false);
        setToolTips(true);

        setZoomFactor(1.05D);
        setTolerance(2);
        setZoomPolicy(0);
        setKeepSelectionVisibleOnZoom(true);
        setDragEnabled(true);
        setPreferPageSize(true);
        setEnterStopsCellEditing(true);
        getConnectionHandler().setHandleEnabled(true);
        // Default not to replicate nodes
        getConnectionHandler().setCreateTarget(false);
        PageFormat pageFormat = this.getGraph().getModel().setDefaultPageFormat();
        setPageFormat(pageFormat);
        setFocusTraversalKeysEnabled(false);
        setGridColor(mxUtils.parseColor("#c0c0c0"));

        // Sets the background to white
        getViewport().setOpaque(true);
        getViewport().setBackground(JBColor.WHITE);

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    GraphComponent.this.startEditing();
                } else if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    // Knowing it's the cell
                    GraphComponent.this.getGraph().fireEvent(new mxEventObject("delete"));
                } else if (e.getKeyCode() == 9) {
                    if ((e.getModifiers() & 1) != 0) {
                        GraphComponent.this.getGraph().selectPreviousCell();
                    } else {
                        GraphComponent.this.getGraph().selectNextCell();
                    }
                }

            }
        });
    }

    @Override
    public Graph getGraph() {
        return (Graph)this.graph;
    }

    /**
     * Overrides drop behaviour to set the cell style if the target
     * is not a valid drop target and the cells are of the same
     * type (eg. both vertices or both edges).
     */
    @Override
    public Object[] importCells(Object[] cells, double dx, double dy,
        Object target, Point location) {

        return super.importCells(cells, dx, dy, target, location);
    }
}
