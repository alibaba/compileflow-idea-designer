package com.alibaba.compileflow.idea.graph.util;

import com.alibaba.compileflow.idea.plugin.LanguageConstants;

import com.intellij.ide.util.TreeClassChooser;
import com.intellij.ide.util.TreeClassChooserFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiClass;
import com.intellij.psi.search.GlobalSearchScope;

/**
 * @author xuan
 * @since 2020/7/17
 */
public class DialogUtil {

    public static void prompt(String message, String initialSelectionValue,
        Callback<String, String> okCallback) {

        String inputValue = Messages.showInputDialog(message, LanguageConstants.INPUT_DIALOG_TITLE,
            ImageIconUtil.LOGO_ICON,
            initialSelectionValue, null);

        if (null != inputValue) {
            okCallback.call(inputValue);
        }
    }

    public static void alert(String str) {
        Messages.showInfoMessage(str, LanguageConstants.INPUT_DIALOG_TITLE);
    }

    public static String selectClassName(Project project) {
        TreeClassChooserFactory chooserFactory = TreeClassChooserFactory.getInstance(project);
        GlobalSearchScope searchScope = GlobalSearchScope.allScope(project);
        TreeClassChooser chooser =
            chooserFactory.createNoInnerClassesScopeChooser(
                "Select Class", searchScope, (psiClass) -> true, null);
        chooser.showDialog();
        PsiClass psiClass = chooser.getSelected();
        if (null != psiClass) {
            return psiClass.getQualifiedName();
        }

        //cancel
        return null;
    }

}
