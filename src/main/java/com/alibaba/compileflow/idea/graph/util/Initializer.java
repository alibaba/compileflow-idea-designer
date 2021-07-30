package com.alibaba.compileflow.idea.graph.util;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeManager;

/**
 * @author xuan
 * @since 2021/7/29
 */
public class Initializer {

    private static volatile boolean isInit = false;

    public static void init() {
        if (!isInit) {
            isInit = true;
            ApplicationManager.getApplication().runWriteAction(() -> {
                final FileType xmlFileType = FileTypeManager.getInstance().getStdFileType("XML");
                FileTypeManager.getInstance().associateExtension(xmlFileType, "bpm");
            });
        }
    }

}
