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
package com.alibaba.compileflow.idea.plugin.action;

import com.alibaba.compileflow.idea.graph.util.Constants;
import com.alibaba.compileflow.idea.plugin.LanguageConstants;
import com.alibaba.compileflow.idea.graph.codec.ModelConvertFactory;
import com.alibaba.compileflow.idea.graph.util.DialogUtil;
import com.alibaba.compileflow.idea.graph.util.FileUtil;
import com.alibaba.compileflow.idea.graph.model.BpmModel;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.testFramework.LightVirtualFile;

import java.io.File;

/**
 * Called when clicking Generate BPM UnitTest
 *
 * @author xuan
 * @since 2019/3/9
 */
public class CreateTestAction extends AnAction {
    private final static String TEST_FILE_SUFFIX = "Flow_TEST.java";
    private final static String PROCESS = "process";

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        //1. Get and check useInfo
        Project project = anActionEvent.getData(CommonDataKeys.PROJECT);
        if (null == project) {
            return;
        }

        VirtualFile virtualFile = anActionEvent.getData(CommonDataKeys.VIRTUAL_FILE);
        VirtualFile vf = virtualFile instanceof LightVirtualFile ? ((LightVirtualFile)virtualFile).getOriginalFile()
            : virtualFile;
        if (null == vf) {
            return;
        }

        Module module = ModuleUtil.findModuleForFile(vf, project);
        if (null == module) {
            return;
        }

        if (vf.isDirectory() || !Constants.PROTOCOL_BPM.equals(vf.getExtension())) {
            return;
        }

        String[] packagesAndClassName = buildTestPackageAndClassName(vf);
        if (null == packagesAndClassName) {
            return;
        }

        //2. tips
        VirtualFile moduleFile = module.getModuleFile();
        if (null == moduleFile) {
            return;
        }
        String testDirStr = moduleFile.getParent().getPath() + "/src/test/java/" + packagesAndClassName[0];
        File testFile = new File(testDirStr + packagesAndClassName[1]);
        DialogUtil.prompt(LanguageConstants.CREATE_TEST_CLASS_MESSAGE, testFile.getPath(), (url) -> {
            File realTestFile = new File(url);
            File realTestFileDir = new File(realTestFile.getParent());
            if (!realTestFileDir.exists() || realTestFileDir.isFile()) {
                boolean success = realTestFileDir.mkdirs();
                if (!success) {
                    return null;
                }
            }
            if (realTestFile.exists()) {
                boolean success = realTestFile.delete();
                if (!success) {
                    return null;
                }
            }
            //4. Cover test file
            coverTestCode(realTestFile, vf);
            //5. refresh disk file to vfs
            VirtualFileManager.getInstance().refreshWithoutFileWatcher(true);
            return null;
        });
    }

    private void coverTestCode(File testFile, VirtualFile vf) {
        String bpmContent = FileUtil.readFileToString(new File(vf.getPath()));
        if (null == bpmContent || bpmContent.length() == 0) {
            return;
        }

        BpmModel bpmModel = ModelConvertFactory.getModelXmlConvertExt(vf.getExtension()).toModel(bpmContent);
        if (!bpmModel.getType().equals(PROCESS)) {
            return;
        }

        String testCode = ModelConvertFactory.getModelCodeConvertExt(vf.getExtension()).getJavaTestCode(bpmModel);
        if (null != testCode) {
            FileUtil.writeStringToFile(testFile, testCode);
        }
    }

    private BpmModel getBpmDefine(VirtualFile vf) {
        String bpmContent = FileUtil.readFileToString(new File(vf.getPath()));
        if (null == bpmContent || bpmContent.length() == 0) {
            return null;
        }

        BpmModel bpmModel = ModelConvertFactory.getModelXmlConvertExt(vf.getExtension()).toModel(bpmContent);
        if (null == bpmModel || !bpmModel.getType().equals(PROCESS)) {
            return null;
        }
        return bpmModel;
    }

    private String[] buildTestPackageAndClassName(VirtualFile vf) {
        BpmModel bpmModel = getBpmDefine(vf);
        if (null == bpmModel) {
            return null;
        }

        String code = bpmModel.getCode();
        if (null == code || code.length() == 0) {
            return null;
        }
        String[] packagesAndClassName = code.split("\\.");
        if (packagesAndClassName.length == 0) {
            return null;
        }

        if (packagesAndClassName.length == 1) {
            return new String[] {"", upFirst(packagesAndClassName[0]) + TEST_FILE_SUFFIX};
        } else {
            StringBuilder packageSb = new StringBuilder();
            String className = "";
            for (int i = 0; i < packagesAndClassName.length; i++) {
                if (i == (packagesAndClassName.length - 1)) {
                    className = upFirst(packagesAndClassName[i]) + TEST_FILE_SUFFIX;
                } else {
                    packageSb.append(packagesAndClassName[i]);
                    packageSb.append("/");
                }
            }
            return new String[] {packageSb.toString(), className};
        }
    }

    private String upFirst(String str) {
        if (!Character.isUpperCase(str.charAt(0))) {
            return Character.toUpperCase(str.charAt(0)) + str.substring(1);
        }
        return str;
    }

}
