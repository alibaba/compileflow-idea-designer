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
package com.alibaba.compileflow.idea.graph.toolbar.action;

import com.alibaba.compileflow.idea.CompileFlow;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.ui.JBColor;

import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * @author xuan
 */
public class VersionActionDialog extends JDialog {

    private static final Logger logger = Logger.getInstance(VersionActionDialog.class);

    public VersionActionDialog(Frame owner) {
        super(owner);
        setTitle("About");
        setLayout(new BorderLayout());

        // Creates the gradient panel
        JPanel panel = new JPanel(new BorderLayout()) {

            private static final long serialVersionUID = -5062895855016210947L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Paint gradient background
                Graphics2D g2d = (Graphics2D)g;
                g2d.setPaint(new GradientPaint(0, 0, JBColor.WHITE, getWidth(),
                    0, getBackground()));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }

        };

        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory
            .createMatteBorder(0, 0, 1, 0, JBColor.GRAY), BorderFactory
            .createEmptyBorder(8, 8, 12, 8)));

        // Adds title
        JLabel titleLabel = new JLabel("About");
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));
        titleLabel.setOpaque(false);
        panel.add(titleLabel, BorderLayout.NORTH);

        // Adds optional subtitle
        JLabel subtitleLabel = new JLabel(
            "For more information visit " + CompileFlow.HOME_PAGE);
        subtitleLabel.setBorder(BorderFactory.createEmptyBorder(4, 18, 0, 0));
        subtitleLabel.setOpaque(false);
        panel.add(subtitleLabel, BorderLayout.CENTER);

        getContentPane().add(panel, BorderLayout.NORTH);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        content.add(new JLabel("Compileflow idea designer - The best bpm idea designer plugin"));
        content.add(new JLabel(" "));

        content.add(new JLabel("Compileflow Version " + CompileFlow.VERSION));
        content.add(new JLabel("Copyright (C) 2019 by Alibaba Ltd."));
        content.add(new JLabel("All rights reserved."));
        content.add(new JLabel(" "));

        try {
            content.add(new JLabel("Operating System Name: "
                + System.getProperty("os.name")));
            content.add(new JLabel("Operating System Version: "
                + System.getProperty("os.version")));
            content.add(new JLabel(" "));

            content.add(new JLabel("Java Vendor: "
                + System.getProperty("java.vendor", "undefined")));
            content.add(new JLabel("Java Version: "
                + System.getProperty("java.version", "undefined")));
            content.add(new JLabel(" "));

            content.add(new JLabel("Total Memory: "
                + Runtime.getRuntime().totalMemory()));
            content.add(new JLabel("Free Memory: "
                + Runtime.getRuntime().freeMemory()));
        } catch (Exception e) {
            logger.error("about frame error", e);
        }

        getContentPane().add(content, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory
            .createMatteBorder(1, 0, 0, 0, JBColor.GRAY), BorderFactory
            .createEmptyBorder(16, 8, 8, 8)));
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Adds OK button to close window
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener((e) -> setVisible(false));

        buttonPanel.add(closeButton);

        // Sets default button for enter key
        getRootPane().setDefaultButton(closeButton);

        setResizable(false);
        setSize(500, 400);
    }

    @Override
    protected JRootPane createRootPane() {
        KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        JRootPane rootPane = new JRootPane();

        rootPane.registerKeyboardAction((actionEvent) -> setVisible(false), stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);

        rootPane.registerKeyboardAction((e) -> setVisible(false), stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
        return rootPane;
    }

    public static void main(String[] args) {
        VersionActionDialog versionActionDialog = new VersionActionDialog(null);
        versionActionDialog.setVisible(true);
    }

}
