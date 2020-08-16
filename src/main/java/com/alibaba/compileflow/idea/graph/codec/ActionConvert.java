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
package com.alibaba.compileflow.idea.graph.codec;

import com.alibaba.compileflow.engine.definition.common.action.IAction;
import com.alibaba.compileflow.engine.definition.common.action.impl.Action;
import com.alibaba.compileflow.idea.graph.model.ActionModel;

/**
 * @author xuan
 * @since 2020/5/19
 */
class ActionConvert {

    public static IAction toAction(ActionModel action) {
        if (null == action) {
            return null;
        }

        Action a = new Action();
        a.setType(action.getType());
        a.setActionHandle(ActionHandleConvert.toActionHandle(action.getActionHandle()));
        return a;
    }

    public static ActionModel toModel(IAction action) {
        if (null == action) {
            return null;
        }

        ActionModel model = ActionModel.of();
        model.setType(action.getType());
        model.setActionHandle(ActionHandleConvert.toModel(action.getActionHandle()));
        return model;
    }

}
