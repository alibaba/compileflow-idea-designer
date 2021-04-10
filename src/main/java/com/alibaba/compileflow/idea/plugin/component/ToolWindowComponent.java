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
package com.alibaba.compileflow.idea.plugin.component;

import com.alibaba.compileflow.idea.graph.FlowChartComponent;
import com.alibaba.compileflow.idea.graph.util.ImageIconUtil;
import com.alibaba.compileflow.idea.plugin.provider.fileeditor.FlowChartFileEditor;

import com.intellij.openapi.actionSystem.DataProvider;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.util.ui.update.MergingUpdateQueue;
import com.intellij.util.ui.update.Update;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

import java.awt.*;

/**
 * @author xuan
 */
public class ToolWindowComponent implements ProjectComponent {

    //private Project myProject;
    //private boolean myToolWindowReady = false;
    //private boolean myToolWindowDisposed = false;
    //private ToolWindow myToolWindow;
    //private ToolWindowComponent.MyToolWindowPanel myToolWindowPanel;
    //
    //private FileEditorManager fileEditorManager;
    //private final MergingUpdateQueue myQueue = new MergingUpdateQueue("property.inspector", 200, true, null);
    //
    //public ToolWindowComponent(Project project, FileEditorManager fileEditorManager) {
    //    this.myProject = project;
    //    this.fileEditorManager = fileEditorManager;
    //    ToolWindowComponent.MyFileEditorManagerListener listener
    //        = new ToolWindowComponent.MyFileEditorManagerListener();
    //    this.fileEditorManager.addFileEditorManagerListener(listener);
    //
    //}
    //
    //@Override
    //public void projectOpened() {
    //    StartupManager.getInstance(this.myProject).registerPostStartupActivity(() -> myToolWindowReady = true);
    //}
    //
    //@Override
    //public void projectClosed() {
    //    if (this.myToolWindowPanel != null) {
    //        this.myToolWindowPanel = null;
    //        this.myToolWindow = null;
    //        this.myToolWindowDisposed = true;
    //    }
    //}
    //
    //private void initToolWindow() {
    //    this.myToolWindowPanel = new ToolWindowComponent.MyToolWindowPanel();
    //    this.myToolWindowPanel.setLayout(new BorderLayout());
    //    this.myToolWindow = ToolWindowManager.getInstance(this.myProject).registerToolWindow("COMPILEFLOW EDITOR",
    //        this.myToolWindowPanel, ToolWindowAnchor.BOTTOM, this.myProject, true);
    //    this.myToolWindow.setIcon(ImageIconUtil.LOGO_ICON);
    //    this.myToolWindow.setAvailable(false, null);
    //}
    //
    //private class MyToolWindowPanel extends JPanel implements DataProvider {
    //
    //    @Nullable
    //    @Override
    //    public Object getData(@NonNls String dataId) {
    //        return FlowChartComponent.DATA_KEY.is(dataId) ? getActiveFormEditor() : null;
    //    }
    //}
    //
    //private void processFileEditorChange(final FlowChartFileEditor newEditor) {
    //    this.myQueue.cancelAllUpdates();
    //    this.myQueue.queue(new Update("update") {
    //        @Override
    //        public void run() {
    //
    //            if (ToolWindowComponent.this.myToolWindowReady
    //                && !ToolWindowComponent.this.myToolWindowDisposed) {
    //                FlowChartComponent activeFormEditor = newEditor != null ? newEditor.getEditor() : null;
    //                if (myToolWindow == null) {
    //                    if (activeFormEditor == null) {
    //                        return;
    //                    }
    //
    //                    initToolWindow();
    //                }
    //
    //                if (activeFormEditor == null) {
    //                    myToolWindow.setAvailable(false, null);
    //                } else {
    //                    myToolWindow.setAvailable(true, null);
    //                    myToolWindow.show(null);
    //                }
    //
    //            }
    //        }
    //    });
    //}
    //
    //@Nullable
    //private FlowChartFileEditor getActiveFormFileEditor() {
    //    FileEditor[] fileEditors = fileEditorManager.getSelectedEditors();
    //
    //    for (FileEditor fileEditor : fileEditors) {
    //        if (fileEditor instanceof FlowChartFileEditor) {
    //            return (FlowChartFileEditor)fileEditor;
    //        }
    //    }
    //
    //    return null;
    //}
    //
    //@Nullable
    //private FlowChartComponent getActiveFormEditor() {
    //    FlowChartFileEditor formEditor = this.getActiveFormFileEditor();
    //    return formEditor == null ? null : formEditor.getEditor();
    //}
    //
    //@NotNull
    //@NonNls
    //@Override
    //public String getComponentName() {
    //    return "ToolWindowComponent";
    //}
    //
    //private class MyFileEditorManagerListener implements FileEditorManagerListener {
    //    private MyFileEditorManagerListener() {
    //    }
    //
    //    @Override
    //    public void fileOpened(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
    //        processFileEditorChange(ToolWindowComponent.this.getActiveFormFileEditor());
    //    }
    //
    //    @Override
    //    public void fileClosed(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
    //        ApplicationManager.getApplication().invokeLater(new Runnable() {
    //            @Override
    //            public void run() {
    //                processFileEditorChange(ToolWindowComponent.this.getActiveFormFileEditor());
    //            }
    //        });
    //    }
    //
    //    @Override
    //    public void selectionChanged(@NotNull FileEditorManagerEvent event) {
    //
    //        FlowChartFileEditor newEditor = event.getNewEditor() instanceof FlowChartFileEditor
    //            ? (FlowChartFileEditor)event.getNewEditor() : null;
    //        processFileEditorChange(newEditor);
    //    }
    //}

}
