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
package com.alibaba.compileflow.idea.graph.util;

import java.awt.*;

import javax.swing.*;

/**
 * @author xuan
 * @since 2019/5/8
 */
public class ImageIconUtil {

    public final static Icon LOGO_ICON = ImageIconUtil.buildImageIcon(
        "/com/alibaba/compileflow/idea/graph/images/logo/bpm.png", 15);

    public static ImageIcon buildPaletteIcon(String iconUrl) {
        return buildImageIcon(iconUrl, 20);
    }

    public static ImageIcon buildToolBarIcon(String iconUrl) {
        return buildImageIcon(iconUrl, 15);
    }

    public static ImageIcon buildImageIcon(String iconUrl, int limitSize) {
        if (null == iconUrl) {
            return null;
        }

        ImageIcon imageIcon = new ImageIcon(ImageIconUtil.class.getResource(iconUrl));
        return buildImageIcon(imageIcon, limitSize);
    }

    private static ImageIcon buildImageIcon(ImageIcon imageIcon, int limitSize) {
        if (null == imageIcon) {
            return null;
        }

        if (imageIcon.getIconWidth() > limitSize || imageIcon.getIconHeight() > limitSize) {
            imageIcon = new ImageIcon(
                imageIcon.getImage().getScaledInstance(limitSize, limitSize, Image.SCALE_AREA_AVERAGING));
        }
        return imageIcon;
    }

}
