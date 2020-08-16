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

/**
 * @author xuan
 * @since 2020/5/11
 */
public class ActionModel {
    public static final String SPRING_BEAN = "spring-bean";
    public static final String JAVA_BEAN = "java";
    public static final String QL = "ql";

    private ActionHandleModel actionHandle;
    private String type;

    private ActionModel() {

    }

    public static ActionModel of() {
        return new ActionModel();
    }

    public static ActionModel getActionFromCellValue(Object cellValue) {
        if (cellValue instanceof AutoTaskNodeModel) {
            return ((AutoTaskNodeModel)cellValue).getAction();
        }
        if (cellValue instanceof DecisionNodeModel) {
            return ((DecisionNodeModel)cellValue).getAction();
        }

        return null;
    }

    public static void setActionToCellValue(Object cellValue, ActionModel actionModel) {
        if (null == actionModel || null == cellValue) {
            return;
        }
        if (cellValue instanceof AutoTaskNodeModel) {
            ((AutoTaskNodeModel)cellValue).setAction(actionModel);
        } else if (cellValue instanceof DecisionNodeModel) {
            ((DecisionNodeModel)cellValue).setAction(actionModel);
        }

    }

    public ActionHandleModel getActionHandle() {
        return actionHandle;
    }

    public void setActionHandle(ActionHandleModel actionHandle) {
        this.actionHandle = actionHandle;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
