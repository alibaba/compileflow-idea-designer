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

import com.alibaba.compileflow.idea.graph.util.SettingsUtils;

import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxStylesheet;
import org.w3c.dom.Document;

/**
 * @author wuxiang
 */
public class StyleSheetLoader {

    private static final String STYLE_XML_DIR = "/com/alibaba/compileflow/idea/graph/styles/";

    public mxStylesheet load() {

        String style = SettingsUtils.getStyle();
        String styleFilePath = STYLE_XML_DIR + style + ".xml";

        Document doc = mxUtils.loadDocument(StyleSheetLoader.class.getResource(styleFilePath).toString());
        StylesheetCodec codec = new StylesheetCodec();
        return codec.decode(doc);
    }

}
