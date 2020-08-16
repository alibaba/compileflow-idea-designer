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
package com.alibaba.compileflow.idea.graph.mxgraph;

import com.alibaba.compileflow.idea.graph.model.BaseNodeModel;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;

/**
 * @author xuan
 * @since 2019/5/9
 */
public class Cell extends mxCell {

    public Cell(Object value, mxGeometry geometry, String style) {
        super(value, geometry, style);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        mxCell newCell = (mxCell)super.clone();
        BaseNodeModel nodeModel = BaseNodeModel.cloneFromCellValue(newCell.getValue());

        if (null != nodeModel) {
            newCell.setId(Cell.createId());
            newCell.setValue(nodeModel);
        }

        return newCell;
    }

    public static String createId() {
        return String.valueOf(System.currentTimeMillis());
    }

}
