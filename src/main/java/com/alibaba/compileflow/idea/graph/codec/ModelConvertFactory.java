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

import com.alibaba.compileflow.idea.graph.util.Constants;
import com.alibaba.compileflow.idea.graph.codec.impl.tbbpm.TbbpmModelCodeConvertExtImpl;
import com.alibaba.compileflow.idea.graph.codec.impl.tbbpm.TbbpmModelGraphConvertExtImpl;
import com.alibaba.compileflow.idea.graph.codec.impl.tbbpm.TbbpmModelXmlConvertExtImpl;

/**
 * @author wuxiang
 * @since 2020-05-24
 */
public class ModelConvertFactory {

    private static ModelXmlConvertExt modelXmlConvertExt = new TbbpmModelXmlConvertExtImpl();
    private static ModelCodeConvertExt modelCodeConvertExt = new TbbpmModelCodeConvertExtImpl();
    private static ModelGraphConvertExt modelGraphConvertExt = new TbbpmModelGraphConvertExtImpl();

    public static ModelXmlConvertExt getModelXmlConvertExt(String protocol) {

        if (Constants.PROTOCOL_BPM.equals(protocol)) {
            return modelXmlConvertExt;
        } else {
            throw new UnsupportedOperationException("not suppport now");
        }
    }

    public static ModelCodeConvertExt getModelCodeConvertExt(String protocol) {
        if (Constants.PROTOCOL_BPM.equals(protocol)) {
            return modelCodeConvertExt;
        } else {
            throw new UnsupportedOperationException("not suppport now");
        }
    }

    public static ModelGraphConvertExt getModelGraphConvertExt(String protocol) {
        if (Constants.PROTOCOL_BPM.equals(protocol)) {
            return modelGraphConvertExt;
        } else {
            throw new UnsupportedOperationException("not suppport now");
        }
    }

}
