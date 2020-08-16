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
 * Switch draw and edit
 *
 * @author xuan
 * @since 2019/3/26
 */
public class SwitchModel {
    private final static long UNSET = -1;
    private final static long INTERVAL = 200;

    /**
     * If draw or edit model
     */
    private boolean isDraw = true;

    /**
     * Record last click timestamp
     */
    private long lastClickTime = UNSET;

    /**
     * If draw mode is turned on（isDraw = true）,
     * and the interval between this click and the last click is too long,
     * then return false.
     * else true.
     *
     * @return true/false
     */
    public boolean isPass() {
        if (!isDraw) {
            //edit model
            return true;
        }

        if (lastClickTime == UNSET) {
            //first click
            lastClickTime = System.currentTimeMillis();
            return false;
        }

        if ((System.currentTimeMillis() - lastClickTime) > INTERVAL) {
            //this click and the last click is too long
            lastClickTime = System.currentTimeMillis();
            return false;
        }

        //fire double click
        lastClickTime = UNSET;
        return true;
    }

    public boolean isDraw() {
        return isDraw;
    }

    public void setDraw(boolean draw) {
        isDraw = draw;
    }

    public String getIconStr() {
        if (isDraw) {
            return "/com/alibaba/compileflow/idea/graph/images/toolbar/mode_draw.png";
        } else {
            return "/com/alibaba/compileflow/idea/graph/images/toolbar/mode_edit.png";
        }
    }

    public String getBtnTextStr() {
        if (isDraw) {
            return "Drawing Mode";
        } else {
            return "Edit Mode";
        }
    }

    public String getToolTipText() {
        if (isDraw) {
            return "Double-Click Edit Properties in Drawing Mode";
        } else {
            return "Click Edit Properties in Edit Mode";
        }
    }

}
