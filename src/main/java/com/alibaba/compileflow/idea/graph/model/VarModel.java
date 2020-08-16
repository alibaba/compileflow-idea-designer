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
package com.alibaba.compileflow.idea.graph.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.commons.collections.CollectionUtils;

/**
 * @author xuan
 * @since 2020/5/11
 */
public class VarModel {

    public static final String VARIABLE_TYPE_PARAM = "param";
    public static final String VARIABLE_TYPE_GLOBAL = "inner";
    public static final String VARIABLE_TYPE_RETURN = "return";

    private String name;
    private String description;
    private String dataType;
    private String contextVarName;
    private String defaultValue;
    private String inOutType;

    private VarModel() {

    }

    public static VarModel of() {
        return new VarModel();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getContextVarName() {
        return contextVarName;
    }

    public void setContextVarName(String contextVarName) {
        this.contextVarName = contextVarName;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getInOutType() {
        return inOutType;
    }

    public void setInOutType(String inOutType) {
        this.inOutType = inOutType;
    }

    ////////ability/////

    public static List<Vector<String>> toVectorList(List<VarModel> varList) {

        List<Vector<String>> vectorList = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(varList)) {

            for (VarModel var : varList) {
                //"name", "dataType", "defaultValue", "inOutType", "description", "contextVarName"
                Vector<String> vector = new Vector<String>();
                vector.add(var.getName());
                vector.add(var.getDataType());
                vector.add(var.getDefaultValue());
                vector.add(var.getInOutType());
                vector.add(var.getDescription());
                vector.add(var.getContextVarName());
                vectorList.add(vector);
            }
        }
        return vectorList;
    }

}
