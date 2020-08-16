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

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.alibaba.compileflow.engine.definition.common.var.IVar;
import com.alibaba.compileflow.engine.definition.common.var.impl.Var;
import com.alibaba.compileflow.idea.graph.model.VarModel;

/**
 * @author xuan
 * @since 2020/5/19
 */
class VarConvert {

    public static IVar toParameterDefine(VarModel var) {
        if (null == var) {
            return null;
        }

        Var pd = new Var();
        pd.setName(var.getName());
        pd.setContextVarName(var.getContextVarName());
        pd.setDataType(var.getDataType());
        pd.setDefaultValue(var.getDefaultValue());
        pd.setInOutType(var.getInOutType());
        pd.setDescription(var.getDescription());
        return pd;
    }

    public static List<IVar> toParameterDefineList(List<VarModel> varList) {
        if (null == varList) {
            return null;
        }

        return varList.stream().map(VarConvert::toParameterDefine).filter(Objects::nonNull).collect(
            Collectors.toList());
    }

    public static VarModel toModel(IVar pd) {
        if (null == pd) {
            return null;
        }

        VarModel var = VarModel.of();
        var.setName(pd.getName());
        var.setContextVarName(pd.getContextVarName());
        var.setDataType(pd.getDataType());
        var.setDefaultValue(pd.getDefaultValue());
        var.setInOutType(pd.getInOutType());
        var.setDescription(pd.getDescription());
        return var;
    }

    public static List<VarModel> toModelList(List<IVar> pdList) {
        if (null == pdList) {
            return null;
        }

        return pdList.stream().map(VarConvert::toModel).filter(Objects::nonNull).collect(
            Collectors.toList());
    }

}
