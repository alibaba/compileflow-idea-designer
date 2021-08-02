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

    //style
    private static final String STYLE_KEY = "style";
    public static final String STYLE_DEFAULT = "classic";

    //layout
    private static final String LAYOUT_KEY = "layout";
    public static final String LAYOUT_DEFALUT = "OrthogonalLayout";

    public static void setStyle(String style) {
        LocalKvUtil.put(PATH, STYLE_KEY, style);
    }

    public static String getStyle() {
        return LocalKvUtil.get(PATH, STYLE_KEY, STYLE_DEFAULT);

    }

    public static String getLayout() {
        return LocalKvUtil.get(PATH, LAYOUT_KEY, LAYOUT_DEFALUT);
    }

    public static void setLayout(String layout) {
        LocalKvUtil.put(PATH, LAYOUT_KEY, layout);
    }

}
