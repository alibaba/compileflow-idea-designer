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

import com.alibaba.compileflow.engine.definition.common.action.IActionHandle;
import com.alibaba.compileflow.engine.definition.common.action.impl.JavaActionHandle;
import com.alibaba.compileflow.engine.definition.common.action.impl.ScriptActionHandle;
import com.alibaba.compileflow.engine.definition.common.action.impl.SpringBeanActionHandle;
import com.alibaba.compileflow.idea.graph.model.ActionHandleModel;
import com.alibaba.compileflow.idea.graph.model.JavaActionHandleModel;
import com.alibaba.compileflow.idea.graph.model.QlActionHandleModel;
import com.alibaba.compileflow.idea.graph.model.SpringBeanActionHandleModel;

/**
 * @author xuan
 * @since 2020/5/19
 */
class ActionHandleConvert {

    public static IActionHandle toActionHandle(ActionHandleModel actionHandle) {
        if (null == actionHandle) {
            return null;
        }

        if (actionHandle instanceof SpringBeanActionHandleModel) {
            SpringBeanActionHandleModel springBeanActionHandleModel = (SpringBeanActionHandleModel)actionHandle;
            SpringBeanActionHandle springBeanActionHandle = new SpringBeanActionHandle();
            springBeanActionHandle.setBean(springBeanActionHandleModel.getBean());
            springBeanActionHandle.setClazz(springBeanActionHandleModel.getClazz());
            springBeanActionHandle.setMethod(springBeanActionHandleModel.getMethod());
            springBeanActionHandle.setVars(VarConvert.toParameterDefineList(springBeanActionHandleModel.getVars()));
            return springBeanActionHandle;
        } else if (actionHandle instanceof JavaActionHandleModel) {
            JavaActionHandleModel javaActionHandleModel = (JavaActionHandleModel)actionHandle;
            JavaActionHandle javaActionHandle = new JavaActionHandle();
            javaActionHandle.setClazz(javaActionHandleModel.getClazz());
            javaActionHandle.setMethod(javaActionHandleModel.getMethod());
            javaActionHandle.setVars(VarConvert.toParameterDefineList(javaActionHandleModel.getVars()));
            return javaActionHandle;
        } else if (actionHandle instanceof QlActionHandleModel) {
            QlActionHandleModel qlActionHandleModel = (QlActionHandleModel)actionHandle;
            ScriptActionHandle scriptActionHandle = new ScriptActionHandle();
            scriptActionHandle.setExpression(qlActionHandleModel.getExpression());
            scriptActionHandle.setVars(VarConvert.toParameterDefineList(qlActionHandleModel.getVars()));
            return scriptActionHandle;
        }
        return null;
    }

    public static ActionHandleModel toModel(IActionHandle ah) {
        if (null == ah) {
            return null;
        }

        if (ah instanceof SpringBeanActionHandle) {
            SpringBeanActionHandle springBeanActionHandle = (SpringBeanActionHandle)ah;
            SpringBeanActionHandleModel springBeanActionHandleModel = SpringBeanActionHandleModel.of();
            springBeanActionHandleModel.setBean(springBeanActionHandle.getBean());
            springBeanActionHandleModel.setClazz(springBeanActionHandle.getClazz());
            springBeanActionHandleModel.setMethod(springBeanActionHandle.getMethod());
            springBeanActionHandleModel.setVars(VarConvert.toModelList(springBeanActionHandle.getVars()));
            return springBeanActionHandleModel;
        } else if (ah instanceof JavaActionHandle) {
            JavaActionHandle javaActionHandle = (JavaActionHandle)ah;
            JavaActionHandleModel javaActionHandleModel = JavaActionHandleModel.of();
            javaActionHandleModel.setClazz(javaActionHandle.getClazz());
            javaActionHandleModel.setMethod(javaActionHandle.getMethod());
            javaActionHandleModel.setVars(VarConvert.toModelList(javaActionHandle.getVars()));
            return javaActionHandleModel;
        } else if (ah instanceof ScriptActionHandle) {
            ScriptActionHandle qlActionHandle = (ScriptActionHandle)ah;
            QlActionHandleModel qlActionHandleModel = QlActionHandleModel.of();
            qlActionHandleModel.setExpression(qlActionHandle.getExpression());
            qlActionHandleModel.setVars(VarConvert.toModelList(qlActionHandle.getVars()));
            return qlActionHandleModel;
        }
        return null;
    }

}
