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
package com.alibaba.compileflow.idea.graph.codec;

import com.alibaba.compileflow.idea.graph.mxgraph.Graph;
import com.alibaba.compileflow.idea.graph.util.FileUtil;
import com.alibaba.compileflow.idea.graph.model.BpmModel;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDocumentManager;

/**
 * @author xuan
 */
public class DocumentHandler {

    private static final Logger log = Logger.getInstance(DocumentHandler.class);

    private Graph graph;
    private Project project;
    private Document document;
    private VirtualFile file;

    public DocumentHandler(Graph graph, Project project, VirtualFile file) {
        this.graph = graph;
        this.project = project;
        ApplicationManager.getApplication().runReadAction(() -> {
            document = FileDocumentManager.getInstance().getDocument(file);
            if (null != document) {
                document.setReadOnly(false);
            }
        });

        this.file = file;
    }

    public void readFromFile() {
        try {
            // step1: If file is blank. Init a demo.
            String text = this.document.getText();
            if (text.length() == 0) {
                text = FileUtil.getInitBpmText(file.getNameWithoutExtension());

                final Document myDocumentFinal = this.document;
                final String textFinal = text;
                ApplicationManager.getApplication().runWriteAction(() -> {
                    myDocumentFinal.setText(textFinal);
                    PsiDocumentManager.getInstance(project).commitDocument(myDocumentFinal);
                });
            }

            // step2: convert xml to model
            BpmModel bpmModel = ModelConvertFactory.getModelXmlConvertExt(file.getExtension()).toModel(text);

            // step3: draw model to graph
            ModelConvertFactory.getModelGraphConvertExt(file.getExtension()).drawGraph(graph, bpmModel);
        } catch (Throwable e) {
            log.error("read bpm file,convert error", e);
        }
    }

    public void saveToFile() {

        //step1: convert graph to model
        BpmModel bpmModel = ModelConvertFactory.getModelGraphConvertExt(file.getExtension()).toModel(graph);

        if (null == bpmModel) {
            return;
        }

        //step2: set default params if null
        setDefaultParams(bpmModel);

        //step3: convert model to xml
        String xml = ModelConvertFactory.getModelXmlConvertExt(file.getExtension()).toXml(bpmModel);

        // step4: save xml to file
        if (null != xml) {
            ApplicationManager.getApplication().runWriteAction(() -> {
                document.setText(xml);
                FileDocumentManager.getInstance().saveDocument(document);
            });
        }
    }

    private void setDefaultParams(BpmModel bpmModel) {
        //If type is null, use process default
        bpmModel.setType(null == bpmModel.getType() ? BpmModel.BPM_DEFINE_PROCESS : bpmModel.getType());

        //If name is null, use file name default
        bpmModel.setName(null == bpmModel.getName() ? file.getName() : bpmModel.getName());

        //If code is null, use file name without extension default
        bpmModel.setCode(null == bpmModel.getCode() ? file.getNameWithoutExtension() : bpmModel.getCode());
    }

}
