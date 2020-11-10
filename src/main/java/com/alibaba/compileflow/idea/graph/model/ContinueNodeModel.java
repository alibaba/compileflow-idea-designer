package com.alibaba.compileflow.idea.graph.model;

/**
 * @author xuan
 * @since 2020/11/9
 */
public class ContinueNodeModel extends BaseNodeModel {

    private String expression;

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public static ContinueNodeModel of() {
        return new ContinueNodeModel();
    }

    public static ContinueNodeModel getFromCellValue(Object cellValue) {
        return (ContinueNodeModel)cellValue;
    }

}
