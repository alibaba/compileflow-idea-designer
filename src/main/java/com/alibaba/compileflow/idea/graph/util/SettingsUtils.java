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

import java.io.File;

/**
 * @author xuan
 * @since 2020/11/6
 */
public class SettingsUtils {
    private static final String PATH = System.getProperty("user.home") + File.separator + ".compileflow-idea-designer"
        + File.separator + "settings.properties";

    private static final String STYLE_KEY = "style";
    public static final String STYLE_VALUE_CLASSIC = "classic";
    public static final String STYLE_VALUE_COLOR = "color";

    public static void setStyle(String style) {
        if (!STYLE_VALUE_CLASSIC.equals(style) && !STYLE_VALUE_COLOR.equals(style)) {
            style = STYLE_VALUE_CLASSIC;
        }
        LocalKvUtil.put(PATH, STYLE_KEY, style);
    }

    public static String getStyle() {
        String style = LocalKvUtil.get(PATH, STYLE_KEY);
        if (!STYLE_VALUE_CLASSIC.equals(style) && !STYLE_VALUE_COLOR.equals(style)) {
            style = STYLE_VALUE_CLASSIC;
        }
        return style;
    }

}
