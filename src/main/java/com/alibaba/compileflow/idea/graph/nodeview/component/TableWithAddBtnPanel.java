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

import com.alibaba.compileflow.idea.graph.util.DialogUtil;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.table.JBTable;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * The panel combine table and button. For bpm vars view and set.
 *
 * @author xuan
 * @since 2019/2/20
 */
public class TableWithAddBtnPanel extends JPanel {

    /**
     * The table scroll container
     */
    private JScrollPane scrollPane = new JBScrollPane() {
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(600, 300);
        }
    };

    /**
     * Add and delete button for table row.
     */
    private JButton addRowBtn = new JButton("add row");
    private JButton deleteRowBtn = new JButton("del row");

    /**
     * Table and data model
     */
    private String[] columnNames = new String[] {"name", "dataType", "defaultValue", "inOutType", "description",
        "contextVarName"};
    private DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
    private JTable table = new JBTable(tableModel);

    private Project project;

    public TableWithAddBtnPanel(Project project) {
        super(new MigLayout("inset 20"));
        this.project = project;
        initView();
        initListener();
    }

    /**
     * insert row data to table
     *
     * @param row  rowNum from 0
     * @param data data
     */
    public void insertData(int row, Data data) {
        tableModel.insertRow(row,
            new String[] {data.name, data.dataType, data.defaultValue, data.inOutType, data.description,
                data.contextVarName});
    }

    /**
     * Get all data from table
     *
     * @return dataList
     */
    public List<Data> getDataList() {
        List<Data> dataList = new ArrayList<>();
        for (int i = 0; i < table.getRowCount(); i++) {
            Data data = new Data();
            data.name = (String)table.getModel().getValueAt(i, 0);
            data.dataType = (String)table.getModel().getValueAt(i, 1);
            data.defaultValue = (String)table.getModel().getValueAt(i, 2);
            data.inOutType = (String)table.getModel().getValueAt(i, 3);
            data.description = (String)table.getModel().getValueAt(i, 4);
            data.contextVarName = (String)table.getModel().getValueAt(i, 5);
            dataList.add(data);
        }
        return dataList;
    }

    private void initView() {
        // add table to scroll
        scrollPane.setViewportView(table);
        table.setRowHeight(30);

        //add scroll and button to panel
        this.add(scrollPane, "wrap");
        this.add(addRowBtn, "gap para");
        this.add(deleteRowBtn, "span, growx, wrap");

        // set inoutType for select
        TableColumn inoutTypeColum = table.getColumnModel().getColumn(3);
        JComboBox<String> comboBox = new ComboBox<>();
        comboBox.addItem("param");
        comboBox.addItem("return");
        comboBox.addItem("inner");
        inoutTypeColum.setCellEditor(new DefaultCellEditor(comboBox));
    }

    public void initContextVarNameToComboBox(Set<String> itemSet) {
        if (null == itemSet || itemSet.size() == 0) {
            return;
        }
        TableColumn contextVarNameColum = table.getColumnModel().getColumn(5);
        DefaultCellEditor defaultCellEditor = new DefaultCellEditor(new JComboxTextField(itemSet));
        defaultCellEditor.setClickCountToStart(1);
        contextVarNameColum.setCellEditor(defaultCellEditor);
    }

    private void initListener() {
        // add button listener
        addRowBtn.addActionListener((e) -> {
            tableModel.addRow(new String[] {"", "", "", "", "", ""});
            tableModel.fireTableDataChanged();
        });
        //delete button listener
        deleteRowBtn.addActionListener((e) -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                tableModel.removeRow(row);
                tableModel.fireTableDataChanged();
            }
        });
        //table listener
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                int colum = table.getSelectedColumn();
                if (1 == colum) {
                    //for dataType select
                    String selectedClassName = DialogUtil.selectClassName(project);
                    if (null != selectedClassName) {
                        tableModel.setValueAt(selectedClassName, row, colum);
                    }
                }
            }
        });
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public static class Data {
        public String name;
        public String dataType;
        public String defaultValue;
        public String inOutType;
        public String description;
        public String contextVarName;
    }

    public static void main(String[] args) {
        TableWithAddBtnPanel panel = new TableWithAddBtnPanel(null);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.getContentPane().add(panel);
        frame.setVisible(true);
        //
        for (int i = 0; i < 2; i++) {
            Data data = new Data();
            data.name = "name" + i;
            data.dataType = "d";
            data.defaultValue = "d2";
            data.inOutType = "i";
            data.description = "d3";
            data.contextVarName = "c";
            panel.insertData(i, data);
        }
    }

}
