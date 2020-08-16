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
package com.alibaba.compileflow.idea.graph.styles;

import javax.swing.border.Border;

import java.awt.*;
import java.io.Serializable;

/**
 * @author xuan
 */
public class ShadowBorder implements Border, Serializable {
    private static final long serialVersionUID = 6854989457150641240L;

    private Insets insets;

    public static final ShadowBorder INSTANCE = new ShadowBorder();

    private ShadowBorder() {
        insets = new Insets(0, 0, 2, 2);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return insets;
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
        // choose which colors we want to use
        Color bg = c.getBackground();

        if (c.getParent() != null) {
            bg = c.getParent().getBackground();
        }

        if (bg != null) {
            Color mid = bg.darker();
            Color edge = average(mid, bg);

            g.setColor(bg);
            g.drawLine(0, h - 2, w, h - 2);
            g.drawLine(0, h - 1, w, h - 1);
            g.drawLine(w - 2, 0, w - 2, h);
            g.drawLine(w - 1, 0, w - 1, h);

            // draw the drop-shadow
            g.setColor(mid);
            g.drawLine(1, h - 2, w - 2, h - 2);
            g.drawLine(w - 2, 1, w - 2, h - 2);

            g.setColor(edge);
            g.drawLine(2, h - 1, w - 2, h - 1);
            g.drawLine(w - 1, 2, w - 1, h - 2);
        }
    }

    private static Color average(Color c1, Color c2) {
        int red = c1.getRed() + (c2.getRed() - c1.getRed()) / 2;
        int green = c1.getGreen() + (c2.getGreen() - c1.getGreen()) / 2;
        int blue = c1.getBlue() + (c2.getBlue() - c1.getBlue()) / 2;
        return new Color(red, green, blue);
    }

    public static ShadowBorder getSharedInstance() {
        return INSTANCE;
    }
}
