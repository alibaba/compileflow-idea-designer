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
package com.alibaba.compileflow.idea.graph.codec.impl.tbbpm;

import com.alibaba.compileflow.engine.definition.tbbpm.TbbpmModel;
import com.alibaba.compileflow.engine.process.preruntime.converter.impl.TbbpmModelConverter;
import com.alibaba.compileflow.engine.runtime.impl.AbstractProcessRuntime;
import com.alibaba.compileflow.engine.runtime.impl.TbbpmStatelessProcessRuntime;
import com.alibaba.compileflow.idea.graph.util.Constants;
import com.alibaba.compileflow.idea.graph.codec.ModelCodeConvertExt;
import com.alibaba.compileflow.idea.graph.codec.ModelConvertFactory;
import com.alibaba.compileflow.idea.graph.codec.ModelXmlConvertExt;
import com.alibaba.compileflow.idea.graph.model.BpmModel;

/**
 * @author xuan
 * @since 2020/5/21
 */
public class TbbpmModelCodeConvertExtImpl implements ModelCodeConvertExt {

    @Override
    public String getJavaTestCode(BpmModel bpmModel) {
        return buildProcessRuntime(bpmModel).generateTestCode();
    }

    @Override
    public String getJavaCode(BpmModel bpmModel) {
        return buildProcessRuntime(bpmModel).generateJavaCode();
    }

    private AbstractProcessRuntime<TbbpmModel> buildProcessRuntime(BpmModel bpmModel) {
        ModelXmlConvertExt modelXmlConvertExt = ModelConvertFactory.getModelXmlConvertExt(Constants.PROTOCOL_BPM);
        String xml = modelXmlConvertExt.toXml(bpmModel);

        //to tbbpmModel
        StringFlowStreamSource stringFlowStreamSource = new StringFlowStreamSource(xml);
        TbbpmModel tbbpmModel = TbbpmModelConverter.getInstance().convertToModel(stringFlowStreamSource);
        AbstractProcessRuntime<TbbpmModel> processRuntime = TbbpmStatelessProcessRuntime.of(tbbpmModel);
        processRuntime.init();
        return processRuntime;
    }

}
