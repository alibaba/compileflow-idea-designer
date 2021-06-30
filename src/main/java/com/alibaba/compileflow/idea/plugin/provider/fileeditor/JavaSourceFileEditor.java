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

import java.beans.PropertyChangeListener;

import javax.swing.*;

import com.alibaba.compileflow.idea.graph.codec.ModelConvertFactory;
import com.alibaba.compileflow.idea.graph.model.BpmModel;
import com.alibaba.compileflow.idea.graph.model.checker.ModelCheckerMgr;
import com.alibaba.compileflow.idea.plugin.lang.Lang;

import com.google.gson.Gson;
import com.intellij.codeHighlighting.BackgroundEditorHighlighter;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.ide.structureView.StructureViewBuilder;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorLocation;
import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.fileEditor.FileEditorState;
import com.intellij.openapi.fileEditor.FileEditorStateLevel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.UserDataHolderBase;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.xml.XmlFile;
import com.intellij.util.messages.MessageBus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * show the javacode
 *
 * @author xuan
 * @since 2021-06-22
 */
public class JavaSourceFileEditor extends UserDataHolderBase implements FileEditor {
    private static final String LINE_BREAK = "\n";

    private static final Logger logger = Logger.getInstance(JavaSourceFileEditor.class);
    private Project project;
    private Editor editor;
    private XmlFile xmlFile;
    private Document document;

    public JavaSourceFileEditor(final Project project, XmlFile xmlFile) {
        this.project = project;
        this.xmlFile = xmlFile;
        this.document = EditorFactory.getInstance().createDocument("");
        this.editor = EditorFactory.getInstance().createEditor(document, project, JavaFileType.INSTANCE, true);

        ApplicationManager.getApplication().invokeLater(this::loadJavaCode);
        // Monitor file changes
        MessageBus messageBus = project.getMessageBus();
        messageBus.connect().subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, new FileEditorManagerListener
            () {
            @Override
            public void selectionChanged(@NotNull FileEditorManagerEvent event) {
                ApplicationManager.getApplication().invokeLater(() -> loadJavaCode());
            }
        });
    }

    private void loadJavaCode() {
        String javaCode;
        BpmModel bpmModel = null;
        try {
            // Empty file
            if (xmlFile.getText().length() == 0) {
                javaCode = "// Please draw bpm flow first, thanks!!!";
            } else {
                bpmModel = ModelConvertFactory.getModelXmlConvertExt(xmlFile.getVirtualFile().getExtension()).toModel(
                    xmlFile.getText());
                javaCode = ModelConvertFactory.getModelCodeConvertExt(xmlFile.getVirtualFile().getExtension())
                    .getJavaCode(bpmModel, xmlFile.getText());
            }
        } catch (Throwable e) {
            logger.error(e);

            javaCode = "//Bpm file is illegal. Message:";
            javaCode += LINE_BREAK;
            javaCode += e.getMessage();
            javaCode += LINE_BREAK;

            javaCode += "//Bpm file is illegal. Throwable:";
            javaCode += LINE_BREAK;
            javaCode += new Gson().toJson(e);
            javaCode += LINE_BREAK;

            javaCode += "//Ops... I found some problems. Please confirm:";
            javaCode += LINE_BREAK;
            javaCode += ModelCheckerMgr.check(bpmModel);
        }

        if (null == javaCode) {
            javaCode = "Just not support!!!";
        }

        refreshText(javaCode);
    }

    private void refreshText(String text) {
        ApplicationManager.getApplication().runWriteAction(
            () -> document.setText(text));
    }

    @NotNull
    @Override
    public JComponent getComponent() {
        return editor.getComponent();
    }

    @Nullable
    @Override
    public JComponent getPreferredFocusedComponent() {
        return editor.getComponent();
    }

    @NotNull
    @Override
    public String getName() {
        return Lang.getString("editor.java.code");
    }

    @NotNull
    @Override
    public FileEditorState getState(@NotNull FileEditorStateLevel fileEditorStateLevel) {
        return ((o, l) -> false);
    }

    @Override
    public void setState(@NotNull FileEditorState fileEditorState) {

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

    @Nullable
    @Override
    public StructureViewBuilder getStructureViewBuilder() {
        return null;
    }

    @Override
    public void dispose() {
    }

    @Nullable
    @Override
    public <T> T getUserData(@NotNull Key<T> key) {
        return null;
    }

    @Override
    public <T> void putUserData(@NotNull Key<T> key, @Nullable T t) {

    }

    @Nullable
    @Override
    public VirtualFile getFile() {
        return xmlFile.getVirtualFile();
    }

}
