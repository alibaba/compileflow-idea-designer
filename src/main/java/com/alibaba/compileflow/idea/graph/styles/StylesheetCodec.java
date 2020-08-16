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

import com.mxgraph.view.mxStylesheet;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xuan
 */
public class StylesheetCodec {

    public mxStylesheet decode(Document doc) {

        mxStylesheet stylesheet = new mxStylesheet();
        Element element = doc.getDocumentElement();

        for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
            if ("add".equals(node.getNodeName()) && node instanceof Element) {
                String as = ((Element)node).getAttribute("as");
                if (as != null && as.length() > 0) {
                    String extend = ((Element)node).getAttribute("extend");
                    Map<String, Object> style = stylesheet.getStyles().get(extend);
                    if (style == null) {
                        style = new HashMap<>();
                    } else {
                        style = new HashMap<>(style);
                    }

                    for (Node entry = node.getFirstChild(); entry != null; entry = entry.getNextSibling()) {
                        if (entry instanceof Element) {
                            Element entryElement = (Element)entry;
                            String key = entryElement.getAttribute("as");
                            if ("add".equals(entry.getNodeName())) {
                                Object value = entryElement.getAttribute("value");
                                if (value != null) {
                                    style.put(key, value);
                                }
                            } else if ("remove".equals(entry.getNodeName())) {
                                style.remove(key);
                            }
                        }
                    }

                    stylesheet.putCellStyle(as, style);
                }
            }
        }

        return stylesheet;
    }

}
