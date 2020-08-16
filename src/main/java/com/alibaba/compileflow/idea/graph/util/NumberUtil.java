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
package com.alibaba.compileflow.idea.graph.util;

/**
 * Number Utils
 *
 * @author xuan
 * @since 2019/3/19
 */
public class NumberUtil {

    public static String long2Str(Long l) {
        return null == l ? StringUtil.EMPTY : String.valueOf(l);
    }

    public static Long str2Long(String str) {
        try {
            return Long.valueOf(str);
        } catch (Exception e) {
            //Ignore
        }
        return null;
    }

    public static Integer str2Integer(String str) {
        try {
            return Integer.valueOf(str);
        } catch (Exception e) {
            //Ignore
        }
        return null;
    }

}
