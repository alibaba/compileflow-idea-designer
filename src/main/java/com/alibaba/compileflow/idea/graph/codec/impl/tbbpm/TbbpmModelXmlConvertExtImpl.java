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

import java.io.OutputStream;

import com.alibaba.compileflow.engine.definition.tbbpm.TbbpmModel;
import com.alibaba.compileflow.engine.process.preruntime.converter.impl.TbbpmModelConverter;
import com.alibaba.compileflow.engine.process.preruntime.converter.impl.parser.model.ParseConfig;
import com.alibaba.compileflow.engine.process.preruntime.converter.impl.parser.support.tbbpm.TbbpmStreamParser;
import com.alibaba.compileflow.idea.graph.codec.BpmConvert;
import com.alibaba.compileflow.idea.graph.codec.ModelXmlConvertExt;
import com.alibaba.compileflow.idea.graph.model.BpmModel;

import com.intellij.openapi.diagnostic.Logger;

/**
 * @author xuan
 * @since 2020/5/21
 */
public class TbbpmModelXmlConvertExtImpl implements ModelXmlConvertExt {

    private static final Logger log = Logger.getInstance(TbbpmModelXmlConvertExtImpl.class);

    @Override
    public String toXml(BpmModel bpmModel) {

        try {

            TbbpmModel tbbpmModel = BpmConvert.toTbbpmModel(bpmModel);
            OutputStream outputStream = TbbpmModelConverter.getInstance().convertToStream(tbbpmModel);
            if (outputStream != null) {
                return outputStream.toString();
            }

        } catch (Exception e) {
            log.error("BpmModel to xml exception.", e);
        }

        return null;
    }

    @Override
    public BpmModel toModel(String xml) {

        StringFlowStreamSource stringFlowStreamSource = new StringFlowStreamSource(xml);

        ParseConfig parseConfig = new ParseConfig();
        parseConfig.setValidateSchema(false);
        TbbpmModel tbbpmModel = TbbpmStreamParser.getInstance().parse(stringFlowStreamSource, parseConfig);

        return BpmConvert.toModel(tbbpmModel);
    }

}
