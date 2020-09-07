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
package com.alibaba.compileflow.idea.graph.util.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.alibaba.compileflow.idea.graph.util.StringUtil;

/**
 * Xml node model.
 *
 * @author xuan
 * @since 2019/10/22
 */
public class Node {

    private String type;

    private String value;

    private Map<String, String> attr;

    private List<Node> childList;

    public Node(String type) {
        this.type = type;
    }

    public void addAttr(String key, String value) {
        if (null == attr) {
            attr = new HashMap<>();
        }
        if (!StringUtil.isEmpty(value)) {
            attr.put(key, value);
        }
    }

    public String getAttr(String key) {
        if (null == attr) {
            return null;
        }
        return attr.get(key);
    }

    public void addChild(Node node) {
        if (null == childList) {
            childList = new ArrayList<>();
        }
        childList.add(node);
    }

    public Node getFistChild() {
        if (null == childList || childList.size() <= 0) {
            return null;
        }

        return childList.get(0);
    }

    public Node getFistChild(String type) {
        List<Node> typeNodeList = getChildList(type);
        if (null == typeNodeList || typeNodeList.size() <= 0) {
            return null;
        }

        return typeNodeList.get(0);
    }

    public List<Node> getChildList(String type) {
        if (null == childList) {
            return null;
        }
        List<Node> typeChildList = new ArrayList<>();
        for (Node node : childList) {
            if (Objects.equals(node.getType(), type)) {
                typeChildList.add(node);
            }
        }
        return typeChildList;
    }

    public void clearChild(String type) {
        if (null == childList) {
            return;
        }

        Iterator<Node> itr = childList.iterator();
        while (itr.hasNext()) {
            Node node = itr.next();
            if (type.equals(node.getType())) {
                itr.remove();
            }
        }
    }

    public void clearChild() {
        if (null == childList) {
            return;
        }
        childList.clear();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getAttr() {
        return attr;
    }

    public void setAttr(Map<String, String> attr) {
        this.attr = attr;
    }

    public List<Node> getChildList() {
        return childList;
    }

    public void setChildList(List<Node> childList) {
        this.childList = childList;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static Node buildNode(String type, Node parent) {
        Node node = new Node(type);
        parent.addChild(node);
        return node;
    }

}
