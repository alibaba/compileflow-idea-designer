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

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.model.mxICell;

import java.awt.print.PageFormat;
import java.awt.print.Paper;

import com.alibaba.compileflow.idea.graph.model.BpmModel;

/**
 * GraphModel
 *
 * @author wuxiang
 * @since 2019-02-05
 */
public class GraphModel extends mxGraphModel {

    private static final double PAGE_WIDTH = 842.4D;
    private static final double PAGE_HEIGHT = 597.6D;
    private static final String ROOT_CELL = "compileflow idea graph";

    private BpmModel bpmModel;

    public BpmModel getBpmModel() {
        return bpmModel;
    }

    public void setBpmModel(BpmModel bpmModel) {
        this.bpmModel = bpmModel;
    }

    public GraphModel() {
        super();
        setDefaultPageFormat();
        this.bpmModel = BpmModel.of();
    }

    @Override
    public mxICell createRoot() {

        mxCell root = new mxCell();
        root.insert(new mxCell(ROOT_CELL));
        return root;
    }

    public PageFormat setDefaultPageFormat() {

        PageFormat pageFormat = new PageFormat();
        Paper paper = pageFormat.getPaper();
        paper.setSize(PAGE_WIDTH, PAGE_HEIGHT * 2.0D);
        double margin = 5.0D;
        paper.setImageableArea(margin, margin, paper.getWidth() - margin * 2.0D, paper.getHeight() - margin * 2.0D);
        pageFormat.setPaper(paper);
        return pageFormat;
    }

}
