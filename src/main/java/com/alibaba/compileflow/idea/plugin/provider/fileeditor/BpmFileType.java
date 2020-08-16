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
package com.alibaba.compileflow.idea.plugin.provider.fileeditor;

import com.intellij.openapi.fileTypes.ex.FileTypeIdentifiableByVirtualFile;
import com.intellij.openapi.vfs.CharsetToolkit;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

import com.alibaba.compileflow.idea.graph.util.Constants;
import com.alibaba.compileflow.idea.graph.util.ImageIconUtil;

/**
 * @author wuxiang
 * @author xuan
 */
public class BpmFileType implements FileTypeIdentifiableByVirtualFile {

    public static final BpmFileType INSTANCE = new BpmFileType();

    private static final String DEFAULT_EXTENSION = "bpm";
    private static final String EDITOR_NAME = "COMPILEFlOW_EDITOR";
    private static final String DESCRIPTION = "compileflow file type";

    @Override
    public boolean isMyFileType(@NotNull VirtualFile file) {
        return file.isInLocalFileSystem() && Constants.PROTOCOL_BPM.equalsIgnoreCase(file.getExtension());
    }

    @NotNull
    @Override
    public String getName() {
        return EDITOR_NAME;
    }

    @NotNull
    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return DEFAULT_EXTENSION;
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return ImageIconUtil.LOGO_ICON;
    }

    @Override
    public boolean isBinary() {
        return false;
    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Nullable
    @Override
    public String getCharset(@NotNull VirtualFile virtualFile, @NotNull byte[] bytes) {
        return CharsetToolkit.getDefaultSystemCharset().name();
    }
}
