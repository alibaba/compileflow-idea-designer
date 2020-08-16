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

/**
 * @author xuan
 * @since 2020/5/18
 */
public class BaseNodeModel {

    private String id;
    private String name;
    private String tag;
    private String g;

    private List<TransitionModel> inTransitions;
    private List<TransitionModel> outTransitions;

    public static BaseNodeModel getBaseNodeFromCellValue(Object cellValue) {
        return (BaseNodeModel)cellValue;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getG() {
        return g;
    }

    public void setG(String g) {
        this.g = g;
    }

    public List<TransitionModel> getInTransitions() {
        return inTransitions;
    }

    public void setInTransitions(List<TransitionModel> inTransitions) {
        this.inTransitions = inTransitions;
    }

    public void addInTransition(TransitionModel inTransition) {
        if (null == inTransition) {
            return;
        }
        if (null == this.inTransitions) {
            this.inTransitions = new ArrayList<>();
        }
        this.inTransitions.add(inTransition);
    }

    public List<TransitionModel> getOutTransitions() {
        return outTransitions;
    }

    public void setOutTransitions(List<TransitionModel> outTransitions) {
        this.outTransitions = outTransitions;
    }

    public void addOutTransition(TransitionModel outTransition) {
        if (null == outTransition) {
            return;
        }
        if (null == this.outTransitions) {
            this.outTransitions = new ArrayList<>();
        }
        this.outTransitions.add(outTransition);
    }

    ////////////////////////////// ability  //////////////////////////////

    public static BaseNodeModel cloneFromCellValue(Object cellValue) {
        if (cellValue instanceof BaseNodeModel) {
            return NodeCloneFactory.cloneModel((BaseNodeModel)cellValue);
        }
        return null;
    }

    public static String getDisplayText(Object cellValue) {
        if (cellValue instanceof BaseNodeModel) {
            if (cellValue instanceof NoteNodeModel) {
                NoteNodeModel noteModel = (NoteNodeModel)cellValue;
                return noteModel.getComment();
            }
            BaseNodeModel nodeModel = (BaseNodeModel)cellValue;
            return nodeModel.getName();
        } else if (cellValue instanceof EdgeModel) {
            EdgeModel edgeModel = (EdgeModel)cellValue;
            return edgeModel.getTransition().getName();
        }
        return "";
    }

}
