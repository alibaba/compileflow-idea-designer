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
package com.alibaba.compileflow.idea.plugin.provider.fileeditor;

import com.alibaba.compileflow.idea.plugin.LanguageConstants;
import com.alibaba.compileflow.idea.graph.FlowChartComponent;
import com.alibaba.compileflow.idea.graph.mxgraph.GraphComponent;
import com.alibaba.compileflow.idea.graph.mxgraph.Graph;
import com.alibaba.compileflow.idea.graph.mxgraph.GraphModel;
import com.alibaba.compileflow.idea.graph.styles.StyleSheetLoader;

import com.intellij.codeHighlighting.BackgroundEditorHighlighter;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorLocation;
import com.intellij.openapi.fileEditor.FileEditorState;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.UserDataHolderBase;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.testFramework.LightVirtualFile;
import com.mxgraph.view.mxStylesheet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

import java.beans.PropertyChangeListener;

/**
 * FlowChartFileEditor
 *
 * @author wuxiang
 * @author xuan
 */
public class FlowChartFileEditor extends UserDataHolderBase implements FileEditor {

    private final FlowChartComponent flowChartComponent;

    public FlowChartFileEditor(@NotNull Project project, @NotNull VirtualFile file) {

        VirtualFile vf = file instanceof LightVirtualFile ? ((LightVirtualFile)file).getOriginalFile() : file;

        Module module = ModuleUtil.findModuleForFile(vf, project);
        mxStylesheet stylesheet = new StyleSheetLoader().load();
        Graph graph = new Graph(new GraphModel(), stylesheet);
        GraphComponent graphComponent = createGraphComponent("bpm", graph);
        this.flowChartComponent = new FlowChartComponent(graph, graphComponent, project, module, file);
    }

    private GraphComponent createGraphComponent(String name, Graph graph) {
        GraphComponent graphComponent = new GraphComponent(graph);
        if (name != null) {
            graphComponent.setName(name);
        }

        return graphComponent;
    }

    @NotNull
    @Override
    public JComponent getComponent() {
        return this.flowChartComponent;
    }

    @Nullable
    @Override
    public JComponent getPreferredFocusedComponent() {
        return this.flowChartComponent.getGraphComponent();
    }

    @NotNull
    @Override
    public String getName() {
        return LanguageConstants.TAB_CHART_NAME;
    }

    @Override
    public void setState(@NotNull FileEditorState fileEditorState) {

    }

    public FlowChartComponent getEditor() {
        return this.flowChartComponent;
    }

    @Override
    public boolean isModified() {
        return false;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void selectNotify() {

    }

    @Override
    public void deselectNotify() {

    }

    @Override
    public void addPropertyChangeListener(@NotNull PropertyChangeListener propertyChangeListener) {

    }

    @Override
    public void removePropertyChangeListener(@NotNull PropertyChangeListener propertyChangeListener) {

    }

    @Nullable
    @Override
    public BackgroundEditorHighlighter getBackgroundHighlighter() {
        return null;
    }

    @Nullable
    @Override
    public FileEditorLocation getCurrentLocation() {
        return null;
    }

    @Override
    public void dispose() {
        this.flowChartComponent.dispose();
    }

}
