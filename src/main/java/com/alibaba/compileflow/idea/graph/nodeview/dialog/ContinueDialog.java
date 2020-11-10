package com.alibaba.compileflow.idea.graph.nodeview.dialog;

import javax.swing.*;

import com.alibaba.compileflow.idea.graph.model.ContinueNodeModel;
import com.alibaba.compileflow.idea.graph.mxgraph.Graph;
import com.alibaba.compileflow.idea.graph.nodeview.component.ContinuePanel;
import com.alibaba.compileflow.idea.graph.util.StringUtil;

import com.intellij.openapi.project.Project;
import com.mxgraph.model.mxCell;
import org.jetbrains.annotations.Nullable;

/**
 * @author xuan
 * @since 2020/11/9
 */
public class ContinueDialog extends BaseDialog {

    public ContinueDialog(@Nullable Project project, mxCell cell, Graph graph) {
        super(project, cell, graph);
    }

    @Override
    protected String getDialogTitle() {
        return "Continue setting";
    }

    @Override
    protected JPanel getParamPanel(Project project, Graph graph, mxCell cell) {
        return new ContinuePanel();
    }

    @Override
    protected void initParamPanelView() {

    }

    @Override
    protected void initParamPanelData() {
        ContinuePanel continuePanel = (ContinuePanel)paramPanel;
        ContinueNodeModel continueNodeModel = ContinueNodeModel.getFromCellValue(cell.getValue());
        continuePanel.getExpressionField().setText(StringUtil.trimToEmpty(continueNodeModel.getExpression()));
    }

    @Override
    protected void doParamSave() {
        ContinuePanel continuePanel = (ContinuePanel)paramPanel;

        ContinueNodeModel continueNodeModel = ContinueNodeModel.getFromCellValue(cell.getValue());

        continueNodeModel.setExpression(StringUtil.trimToEmpty(continuePanel.getExpressionField().getText()));
    }
}
