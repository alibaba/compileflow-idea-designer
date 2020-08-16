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
 * String Utils
 *
 * @author xuan
 * @since 2019/3/21
 */
public class StringUtil {
    public final static String EMPTY = "";

    public static boolean isEquals(String str1, String str2) {
        if (null == str1 && null == str2) {
            return true;
        } else if (null != str1 && null != str2) {
            return str1.equals(str2);
        }
        return false;
    }

    public static String trimToEmpty(String str) {
        if (null == str) {
            return EMPTY;
        }
        return str.trim();
    }

    public static boolean isEmpty(String str) {
        return null == str || str.trim().isEmpty();
    }

    public static String emptyToNull(String str) {
        if (isEmpty(str)) {
            return null;
        }
        return str;
    }

}
