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
 * @since 2020/5/10
 */
public class NodeCloneFactory {

    public static BaseNodeModel cloneModel(BaseNodeModel oldModel) {
        BaseNodeModel newModel;

        if (oldModel instanceof AutoTaskNodeModel) {
            newModel = AutoTaskNodeModel.of();
        } else if (oldModel instanceof DecisionNodeModel) {
            newModel = DecisionNodeModel.of();
        } else if (oldModel instanceof SubBpmNodeModel) {
            newModel = SubBpmNodeModel.of();
        } else if (oldModel instanceof ScriptTaskNodeModel) {
            newModel = ScriptTaskNodeModel.of();
        } else if (oldModel instanceof StartNodeModel) {
            newModel = StartNodeModel.of();
        } else if (oldModel instanceof EndNodeModel) {
            newModel = EndNodeModel.of();
        } else if (oldModel instanceof UserTaskNodeModel) {
            newModel = UserTaskNodeModel.of();
        } else if (oldModel instanceof WaitTaskNodeModel) {
            newModel = WaitTaskNodeModel.of();
        } else if (oldModel instanceof NoteNodeModel) {
            newModel = NoteNodeModel.of();
            ((NoteNodeModel)newModel).setComment(((NoteNodeModel)oldModel).getComment());
        } else if (oldModel instanceof LoopProcessNodeModel) {
            newModel = LoopProcessNodeModel.of();
        } else if (oldModel instanceof ContinueNodeModel) {
            newModel = ContinueNodeModel.of();
        } else if (oldModel instanceof BreakNodeModel) {
            newModel = BreakNodeModel.of();
        } else {
            newModel = AutoTaskNodeModel.of();
        }
        newModel.setName(oldModel.getName());
        return newModel;
    }

}
