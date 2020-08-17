package com.alibaba.compileflow.idea.graph.toolbar.action;

import java.awt.event.ActionEvent;

import javax.swing.*;

import com.alibaba.compileflow.idea.graph.mxgraph.Graph;
import com.alibaba.compileflow.idea.graph.mxgraph.GraphComponent;

/**
 * @author xuan
 * @since 2020/8/17
 */
public class MoreBigAction extends AbstractAction {

    private GraphComponent graphComponent;

    public MoreBigAction(GraphComponent graphComponent) {
        this.graphComponent = graphComponent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        graphComponent.zoomIn();
        graphComponent.refresh();
    }

}
