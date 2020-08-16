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
package com.alibaba.compileflow.idea.graph;

import com.alibaba.compileflow.idea.graph.codec.DocumentHandler;
import com.alibaba.compileflow.idea.graph.model.BpmModel;
import com.alibaba.compileflow.idea.graph.model.EdgeModel;
import com.alibaba.compileflow.idea.graph.model.GeolocationModel;
import com.alibaba.compileflow.idea.graph.model.LoopProcessNodeModel;
import com.alibaba.compileflow.idea.graph.mxgraph.GraphComponent;
import com.alibaba.compileflow.idea.graph.nodeview.dialog.GlobalPropertyDialog;
import com.alibaba.compileflow.idea.graph.nodeview.NodeEditDialogFactory;
import com.alibaba.compileflow.idea.graph.nodeview.dialog.TransitionPropertiesDialog;
import com.alibaba.compileflow.idea.graph.mxgraph.Cell;
import com.alibaba.compileflow.idea.graph.model.TransitionModel;
import com.alibaba.compileflow.idea.graph.mxgraph.Graph;
import com.alibaba.compileflow.idea.graph.palette.EditorPalette;
import com.alibaba.compileflow.idea.graph.toolbar.FlowChartToolBar;
import com.alibaba.compileflow.idea.graph.util.SwitchModel;
import com.alibaba.compileflow.idea.graph.palette.EditorPaletteModel;
import com.alibaba.compileflow.idea.graph.model.BaseNodeModel;
import com.alibaba.compileflow.idea.graph.model.EndNodeModel;
import com.alibaba.compileflow.idea.graph.model.StartNodeModel;

import com.intellij.designer.ModuleProvider;
import com.intellij.openapi.actionSystem.DataKey;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTabbedPane;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.swing.handler.mxRubberband;
import com.mxgraph.swing.mxGraphOutline;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventSource;
import com.mxgraph.util.mxUndoManager;
import com.mxgraph.util.mxUndoableEdit;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wuxiang
 * @author xuan
 */
public class FlowChartComponent extends JPanel implements ModuleProvider {

    private GraphComponent graphComponent;
    private Graph graph;
    private final Project project;
    private final VirtualFile file;
    private Module module;
    private DocumentHandler documentHandler;

    private JTabbedPane libraryPane;
    private mxGraphOutline graphOutline;
    private mxUndoManager undoManager;

    public static final DataKey<FlowChartComponent> DATA_KEY = DataKey.create(FlowChartComponent.class.getName());

    private SwitchModel switchModel = new SwitchModel();

