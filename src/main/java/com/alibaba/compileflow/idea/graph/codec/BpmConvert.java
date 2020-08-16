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

import com.alibaba.compileflow.engine.definition.tbbpm.TbbpmModel;
import com.alibaba.compileflow.idea.graph.model.BpmModel;

/**
 * @author xuan
 * @since 2020/5/19
 */
public class BpmConvert {

    public static TbbpmModel toTbbpmModel(BpmModel bpmModel) {

        if (null == bpmModel) {
            return null;
        }

        TbbpmModel tbbpmModel = new TbbpmModel();
        tbbpmModel.setCode(bpmModel.getCode());
        tbbpmModel.setName(bpmModel.getName());
        tbbpmModel.setType(bpmModel.getType());
        tbbpmModel.setDescription(bpmModel.getDescription());
        tbbpmModel.setVars(VarConvert.toParameterDefineList(bpmModel.getVars()));
        tbbpmModel.setAllNodes(NodeConvert.toNodeList(bpmModel.getAllNodes()));

        return tbbpmModel;
    }

    public static BpmModel toModel(TbbpmModel tbbpmModel) {

        if (null == tbbpmModel) {
            return null;
        }

        BpmModel model = BpmModel.of();
        model.setCode(tbbpmModel.getCode());
        model.setName(tbbpmModel.getName());
        model.setType(tbbpmModel.getType());
        model.setDescription(tbbpmModel.getDescription());
        model.setVars(VarConvert.toModelList(tbbpmModel.getVars()));
        model.setAllNodes(NodeConvert.toModelList(tbbpmModel.getAllNodes()));

        return model;
    }

}
