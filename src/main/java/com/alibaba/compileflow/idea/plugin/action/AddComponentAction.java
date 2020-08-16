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
package com.alibaba.compileflow.idea.plugin.action;

import com.alibaba.compileflow.idea.CompileFlow;
import com.alibaba.compileflow.idea.graph.util.FileUtil;

import com.alibaba.compileflow.idea.graph.util.ImageIconUtil;
import com.alibaba.compileflow.idea.plugin.provider.fileeditor.BpmFileType;

import com.intellij.CommonBundle;
import com.intellij.ide.actions.CreateElementActionBase;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import org.jetbrains.annotations.NotNull;

/**
 * Create new bpm file
 *
 * @author wuxiang
 * @author xuan
 */
public class AddComponentAction extends CreateElementActionBase {

    public AddComponentAction() {
        super("create new BPM File", "Create new BPM File", ImageIconUtil.LOGO_ICON);
    }

    @Override
    protected String getActionName(PsiDirectory directory, String newName) {
        return null;
    }

    protected String getDialogPrompt() {
        return "Enter name for new BPM File";
    }

    protected String getDialogTitle() {
        return "New BPM File";
    }

    @Override
    protected String getCommandName() {
        return "Create BPM File";
    }

    @Override
    protected boolean isAvailable(DataContext dataContext) {
        return true;
    }

    @Override
    public void update(AnActionEvent event) {
        DataContext dataContext = event.getDataContext();
        Presentation presentation = event.getPresentation();
        boolean enabled = this.isAvailable(dataContext);
        presentation.setVisible(enabled);
        presentation.setEnabled(enabled);
    }

    @NotNull
    private PsiElement[] doCreate(String newName, PsiDirectory directory) throws Exception {
        //code format like: aaa.bbb.ccc
        // aaa.bbb is packageName. ccc is fileName
        String code;
        String path = directory.getVirtualFile().getPath();
        if (path.endsWith("resources")) {
            code = newName;
        } else {
            String packageName = path.replaceAll("/", ".");
            packageName = packageName.substring(packageName.indexOf("resources") + "resources".length() + 1);
            code = packageName + "." + newName;
        }

        PsiFile file = createFromTemplate(directory, newName, code);
        PsiElement child = null;
        if (file.getChildren().length > 0) {
            child = file.getLastChild();
        }

        return child != null ? new PsiElement[] {child, file} : new PsiElement[] {file};
    }

    private static PsiFile createFromTemplate(PsiDirectory directory, String className, String code) {
        String text = FileUtil.getInitBpmText(code);
        String fileName = className + ".bpm";
        PsiFile f = PsiFileFactory.getInstance(directory.getProject()).createFileFromText(fileName,
            BpmFileType.INSTANCE, text);
        return (PsiFile)directory.add(f);
    }

    @NotNull
    @Override
    protected final PsiElement[] invokeDialog(Project project, PsiDirectory directory) {
        MyInputValidator validator = new MyInputValidator(project, directory);
        Messages.showInputDialog(project, this.getDialogPrompt(), this.getDialogTitle(), Messages.getQuestionIcon(), "",
            validator);
        return validator.getCreatedElements();
    }

    @NotNull
    @Override
    protected PsiElement[] create(String newName, PsiDirectory directory) throws Exception {
        return this.doCreate(newName, directory);
    }

    @Override
    protected String getErrorTitle() {
        return CommonBundle.getErrorTitle();
    }

}
