package com.alibaba.compileflow.idea.graph.util;

import java.util.List;

import com.alibaba.compileflow.idea.graph.model.VarModel;

/**
 * @author xuan
 * @since 2020/7/24
 */
public class Checker {

    public static boolean checkVars(List<VarModel> varList) {
        if (null == varList) {
            return true;
        }
        for (VarModel var : varList) {
            if (StringUtil.isEmpty(var.getName())) {
                DialogUtil.alert("var.name can not empty.");
                return false;
            }

            if (StringUtil.isEmpty(var.getInOutType())) {
                DialogUtil.alert("var.inOutType can not empty.");
                return false;
            }

            if (StringUtil.isEmpty(var.getDataType())) {
                DialogUtil.alert("var.dataType can not empty.");
                return false;
            }
        }
        return true;
    }

}
