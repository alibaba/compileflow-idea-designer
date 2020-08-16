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
package com.alibaba.compileflow.idea.graph.palette;

import com.alibaba.compileflow.idea.graph.mxgraph.Cell;
import com.alibaba.compileflow.idea.graph.styles.ShadowBorder;
import com.alibaba.compileflow.idea.graph.palette.EditorPaletteModel.Template;
import com.alibaba.compileflow.idea.graph.util.ImageIconUtil;

import com.intellij.openapi.diagnostic.Logger;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.swing.util.mxGraphTransferable;
import com.mxgraph.swing.util.mxSwingConstants;
import com.mxgraph.util.*;

import javax.swing.*;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * @author wuxiang
 * @author xuan
 * @since 2019/5/9
 */
public class EditorPalette extends JPanel {

    private static final Logger logger = Logger.getInstance(EditorPalette.class);

    private JLabel selectedEntry = null;

    public EditorPalette() {

        setLayout(new FlowLayout(FlowLayout.LEADING, 5, 5));
        try {
            mxGraphTransferable.dataFlavor = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType
                + "; class=com.mxgraph.swing.util.mxGraphTransferable", null,
                new com.mxgraph.swing.util.mxGraphTransferable(null, null).getClass().getClassLoader());
        } catch (ClassNotFoundException e) {
            logger.error("classNotFoundException", e);
        }

        // Shows a nice icon for drag and drop but doesn't import anything
        setTransferHandler(new TransferHandler() {
            @Override
            public boolean canImport(JComponent comp, DataFlavor[] flavors) {
                return true;
            }
        });
    }

    public void setPreferredWidth(int width) {
        int cols = Math.max(1, width / 55);
        setPreferredSize(new Dimension(width,
            (getComponentCount() * 55 / cols) + 30));
        revalidate();
    }

    public void setModel(EditorPaletteModel model) {
        List<Template> templateList = model.getData();
        for (Template template : templateList) {
            addTemplate(template);
        }
    }

    private void addTemplate(Template template) {

        Cell cell = new Cell(template.value, new mxGeometry(0, 0, template.width, template.height),
            template.style);
        cell.setVertex(true);

        mxRectangle bounds = (mxGeometry)cell.getGeometry().clone();
        mxGraphTransferable transferable = new mxGraphTransferable(
            new Object[] {cell}, bounds);

        // Scales the image if it's too large for the library
        Icon icon = ImageIconUtil.buildPaletteIcon(template.iconUrl);

        final JLabel entry = new JLabel(icon);
        entry.setPreferredSize(new Dimension(50, 50));
        entry.setBackground(this.getBackground().brighter());
        entry.setFont(new Font(entry.getFont().getFamily(), Font.PLAIN, 12));
        entry.setVerticalTextPosition(JLabel.BOTTOM);
        entry.setHorizontalTextPosition(JLabel.CENTER);
        entry.setIconTextGap(5);
        entry.setToolTipText(template.name);
        entry.setText(template.name);
        entry.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setSelectionEntry(entry);
            }
        });

        // Install the handler for dragging nodes into a graph
        DragGestureListener dragGestureListener = (e) -> e
            .startDrag(null, mxSwingConstants.EMPTY_IMAGE, new Point(),
                transferable, null);
        DragSource dragSource = new DragSource();
        dragSource.createDefaultDragGestureRecognizer(entry,
            DnDConstants.ACTION_COPY, dragGestureListener);

        add(entry);
    }

    private void setSelectionEntry(JLabel entry) {

        JLabel previous = selectedEntry;
        selectedEntry = entry;

        if (previous != null) {
            previous.setBorder(null);
            previous.setOpaque(false);
        }

        if (selectedEntry != null) {
            selectedEntry.setBorder(ShadowBorder.getSharedInstance());
            selectedEntry.setOpaque(true);
        }
    }

}
