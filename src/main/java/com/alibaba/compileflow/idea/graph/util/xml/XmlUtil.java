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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.alibaba.compileflow.idea.graph.util.StringUtil;

import com.intellij.openapi.diagnostic.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

/**
 * Parse xml utils.
 *
 * @author xuan
 * @since 2019/10/22
 */
public class XmlUtil {

    private static final Logger logger = Logger.getInstance(XmlUtil.class);
    public final static String SP = System.getProperty("line.separator");

    /**
     * node parse to xml string.
     *
     * @param node Node
     * @return xml string
     */
    public static String node2Xml(Node node) {
        if (null == node) {
            return "";
        }

        StringBuilder xml = new StringBuilder();
        doNode2Xml(xml, node, 0);
        return xml.toString();
    }

    /**
     * Xml string parse to node
     *
     * @param xml xml string
     * @return node
     */
    public static Node xml2Node(String xml) {
        if (null == xml) {
            return null;
        }

        try {
            InputStream xmlInputStream = new ByteArrayInputStream(xml.getBytes("utf-8"));
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlInputStream);

            NodeList nodeList = document.getChildNodes();
            if (null == nodeList || nodeList.getLength() < 1) {
                return null;
            }

            org.w3c.dom.Node rootNode = nodeList.item(0);
            return doNode2Node(rootNode);

        } catch (Exception e) {
            logger.error("XmlUtil@xml2Node_exception. msg:" + e.getMessage(), e);
        }

        return null;
    }

    private static void doNode2Xml(StringBuilder xml, Node node, int blankSpaceNum) {
        xml.append(SP);

        //attr
        xml.append(getBlankSpaceStr(blankSpaceNum) + "<" + node.getType());
        if (null != node.getAttr()) {
            for (Entry<String, String> attrEntry : node.getAttr().entrySet()) {
                if (null != attrEntry.getValue()) {
                    xml.append(" " + attrEntry.getKey() + "=\"" + filterSpecialCharacters(attrEntry.getValue()) + "\"");
                }
            }
        }

        if (null != node.getChildList()) {
            xml.append(">");
            for (Node child : node.getChildList()) {
                // child
                doNode2Xml(xml, child, blankSpaceNum + 2);
            }
            xml.append(SP);
            xml.append(getBlankSpaceStr(blankSpaceNum) + "</" + node.getType() + ">");
        } else if (!StringUtil.isEmpty(node.getValue())) {
            xml.append(">");
            xml.append(getNodeValue(node.getValue()));
            xml.append("</" + node.getType() + ">");
        } else {
            xml.append("/>");
        }
    }

    private static String getBlankSpaceStr(int blankSpaceNum) {
        StringBuilder blankSpaceStr = new StringBuilder();
        for (int i = 0; i < blankSpaceNum; i++) {
            blankSpaceStr.append(" ");
        }
        return blankSpaceStr.toString();
    }

    private static String getNodeValue(String value) {
        return "<![CDATA[" + value + "]]>";
    }

    private static String filterSpecialCharacters(String xml) {
        return xml.replace("<", "&lt;").replace(">", "&gt;");
    }

    private static Node doNode2Node(org.w3c.dom.Node w3cNode) {
        if (null == w3cNode) {
            return null;
        }

        Node node = new Node(w3cNode.getNodeName());

        //attr
        NamedNodeMap namedNodeMap = w3cNode.getAttributes();
        if (null != namedNodeMap && namedNodeMap.getLength() > 0) {
            for (int i = 0, n = namedNodeMap.getLength(); i < n; i++) {
                org.w3c.dom.Node attr = namedNodeMap.item(i);
                node.addAttr(attr.getNodeName(), attr.getNodeValue());
            }
        }

        //child
        NodeList nodeList = w3cNode.getChildNodes();
        if (null != nodeList && nodeList.getLength() > 0) {
            for (int i = 0, n = nodeList.getLength(); i < n; i++) {
                org.w3c.dom.Node child = nodeList.item(i);

                if (!(child instanceof Element)) {
                    if ("#cdata-section".equals(child.getNodeName())) {
                        node.setValue(child.getNodeValue());
                    }
                } else {
                    node.addChild(doNode2Node(child));
                }
            }
        }
        return node;
    }

    public static void main(String[] args) {
        Node bpm = new Node("bpm");
        bpm.addAttr("code", "bpm.ktvExample");
        bpm.addAttr("name", "xxxx");

        Node autoTask = new Node("autoTask");
        autoTask.addAttr("a", "111");
        autoTask.addAttr("b", "222");
        bpm.addChild(autoTask);

        Node decition = new Node("decition");
        decition.addAttr("aa", "111aaa");
        decition.addAttr("ba", "222aaa");
        bpm.addChild(decition);

        Node decition1 = new Node("decition1");
        decition1.addAttr("ddd", "vvv");
        decition.addChild(decition1);

        System.out.println(node2Xml(bpm));
    }

}
