package com.alibaba.compileflow.idea.graph.model;

/**
 * @author xuan
 * @since 2020/11/9
 */
public class BreakNodeModel extends BaseNodeModel {

    private String expression;

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public static BreakNodeModel of() {
        return new BreakNodeModel();
    }

    public static BreakNodeModel getFromCellValue(Object cellValue) {
        return (BreakNodeModel)cellValue;
    }

}
