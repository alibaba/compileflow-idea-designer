package com.alibaba.compileflow.idea.graph.toolbar.action;

import java.awt.event.ActionEvent;

import javax.swing.*;

import com.alibaba.compileflow.idea.graph.mxgraph.GraphComponent;

/**
 * @author xuan
 * @since 2020/8/17
 */
public class MoreSmallAction extends AbstractAction {

    private GraphComponent graphComponent;

    public MoreSmallAction(GraphComponent graphComponent) {
        this.graphComponent = graphComponent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        graphComponent.zoomOut();
        graphComponent.refresh();
    }

}
