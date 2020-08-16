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
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;

/**
 * @author wuxiang
 * @author xuan
 * @since 2019/5/9
 */
public class Graph extends mxGraph {

    public Graph(mxGraphModel model, mxStylesheet stylesheet) {
        super(model, stylesheet);
        this.setVertexLabelsMovable(false);
        this.setResetViewOnRootChange(false);
        this.setDisconnectOnMove(false);
        this.setAllowDanglingEdges(false);
        this.setResetEdgesOnConnect(false);
        this.setMultigraph(true);
        this.setCellsDisconnectable(false);
        this.setKeepEdgesInForeground(true);
        this.setGridSize(5);
        this.setAutoSizeCells(true);
    }

    @Override
    public boolean isCellEditable(Object cell) {
        return getModel().isEdge(cell);
    }

    @Override
    public String convertValueToString(Object cell) {
        if (cell instanceof mxCell) {
            Object cellValue = ((mxCell)cell).getValue();
            return BaseNodeModel.getDisplayText(cellValue);
        }
        return super.convertValueToString(cell);
    }

    @Override
    public GraphModel getModel() {
        return (GraphModel)this.model;
    }

    @Override
    public Object createVertex(Object var1, String var2, Object var3, double var4, double var6, double var8,
        double var10, String var12, boolean var13) {
        mxGeometry mxGeometry = new mxGeometry(var4, var6, var8, var10);
        mxGeometry.setRelative(var13);
        Cell cell = new Cell(var3, mxGeometry, var12);
        cell.setId(var2);
        cell.setVertex(true);
        cell.setConnectable(true);
        return cell;
    }

}
