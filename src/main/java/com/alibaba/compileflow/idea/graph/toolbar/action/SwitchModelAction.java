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
package com.alibaba.compileflow.idea.graph.toolbar.action;

import com.alibaba.compileflow.idea.graph.util.DialogUtil;
import com.alibaba.compileflow.idea.graph.util.ImageIconUtil;
import com.alibaba.compileflow.idea.graph.util.SwitchModel;

import javax.swing.*;

import java.awt.event.ActionEvent;

/**
 * Switch draw and edit model
 *
 * @author xuan
 * @since 2019/3/26
 */
public class SwitchModelAction extends AbstractAction {

    private JButton btn;
    private SwitchModel switchModel;

    @Override
    public void actionPerformed(ActionEvent e) {
        switchModel.setDraw(!switchModel.isDraw());
        btn.setToolTipText(switchModel.getToolTipText());
        btn.setIcon(ImageIconUtil.buildToolBarIcon(switchModel.getIconStr()));

        String tips;
        if (switchModel.isDraw()) {
            tips = "Open the drawing mode and double-click the node to set the properties";
        } else {
            tips = "Open edit mode and click node to set properties";
        }
        DialogUtil.alert(tips);
    }

    public void setBtn(JButton btn) {
        this.btn = btn;
    }

    public void setSwitchModel(SwitchModel switchModel) {
        this.switchModel = switchModel;
    }

}
