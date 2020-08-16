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
package com.alibaba.compileflow.idea.graph.nodeview.dialog;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.ui.treeStructure.Tree;
import net.miginfocom.swing.MigLayout;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This is dialog box for selecting methods of classes
 *
 * @author wuxiang
 * @since 2019-02-13
 */
public class PsiMethodDialog extends DialogWrapper {

    private Tree tree = new Tree();
    private JPanel panel = new JPanel(new MigLayout("inset 20"));
    private PsiClass psiClass;
    private PsiMethod selectMethod;

    public PsiMethodDialog(@Nullable Project project, @Nullable PsiClass psiClass) {
        super(project);
        this.psiClass = psiClass;
        setModal(true);
        init();
        panel.setOpaque(false);
    }

    @Override
    public void doOKAction() {
        super.doOKAction();
        enableMethod();
    }

    private void enableMethod() {
        DefaultMutableTreeNode selectedNode =
                (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        if (selectedNode != null) {
            String object = (String) selectedNode.getUserObject();

            for (PsiMethod psiMethod : getCanUseMethods(psiClass)) {
                String method = psiMethod.getName() + ":" + Arrays.toString(psiMethod.getParameters());
                if (method.equals(object)) {
                    selectMethod = psiMethod;
                    break;
                }
            }
        }
    }

    public PsiMethod getSelected() {
        return selectMethod;
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(psiClass.getName());
        DefaultTreeModel model = new DefaultTreeModel(root);
        int i = 0;
        for (PsiMethod psiMethod : getCanUseMethods(psiClass)) {
            // Display methods and parameters
            model.insertNodeInto(new DefaultMutableTreeNode(psiMethod.getName() + ":" + Arrays.toString(psiMethod.getParameters())), root, i);
            i++;
        }
        tree.setModel(model);
        panel.add(tree);
        return panel;
    }

    private PsiMethod[] getCanUseMethods(PsiClass psiClass) {
        PsiMethod[] all = psiClass.getAllMethods();
        if (all.length == 0) {
            return all;
        }

        Set<String> excludeSet = new HashSet<>();
        excludeSet.add("Object");
        excludeSet.add("registerNatives");
        excludeSet.add("getClass");
        excludeSet.add("hashCode");
        excludeSet.add("equals");
        excludeSet.add("clone");
        excludeSet.add("toString");
        excludeSet.add("notify");
        excludeSet.add("notifyAll");
        excludeSet.add("wait");
        excludeSet.add("finalize");

        List<PsiMethod> psiMethodList = new ArrayList<>();
        for (PsiMethod p : all) {
            if (excludeSet.contains(p.getName())) {
                continue;
            }
            psiMethodList.add(p);
        }

        PsiMethod[] result = new PsiMethod[psiMethodList.size()];
        psiMethodList.toArray(result);
        return result;
    }

}
