package com.alibaba.compileflow.idea.graph.toolbar.action;

import java.awt.event.ActionEvent;

import javax.swing.*;

import com.alibaba.compileflow.idea.graph.mxgraph.Graph;
import com.alibaba.compileflow.idea.graph.mxgraph.GraphComponent;
import com.alibaba.compileflow.idea.graph.mxgraph.export.ExportImageUtil;
import com.alibaba.compileflow.idea.graph.util.DialogUtil;

import com.intellij.openapi.vfs.VirtualFile;

/**
 * @author xuan
 * @since 2020/8/7
 */
public class PngEncodeAction extends AbstractAction {

    private Graph graph;
    private GraphComponent graphComponent;
    private VirtualFile virtualFile;

    public PngEncodeAction(Graph graph, GraphComponent graphComponent, VirtualFile virtualFile) {
        this.graph = graph;
        this.graphComponent = graphComponent;
        this.virtualFile = virtualFile;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String savePath = virtualFile.getParent().getPath() + "/" + virtualFile.getNameWithoutExtension() + ".png";
        DialogUtil.prompt("Input image path", savePath, (path) -> {
            ExportImageUtil.exportImage(2048, 2048, path, graph, graphComponent);
            return null;
        });
    }

}
