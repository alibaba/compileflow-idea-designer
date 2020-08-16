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

import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.DocumentAdapter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

/**
 * Combine JTextField and JComboBox
 *
 * @author xuan
 * @since 2019/2/28
 */
public class JComboxTextField extends JTextField {

    private JComboBox<String> comboBox;

    JComboxTextField(Set<String> itemSet, int columns) {
        super(columns);
        init(itemSet);
    }

    JComboxTextField(Set<String> itemSet) {
        init(itemSet);
    }

    private void init(Set<String> itemSet){
        comboBox = new ComboBox<>(1);
        if (null != itemSet && !itemSet.isEmpty()) {
            for (String item : itemSet) {
                comboBox.addItem(item);
            }
        }
        add(comboBox);

        //Click open
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                showSelect();
            }
        });

        //Input hiden
        this.getDocument().addDocumentListener(new DocumentAdapter() {
            @Override
            protected void textChanged(DocumentEvent documentEvent) {
                hideSelect();
            }

        });

        //Select event
        comboBox.addItemListener((e) -> {
            setText(e.getItem().toString());
            hideSelect();
        });
    }

    private void hideSelect() {
        comboBox.hidePopup();
        comboBox.setVisible(false);
    }

    private void showSelect() {
        comboBox.setVisible(true);
        SwingUtilities.invokeLater(() -> comboBox.showPopup());
    }

    public static void main(String[] args) {
        Set<String> itemSet = new HashSet<>();
        itemSet.add("111");
        itemSet.add("23");
        itemSet.add("2344");

        JComboxTextField combox = new JComboxTextField(itemSet);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.getContentPane().add(combox);
        frame.setVisible(true);
    }

}
