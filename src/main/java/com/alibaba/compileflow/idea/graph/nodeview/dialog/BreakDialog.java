package com.alibaba.compileflow.idea.graph.nodeview.dialog;

import javax.swing.*;

import com.alibaba.compileflow.idea.graph.model.BreakNodeModel;
import com.alibaba.compileflow.idea.graph.mxgraph.Graph;
import com.alibaba.compileflow.idea.graph.nodeview.component.BreakPanel;
import com.alibaba.compileflow.idea.graph.nodeview.component.ContinuePanel;
import com.alibaba.compileflow.idea.graph.util.StringUtil;

import com.intellij.openapi.project.Project;
import com.mxgraph.model.mxCell;
import org.jetbrains.annotations.Nullable;

/**
 * @author xuan
 * @since 2020/11/9
 */
public class BreakDialog extends BaseDialog {

    public BreakDialog(@Nullable Project project, mxCell cell, Graph graph) {
        super(project, cell, graph);
    }

    @Override
    protected String getDialogTitle() {
        return "Break setting";
    }

    @Override
    protected JPanel getParamPanel(Project project, Graph graph, mxCell cell) {
        return new BreakPanel();
    }

    @Override
    protected void initParamPanelView() {

    }

    @Override
    protected void initParamPanelData() {
        BreakPanel breakPanel = (BreakPanel)paramPanel;
        BreakNodeModel breakNodeModel = BreakNodeModel.getFromCellValue(cell.getValue());
        breakPanel.getExpressionField().setText(StringUtil.trimToEmpty(breakNodeModel.getExpression()));
    }

    @Override
    protected void doParamSave() {
        BreakPanel breakPanel = (BreakPanel)paramPanel;
        BreakNodeModel breakNodeModel = BreakNodeModel.getFromCellValue(cell.getValue());
        breakNodeModel.setExpression(StringUtil.trimToEmpty(breakPanel.getExpressionField().getText()));
    }

}
