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
 * @author wuxiang
 * @since 2019-03-08
 */
public class GeolocationModel {
    private static final String SPIT = ",";

    public int x;
    public int y;
    public int w;
    public int h;

    public GeolocationModel(String g) {
        String[] list = g.split(SPIT);
        x = Integer.parseInt(list[0]);
        y = Integer.parseInt(list[1]);
        w = Integer.parseInt(list[2]);
        h = Integer.parseInt(list[3]);
    }

    public static String toG(double x, double y, int w, int h) {
        StringBuilder sb = new StringBuilder();
        sb.append((int)x);
        sb.append(SPIT);
        sb.append((int)y);
        sb.append(SPIT);
        sb.append(w);
        sb.append(SPIT);
        sb.append(h);
        return sb.toString();
    }

}
