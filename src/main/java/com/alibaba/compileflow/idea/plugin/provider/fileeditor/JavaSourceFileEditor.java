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

import com.alibaba.compileflow.idea.graph.model.checker.ModelCheckerMgr;
import com.alibaba.compileflow.idea.plugin.LanguageConstants;
import com.alibaba.compileflow.idea.graph.codec.ModelConvertFactory;
import com.alibaba.compileflow.idea.graph.util.FileUtil;
import com.alibaba.compileflow.idea.graph.model.BpmModel;

import com.google.gson.Gson;
import com.intellij.codeHighlighting.BackgroundEditorHighlighter;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.ide.structureView.StructureViewBuilder;
import com.intellij.ide.ui.UISettings;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileEditor.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.UserDataHolderBase;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.xml.XmlFile;
import com.intellij.util.messages.MessageBus;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.UIUtil;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.io.IOException;

/**
 *
 * JavaSourceFileEditor
 *
 * @author wuxiang
 * @author xuan
 * @since 2016-03-21
 */
public class JavaSourceFileEditor extends UserDataHolderBase implements FileEditor {

    private static final Logger logger = Logger.getInstance(JavaSourceFileEditor.class);
    private Project project;
    private JPanel mainPanel;
    private com.intellij.openapi.editor.Document document;
    private static final String UI_THEME = "Darcula";
    private static final String RSYNTAXTEXTAREA_THEME = "/org/fife/ui/rsyntaxtextarea/themes/dark.xml";

    public JavaSourceFileEditor(final Project project, XmlFile file) {

        this.project = project;
        this.document = FileDocumentManager.getInstance().getDocument(file.getVirtualFile());
        this.mainPanel = new JPanel(new BorderLayout());
        this.mainPanel.setBorder(JBUI.Borders.empty(10, 30, 10, 10));
        mainPanel.setBackground(UIUtil.getTreeBackground());

        RSyntaxTextArea textArea = new RSyntaxTextArea(20, 60);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        textArea.setCodeFoldingEnabled(true);
        textArea.setUseFocusableTips(true);
        textArea.requestFocusInWindow();
        textArea.setMarkOccurrences(true);
        textArea.setAutoscrolls(true);
        textArea.setAutoIndentEnabled(true);
        textArea.setUseSelectedTextColor(true);
        textArea.setEditable(true);
        // Different styles of presentation, different, to close the editor in order to take effect
        if (UIManager.getLookAndFeel().getName().contains(UI_THEME)) {
            try {
                Theme theme = Theme.load(getClass().getResourceAsStream(
                    RSYNTAXTEXTAREA_THEME));
                theme.apply(textArea);
            } catch (IOException ioe) { // Never happens
                logger.error("load theme error", ioe);
            }
        } else {
            try {
                Theme theme = Theme.load(getClass().getResourceAsStream(
                    "/org/fife/ui/rsyntaxtextarea/themes/default.xml"));
                theme.apply(textArea);
            } catch (IOException ioe) { // Never happens
                logger.error("load theme error", ioe);
            }
        }

        // 从IDE 字体中直接获取
        UISettings settingsManager = UISettings.getInstance();
        int fontSize = settingsManager.getFontSize();
        Font font = new Font("JetBrains Mono NL", Font.PLAIN, fontSize+1);
        textArea.setFont(font);
        RTextScrollPane sp = new RTextScrollPane(textArea);
        mainPanel.add(sp);

        ApplicationManager.getApplication().invokeLater(() -> loadJavaCode(project, file, textArea));
        // Monitor file changes
        MessageBus messageBus = project.getMessageBus();
        messageBus.connect().subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, new FileEditorManagerListener() {

            @Override
            public void selectionChanged(@NotNull FileEditorManagerEvent event) {
                ApplicationManager.getApplication().invokeLater(() -> loadJavaCode(project, file, textArea));
            }
        });
    }

    private void loadJavaCode(Project project, XmlFile file, RSyntaxTextArea textArea) {

        String javaCode;
        BpmModel bpmModel = null;
        String bpmCode = "default test";
        try {
            // Empty file
            if (file.getText().length() == 0) {
                javaCode = "// Please draw bpm flow first, thanks!!!";
            } else {
                bpmModel = ModelConvertFactory.getModelXmlConvertExt(file.getVirtualFile().getExtension()).toModel(
                    file.getText());
                javaCode = ModelConvertFactory.getModelCodeConvertExt(file.getVirtualFile().getExtension()).getJavaCode(
                    bpmModel);
                bpmCode = bpmModel.getCode();
            }
        } catch (Throwable e) {
            logger.error(e);
            javaCode = "//Bpm file is illegal. Case:";
            javaCode += "\n";
            javaCode += "//" + e.getMessage();

            javaCode += "\n";
            Gson gson = new Gson();
            javaCode += "//" + gson.toJson(e);

            javaCode += "\n";
            javaCode += "\n";

            javaCode += "//Ops... I found some problems. Please confirm:";
            javaCode += "\n";
            javaCode += ModelCheckerMgr.check(bpmModel);
        }

        if (null == javaCode) {
            javaCode = "//Just not support!!!";
        }

        PsiFile f = PsiFileFactory.getInstance(project).createFileFromText(bpmCode, JavaFileType.INSTANCE, javaCode);
        textArea.setText(f.getViewProvider().getContents().toString());
    }

    @NotNull
    @Override
    public JComponent getComponent() {
        return mainPanel;
    }

    @Nullable
    @Override
    public JComponent getPreferredFocusedComponent() {
        return mainPanel;
    }

    @NotNull
    @Override
    public String getName() {
        return LanguageConstants.TAB_CODE_NAME;
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
        if (PsiDocumentManager.getInstance(project).isUncommited(document)) {
            return true;
        }
        return FileDocumentManager.getInstance().isDocumentUnsaved(document);
    }

    @Override
    public boolean isValid() {
        return FileUtil.isFileValid(project, document);
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
}
