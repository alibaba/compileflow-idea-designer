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
package com.alibaba.compileflow.idea.graph.codec.impl.bpmn.codec;

import com.alibaba.compileflow.idea.graph.util.xml.Node;
import com.alibaba.compileflow.idea.graph.model.ActionModel;
import com.alibaba.compileflow.idea.graph.model.AutoTaskNodeModel;

/**
 * @author xuan
 * @since 2020/2/5
 */
public class BpmnServiceTaskNodeCodec extends AbstractBpmnActionCodec {

    public static void data2Node(AutoTaskNodeModel autoTaskNode, Node currentNode) {
        if (null == autoTaskNode || null == autoTaskNode.getAction()) {
            return;
        }
        action2Node(autoTaskNode.getAction(), currentNode);
    }

    public static void node2Data(Node currentNode, AutoTaskNodeModel autoTaskNode) {
        if (null == autoTaskNode) {
            return;
        }
        ActionModel action = node2Action(currentNode);
        autoTaskNode.setAction(action);
    }

}
