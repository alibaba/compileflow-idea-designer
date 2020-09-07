/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.compileflow.idea.graph.util;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.xml.XmlFile;
import org.jetbrains.annotations.NotNull;

import java.io.*;

/**
 * FileUtil
 *
 * @author wuxiang
 * @author xuan
 */
public class FileUtil {
    private static final int EOF = -1;
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
    private static final String EMPTY = "";
    private static final String CHARSET = "UTF-8";

    /**
     * Check whether it is a BPM file
     *
     * @param project     Project
     * @param virtualFile Be check file
     * @return true/false
     */
    public static boolean acceptFile(@NotNull Project project, @NotNull VirtualFile virtualFile) {
        PsiFile psiFile = PsiManager.getInstance(project).findFile(virtualFile);
        boolean accept = (psiFile instanceof XmlFile);

        if (accept) {
            return psiFile.getName().indexOf(".bpm") > 0 || psiFile.getName().indexOf(".bpmn") > 0;
        }
        return accept;
    }

    /**
     * Check whether it is a valid document
     *
     * @param project  Project
     * @param document Be check document
     * @return true/false
     */
    public static boolean isFileValid(Project project, Document document) {
        final PsiDocumentManager mgr = PsiDocumentManager.getInstance(project);
        final PsiFile file = mgr.getCachedPsiFile(document);
        if (file == null) {
            return false;
        }
        return file.isValid();
    }

    /**
     * Get init bpm text
     *
     * @param code Bpm code
     * @return Bpm text
     */
    public static String getInitBpmText(String code) {

        return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
            "<bpm code=\"" + code + "\" name=\"test\" type=\"process\" description=\"This is test demo.\">\n" +
            "  <var name=\"num\" description=\"入参\" dataType=\"java.lang.Double\" inOutType=\"param\"/>\n" +
            "  <var name=\"numSqrt\" description=\"开根号结果\" dataType=\"java.lang.Double\" inOutType=\"return\"/>\n" +
            "  <start id=\"1\" name=\"开始\" g=\"115,16,30,30\">\n" +
            "    <transition g=\":-15,20\" to=\"17\"/>\n" +
            "  </start>\n" +
            "  <end id=\"11\" name=\"结束\" g=\"115,411,30,30\"/>\n" +
            "  <autoTask id=\"17\" name=\"计算平方根\" g=\"424,249,88,48\">\n" +
            "    <transition g=\":-15,20\" to=\"11\"/>\n" +
            "    <action type=\"java\">\n" +
            "      <actionHandle clazz=\"java.lang.Math\" method=\"sqrt\">\n" +
            "        <var name=\"input\" dataType=\"double\" contextVarName=\"num\" inOutType=\"param\"/>\n" +
            "        <var name=\"output\" dataType=\"double\" contextVarName=\"numSqrt\" inOutType=\"return\"/>\n" +
            "      </actionHandle>\n" +
            "    </action>\n" +
            "  </autoTask>\n" +
            "</bpm>";
    }

    /**
     * Read file to string using utf-8
     *
     * @param file file
     * @return string
     */
    public static String readFileToString(File file) {
        if (null == file) {
            return EMPTY;
        }

        FileInputStream fis = null;
        InputStreamReader isr = null;
        try {
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis, CHARSET);

            int n;
            StringBuilder builder = new StringBuilder();
            char[] buffer = new char[DEFAULT_BUFFER_SIZE];
            while (EOF != (n = isr.read(buffer))) {
                builder.append(buffer, 0, n);
            }

            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeQuietly(fis);
            closeQuietly(isr);
        }
        return EMPTY;
    }

    public static String readToString(InputStream inputStream) {
        if (null == inputStream) {
            return EMPTY;
        }

        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(inputStream, CHARSET);

            int n;
            StringBuilder builder = new StringBuilder();
            char[] buffer = new char[DEFAULT_BUFFER_SIZE];
            while (EOF != (n = isr.read(buffer))) {
                builder.append(buffer, 0, n);
            }

            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeQuietly(inputStream);
            closeQuietly(isr);
        }
        return EMPTY;
    }

    /**
     * Write string to file using utf-8
     *
     * @param file    file
     * @param content string
     */
    public static void writeStringToFile(File file, String content) {
        if (null == file || null == content) {
            return;
        }
        OutputStream os = null;
        try {
            if (!file.exists()) {
                boolean success = file.createNewFile();
                if (!success) {
                    return;
                }
            }

            os = new FileOutputStream(file);
            os.write(content.getBytes(CHARSET));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeQuietly(os);
        }

    }

    private static void closeQuietly(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException ioe) {
            // ignore
        }
    }

}
