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
package com.alibaba.compileflow.idea.graph.nodeview.component;

import com.alibaba.compileflow.idea.graph.model.ActionModel;
import com.alibaba.compileflow.idea.graph.model.ActionHandleModel;
import com.alibaba.compileflow.idea.graph.model.BpmModel;
import com.alibaba.compileflow.idea.graph.model.JavaActionHandleModel;
import com.alibaba.compileflow.idea.graph.model.SpringBeanActionHandleModel;
import com.alibaba.compileflow.idea.graph.model.VarModel;
import com.alibaba.compileflow.idea.graph.nodeview.dialog.PsiMethodDialog;
import com.alibaba.compileflow.idea.graph.mxgraph.Graph;
import com.alibaba.compileflow.idea.graph.util.*;
import com.alibaba.compileflow.idea.plugin.LanguageConstants;

import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.table.JBTable;
import net.miginfocom.swing.MigLayout;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

/**
 * AutoTask or Decision params setting view
 *
 * @author xuan
 * @since 2019/2/25
 */
public class ActionPanel extends JPanel {

    /**
     * type（like：spring-bean, java）
     */
    private JLabel typeLabel = new JLabel("Type:");
    private ComboBox<String> typeComboBox = new ComboBox<>(
        new String[] {ActionModel.SPRING_BEAN, ActionModel.JAVA_BEAN});
    /**
     * beanName（when type=spring-bean）
     */
    private JLabel beanLabel = new JLabel("Bean:");
    private JTextField beanField = new JTextField(15);
    /**
     * classType
     */
    private JLabel classTypeLabel = new JLabel("classType:");
    private JTextField classTypeField = new JTextField(30);
    private JButton classTypeSelectedBtn = new JButton("Browse");
    /**
     * method
     */
    private JLabel methodLabel = new JLabel("Method:");
    private JTextField methodField = new JTextField(30);
    private JButton methodSelectedBtn = new JButton("Browse");
    /**
     * source button
     */
    private JButton sourceButton = new JButton("Jump to Source");
    /**
     * show paramters
     */
    private JLabel tableLabel = new JLabel("Method params:");
    private String[] columnNames = new String[] {"name", "dataType", "defaultValue", "inOutType", "description",
        "contextVarName"};
    private DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
    private JTable table = new JBTable(tableModel);
    private JScrollPane scrollTable = new JBScrollPane() {
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(600, 300);
        }
    };

    private Project project;

    /**
     * want to listener jump to source action
     */
    private Callback<String, String> jumpToSourceActionCallback;

    public ActionPanel(Project project) {
        super(new MigLayout("inset 20"));
        this.project = project;
        initView();
        initListener();
    }

    private void initView() {
        //type
        this.add(typeLabel, "gap para");
        typeComboBox.setFocusable(true);
        typeComboBox.setEditable(true);
        this.add(typeComboBox, "gap para");
        //beanName
        this.add(beanLabel, "gap para");
        this.add(beanField, "wrap");
        //classType
        this.add(classTypeLabel, "gap para");
        this.add(classTypeField, "gap para");
        this.add(classTypeSelectedBtn, "span, growx, wrap");
        //method
        this.add(methodLabel, "gap para");
        this.add(methodField, "gap para");
        this.add(methodSelectedBtn, "span, growx, wrap");
        //source button
        this.add(sourceButton, "gap para");
        //paramters
        scrollTable.setViewportView(table);
        table.setRowHeight(30);
        this.add(tableLabel, "wrap");
        this.add(scrollTable, "span, growx, wrap 8");
        // Set inoutType to Select
        TableColumn inoutTypeColum = table.getColumnModel().getColumn(3);
        JComboBox<String> inoutTypeBox = new ComboBox<>();
        inoutTypeBox.addItem("param");
        inoutTypeBox.addItem("return");
        inoutTypeBox.addItem("inner");
        inoutTypeColum.setCellEditor(new DefaultCellEditor(inoutTypeBox));
    }

    public void initContextVarNameToComboBox(Set<String> itemSet) {
        if (null == itemSet || itemSet.isEmpty()) {
            return;
        }
        //For contextVarName to select
        TableColumn contextVarNameColum = table.getColumnModel().getColumn(5);
        DefaultCellEditor defaultCellEditor = new DefaultCellEditor(new JComboxTextField(itemSet));
        defaultCellEditor.setClickCountToStart(1);
        contextVarNameColum.setCellEditor(defaultCellEditor);
    }

    private void initListener() {
        //Click jump to source
        sourceButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (classTypeField.getText() != null) {
                    GlobalSearchScope scope = GlobalSearchScope.allScope(project);
                    PsiClass psiClass = JavaPsiFacade.getInstance(project).findClass(classTypeField.getText(), scope);

                    if (psiClass != null) {
                        //Open the file containing the class
                        VirtualFile vf = psiClass.getContainingFile().getVirtualFile();
                        //Jump there
                        new OpenFileDescriptor(project, vf, 1, 0).navigateInEditor(project, false);
                        if (null != jumpToSourceActionCallback) {
                            jumpToSourceActionCallback.call(null);
                        }
                        return;
                    }
                }

                //fail to jump source
                DialogUtil.alert(LanguageConstants.JUMP_SOURCE_TIPS);
                super.mousePressed(e);
            }
        });

        //Select class to field
        classTypeSelectedBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                String selectedClassName = DialogUtil.selectClassName(project);
                if (null != selectedClassName) {
                    classTypeField.setText(selectedClassName);
                }
                super.mousePressed(e);
            }
        });

        //Click to get method from PsiClass. Then will auto get parameters.
        methodSelectedBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (classTypeField.getText() != null) {
                    GlobalSearchScope scope = GlobalSearchScope.allScope(project);
                    PsiClass psiClass = JavaPsiFacade.getInstance(project).findClass(classTypeField.getText(), scope);
                    if (psiClass != null) {
                        //Show all methods
                        PsiMethodDialog dialog = new PsiMethodDialog(project, psiClass);
                        dialog.show();
                        PsiMethod psiMethod = dialog.getSelected();
                        if (psiMethod != null) {
                            methodField.setText(psiMethod.getName());
                            //Reset table
                            for (int i = 0, n = tableModel.getRowCount(); i < n; i++) {
                                tableModel.removeRow(0);
                            }
                            //Set new data
                            for (PsiParameter psiParameter : psiMethod.getParameterList().getParameters()) {
                                //"name", "dataType", "defaultValue", "inOutType", "description", "contextVarName"
                                Vector<String> rowData = new Vector<>();
                                rowData.add(psiParameter.getName());
                                rowData.add(psiParameter.getType().getCanonicalText());
                                rowData.add("");
                                rowData.add("param");
                                rowData.add("");
                                rowData.add("");
                                tableModel.addRow(rowData);
                            }

                            if (!PsiType.VOID.equals(psiMethod.getReturnType())) {
                                //"name", "dataType", "defaultValue", "inOutType", "description", "contextVarName"
                                Vector<String> rowData = new Vector<>();
                                if (null != psiMethod.getReturnType()) {
                                    rowData.add(psiMethod.getReturnType().getCanonicalText());
                                    rowData.add(psiMethod.getReturnType().getCanonicalText());
                                }
                                rowData.add("");
                                rowData.add("return");
                                rowData.add("");
                                if (null != psiMethod.getReturnType()) {
                                    rowData.add(psiMethod.getReturnType().getPresentableText());
                                }
                                tableModel.addRow(rowData);
                            }
                        }
                    }
                }

                super.mousePressed(e);
            }
        });

        //Determine whether to visible bean input view based on type changed
        typeComboBox.addItemListener((e) -> {
            if (ActionModel.SPRING_BEAN.equals(typeComboBox.getSelectedItem())) {
                beanField.setVisible(true);
                beanLabel.setVisible(true);
            } else {
                beanField.setVisible(false);
                beanLabel.setVisible(false);
            }
        });
    }

    public ComboBox getTypeComboBox() {
        return typeComboBox;
    }

    public JTextField getBeanField() {
        return beanField;
    }

    public JTextField getClassTypeField() {
        return classTypeField;
    }

    public JTextField getMethodField() {
        return methodField;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public void setJumpToSourceActionCallback(Callback<String, String> jumpToSourceActionCallback) {
        this.jumpToSourceActionCallback = jumpToSourceActionCallback;
    }

    ///////////////// Utils //////////////////////////

    public static void initContextVarNameComboBox(JPanel panel, Graph graph) {
        ActionPanel actionSettingPanel = (ActionPanel)panel;
        BpmModel bpmModel = graph.getModel().getBpmModel();
        actionSettingPanel.initContextVarNameToComboBox(bpmModel.getContextVarNameSet());
    }

    public static void data2View(JPanel panel, ActionModel action) {
        ActionPanel actionSettingPanel = (ActionPanel)panel;

        if (null == action) {
            return;
        }
        ActionHandleModel actionHandle = action.getActionHandle();
        if (null == actionHandle) {
            return;
        }
        actionSettingPanel.getTypeComboBox().setSelectedItem(action.getType());

        if (actionHandle instanceof SpringBeanActionHandleModel) {
            SpringBeanActionHandleModel springBeanActionHandleModel = (SpringBeanActionHandleModel)actionHandle;

            String beanName = springBeanActionHandleModel.getBean();
            actionSettingPanel.getClassTypeField().setText(springBeanActionHandleModel.getClazz());
            actionSettingPanel.getMethodField().setText(springBeanActionHandleModel.getMethod());
            if (null != beanName) {
                actionSettingPanel.getBeanField().setText(beanName);
            }
        } else if (actionHandle instanceof JavaActionHandleModel) {
            JavaActionHandleModel javaActionHandleModel = (JavaActionHandleModel)actionHandle;

            actionSettingPanel.getClassTypeField().setText(javaActionHandleModel.getClazz());
            actionSettingPanel.getMethodField().setText(javaActionHandleModel.getMethod());
        }

        //method params
        List<VarModel> varList = actionHandle.getVars();
        if (null != varList && varList.size() > 0) {
            for (VarModel var : varList) {
                //"name", "dataType", "defaultValue", "inOutType", "description", "contextVarName"
                Vector<String> vector = new Vector<>();
                vector.add(var.getName());
                vector.add(var.getDataType());
                vector.add(var.getDefaultValue());
                vector.add(var.getInOutType());
                vector.add(var.getDescription());
                vector.add(var.getContextVarName());
                actionSettingPanel.getTableModel().addRow(vector);
            }
        }
    }

    public static void view2Data(JPanel panel, ActionModel actionModel) {
        ActionPanel actionSettingPanel = (ActionPanel)panel;

        // Default spring-bean
        String actionType = ActionModel.SPRING_BEAN;
        if (null != actionModel) {
            if (actionSettingPanel.getTypeComboBox().getSelectedItem() != null) {
                actionType = actionSettingPanel.getTypeComboBox().getSelectedItem().toString();
            }
        } else {
            actionModel = ActionModel.of();
        }
        actionModel.setType(actionType);

        if (ActionModel.SPRING_BEAN.equals(actionType)) {
            actionModel.setActionHandle(SpringBeanActionHandleModel.of());
        } else if (ActionModel.JAVA_BEAN.equals(actionType)) {
            actionModel.setActionHandle(JavaActionHandleModel.of());
        }
        // To set bean info from paramPanel
        ActionHandleModel actionHandle = actionModel.getActionHandle();
        if (actionHandle instanceof SpringBeanActionHandleModel) {
            ((SpringBeanActionHandleModel)actionHandle).setBean(actionSettingPanel.getBeanField().getText());
            ((SpringBeanActionHandleModel)actionHandle).setClazz(actionSettingPanel.getClassTypeField().getText());
            ((SpringBeanActionHandleModel)actionHandle).setMethod(actionSettingPanel.getMethodField().getText());

        } else if (actionHandle instanceof JavaActionHandleModel) {
            ((JavaActionHandleModel)actionHandle).setClazz(actionSettingPanel.getClassTypeField().getText());
            ((JavaActionHandleModel)actionHandle).setMethod(actionSettingPanel.getMethodField().getText());
        }

        // To set method vars from paramPanel
        List<VarModel> varList = new ArrayList<>();
        for (int i = 0, n = actionSettingPanel.getTableModel().getRowCount(); i < n; i++) {
            String name = (String)actionSettingPanel.getTableModel().getValueAt(i, 0);
            String dataType = (String)actionSettingPanel.getTableModel().getValueAt(i, 1);
            String defaultValue = (String)actionSettingPanel.getTableModel().getValueAt(i, 2);
            String inOutType = (String)actionSettingPanel.getTableModel().getValueAt(i, 3);
            String description = (String)actionSettingPanel.getTableModel().getValueAt(i, 4);
            String contextVarName = (String)actionSettingPanel.getTableModel().getValueAt(i, 5);
            if (StringUtils.isBlank(name) || StringUtils.isBlank(dataType)) {
                continue;
            }
            VarModel var = VarModel.of();
            var.setName(name);
            var.setDataType(dataType);
            var.setDefaultValue(defaultValue);
            var.setInOutType(inOutType);
            var.setDescription(description);
            var.setContextVarName(contextVarName);
            varList.add(var);
        }
        actionHandle.setVars(varList);
    }

    public static boolean isActionSettingPanelEmpty(JPanel panel) {
        ActionPanel actionSettingPanel = (ActionPanel)panel;
        return StringUtil.isEmpty(actionSettingPanel.getBeanField().getText()) && StringUtil.isEmpty(
            actionSettingPanel.getClassTypeField().getText()) && StringUtil.isEmpty(
            actionSettingPanel.getMethodField().getText());
    }

    public static void main(String[] args) {
        ActionPanel panel = new ActionPanel(null);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

}