    public FlowChartComponent(Graph g, GraphComponent graphComponent, Project project, Module module,
        VirtualFile file) {

        this.project = project;
        this.graph = g;
        this.module = module;
        this.file = file;

        this.setName(this.getClass().getSimpleName());
        this.graphComponent = graphComponent;
        this.setLayout(new BorderLayout());
        this.add(this.graphComponent, "Center");
        this.graphComponent.setDragEnabled(true);

        this.graphComponent.getGraphControl().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Object cell = graphComponent.getCellAt(e.getX(), e.getY());

                if (cell != null) {
                    if (cell instanceof mxCell) {
                        mxCell mxCell = (mxCell)cell;

                        if (mxCell.isVertex()) {
                            ApplicationManager.getApplication().invokeLater(() -> {
                                if (switchModel.isPass() && null != module) {
                                    DialogWrapper dialog = NodeEditDialogFactory.getDialog(project, mxCell, graph);
                                    if (null != dialog) {
                                        dialog.show();
                                    }
                                }
                            });

                        } else if (mxCell.isEdge()) {
                            ApplicationManager.getApplication().invokeLater(() -> {
                                if (null != module) {
                                    TransitionPropertiesDialog dialog = new TransitionPropertiesDialog(project,
                                        graphComponent, mxCell);
                                    dialog.pack();
                                    dialog.setVisible(true);
                                }
                            });
                        }
                    }
                } else {
                    ApplicationManager.getApplication().invokeLater(() -> {
                        if (switchModel.isPass() && null != module) {
                            GlobalPropertyDialog globalPropertyDialog = new GlobalPropertyDialog(project, graph);
                            globalPropertyDialog.show();
                        }
                    });
                }
                super.mouseReleased(e);
            }
        });

        // Read bpm file
        this.documentHandler = new DocumentHandler(graph, project, file);
        this.documentHandler.readFromFile();
        // Keeps the selection in sync with the command history
        mxEventSource.mxIEventListener undoHandler = (o, evt) -> undoManager.undoableEditHappened((mxUndoableEdit)evt
            .getProperty("edit"));

        undoManager = new mxUndoManager();
        undoManager.addListener(mxEvent.UNDO, undoHandler);
        undoManager.addListener(mxEvent.REDO, undoHandler);

        // Adds the command history to the model and view
        graph.getModel().addListener(mxEvent.UNDO, undoHandler);
        graph.getView().addListener(mxEvent.UNDO, undoHandler);

        // Creates the graph outline component
        graphOutline = new mxGraphOutline(graphComponent);
        libraryPane = new JBTabbedPane();

        JSplitPane inner = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
            libraryPane, graphOutline);
        inner.setDividerLocation(320);
        inner.setResizeWeight(1);
        inner.setDividerSize(6);
        inner.setBorder(null);

        JSplitPane outer = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, inner,
            graphComponent);
        outer.setOneTouchExpandable(true);
        outer.setDividerLocation(200);
        outer.setDividerSize(6);
        outer.setBorder(null);

        // Puts everything together
        setLayout(new BorderLayout());
        add(outer, BorderLayout.CENTER);

        // Init Editor Palette
        EditorPalette editorPalette = insertPalette(BpmModel.BPM_DEFINE_PROCESS, graphComponent);
        editorPalette.setModel(new EditorPaletteModel(file.getExtension()));

        installToolBar();
        installListeners();

        graph.getModel().addListener(mxEvent.CHANGE, (sender, evt) -> {
            Object changes = evt.getProperties().get("changes");
            if (changes instanceof ArrayList) {

                ArrayList list = (ArrayList)changes;
                for (Object o : list) {

                    if (o instanceof mxGraphModel.mxGeometryChange) {
                        mxCell mxCell = (com.mxgraph.model.mxCell)((mxGraphModel.mxGeometryChange)o).getCell();
                        // Update geometry change to node
                        mxGeometry geometry = mxCell.getGeometry();
                        if (mxCell.isVertex()) {
                            BaseNodeModel nodeModel = (BaseNodeModel)mxCell.getValue();

                            if (nodeModel.getG() != null) {
                                GeolocationModel g1 = new GeolocationModel(nodeModel.getG());
                                nodeModel.setG(GeolocationModel
                                    .toG(geometry.getX(), geometry.getY(), (int)geometry.getWidth(),
                                        (int)geometry.getHeight()));
                                if (nodeModel.getId() == null) {
                                    nodeModel.setId(Cell.createId());
                                }
                            } else {
                                if (nodeModel.getId() == null) {
                                    nodeModel.setId(Cell.createId());
                                }
                                if (nodeModel instanceof StartNodeModel || nodeModel instanceof EndNodeModel) {
                                    nodeModel.setG(GeolocationModel.toG(geometry.getX(), geometry.getY(), 30, 30));
                                } else if (nodeModel instanceof LoopProcessNodeModel) {
                                    nodeModel.setG(GeolocationModel.toG(geometry.getX(), geometry.getY(), 200, 200));
                                } else {
                                    nodeModel.setG(GeolocationModel.toG(geometry.getX(), geometry.getY(), 88, 48));
                                }
                                mxCell.setValue(nodeModel);
                            }

                        } else if (mxCell.isEdge()) {
                        }
                    } else if (o instanceof mxGraphModel.mxTerminalChange) {

                        mxCell pCell = (com.mxgraph.model.mxCell)((mxGraphModel.mxTerminalChange)o).getCell();

                        if (pCell.getValue() instanceof EdgeModel) {

                            EdgeModel edgeRelation = (EdgeModel)pCell.getValue();
                            mxCell terminalCell = (com.mxgraph.model.mxCell)((mxGraphModel.mxTerminalChange)o)
                                .getTerminal();
                            if (null == terminalCell) {
                                return;
                            }
                            BaseNodeModel tNode = (BaseNodeModel)terminalCell.getValue();
                            List<TransitionModel> toLists = new ArrayList<>();
                            TransitionModel transition = new TransitionModel();
                            transition.setTo(tNode.getId());
                            toLists.add(transition);
                            edgeRelation.setTransition(transition);
                            BaseNodeModel abstractNode = edgeRelation.getCurrentNode();
                            List<TransitionModel> olderList = abstractNode.getOutTransitions();
                            if (olderList != null) {
                                olderList.addAll(toLists);
                                abstractNode.setOutTransitions(olderList);
                            } else {
                                abstractNode.setOutTransitions(toLists);
                            }
                        } else if (pCell.getValue() instanceof String) {
                            EdgeModel edgeRelation = new EdgeModel();
                            pCell.setValue(edgeRelation);
                            mxCell terminalCell = (com.mxgraph.model.mxCell)((mxGraphModel.mxTerminalChange)o)
                                .getTerminal();
                            BaseNodeModel currentNode = (BaseNodeModel)terminalCell.getValue();
                            if (currentNode != null) {
                                edgeRelation.setCurrentNode(currentNode);
                            }
                        }
                    }
                }
            }

            documentHandler.saveToFile();
            graphComponent.validateGraph();
            graphComponent.repaint();
        });

        installHandlers();
        installListeners();
    }

    private void installToolBar() {
        add(new FlowChartToolBar(this, JToolBar.VERTICAL), BorderLayout.EAST);
    }

    private void installHandlers() {
        //Mouse Selection Node Ability
        new mxRubberband(graphComponent);
        //Keyboard Operating Ability
        new EditorKeyboardHandler(graphComponent);
    }

    public mxUndoManager getUndoManager() {
        return undoManager;
    }

    private EditorPalette insertPalette(String title, GraphComponent graphComponent) {
        final EditorPalette palette = new EditorPalette();
        final JScrollPane scrollPane = new JBScrollPane(palette);
        scrollPane
            .setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane
            .setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        libraryPane.add(title, scrollPane);
        //Display scrollbars when dragging libraryPane
        libraryPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = scrollPane.getWidth()
                    - scrollPane.getVerticalScrollBar().getWidth();
                palette.setPreferredWidth(w);
            }
        });

        return palette;
    }

    private void installListeners() {

        // Installs the popup menu in the graph component
        graphComponent.getGraphControl().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Handles context menu on the Mac where the trigger is on mousepressed
                mouseReleased(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }
        });
    }

    public GraphComponent getGraphComponent() {
        return this.graphComponent;
    }

    @Override
    public Module getModule() {
        if (null == this.module) {
            return null;
        }
        if (this.module.isDisposed()) {
            this.module = ModuleUtil.findModuleForFile(this.file, this.project);
        }
        return this.module;
    }

    @Override
    public Project getProject() {
        return this.project;
    }

    public Graph getGraph() {
        return this.graph;
    }

    public VirtualFile getFile() {
        return this.file;
    }

    public void dispose() {

    }

    public SwitchModel getSwitchModel() {
        return switchModel;
    }

}
