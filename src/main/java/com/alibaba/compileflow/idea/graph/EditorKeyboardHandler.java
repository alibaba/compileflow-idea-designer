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
package com.alibaba.compileflow.idea.graph;

import com.mxgraph.swing.handler.mxKeyboardHandler;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxGraphActions;

import javax.swing.*;

/**
 * EditorKeyboardHandler
 *
 * @author wuxiang
 * @author xuan
 */
public class EditorKeyboardHandler extends mxKeyboardHandler {

    public EditorKeyboardHandler(mxGraphComponent graphComponent) {
        super(graphComponent);
    }

    @Override
    protected InputMap getInputMap(int condition) {
        InputMap map = super.getInputMap(condition);

        if (condition == JComponent.WHEN_FOCUSED && map != null) {
            map.put(KeyStroke.getKeyStroke("control S"), "save");
            map.put(KeyStroke.getKeyStroke("control shift S"), "saveAs");
            map.put(KeyStroke.getKeyStroke("control N"), "new");
            map.put(KeyStroke.getKeyStroke("control O"), "open");

            map.put(KeyStroke.getKeyStroke("control Z"), "undo");
            map.put(KeyStroke.getKeyStroke("control Y"), "redo");
            map
                .put(KeyStroke.getKeyStroke("control shift V"),
                    "selectVertices");
            map.put(KeyStroke.getKeyStroke("DELETE"), "delete");
            map.put(KeyStroke.getKeyStroke("control shift E"), "selectEdges");
        }

        return map;
    }

    @Override
    protected ActionMap createActionMap() {
        ActionMap map = super.createActionMap();

        map.put("selectVertices", mxGraphActions.getSelectVerticesAction());
        map.put("selectEdges", mxGraphActions.getSelectEdgesAction());

        return map;
    }

}
