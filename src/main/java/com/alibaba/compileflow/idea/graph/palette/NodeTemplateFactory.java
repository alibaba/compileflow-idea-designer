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
package com.alibaba.compileflow.idea.graph.palette;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.compileflow.idea.graph.util.Constants;
import com.alibaba.compileflow.idea.graph.model.LoopProcessNodeModel;
import com.alibaba.compileflow.idea.graph.palette.EditorPaletteModel.Template;
import com.alibaba.compileflow.idea.graph.model.AutoTaskNodeModel;
import com.alibaba.compileflow.idea.graph.model.DecisionNodeModel;
import com.alibaba.compileflow.idea.graph.model.EndNodeModel;
import com.alibaba.compileflow.idea.graph.model.NoteNodeModel;
import com.alibaba.compileflow.idea.graph.model.ScriptTaskNodeModel;
import com.alibaba.compileflow.idea.graph.model.StartNodeModel;
import com.alibaba.compileflow.idea.graph.model.SubBpmNodeModel;
import com.alibaba.compileflow.idea.graph.model.UserTaskNodeModel;
import com.alibaba.compileflow.idea.graph.model.WaitTaskNodeModel;

/**
 * @author xuan
 * @since 2020/5/10
 */
public class NodeTemplateFactory {
    
    private static List<Template> BPM_TEMPLATE_LIST = new ArrayList<>();
    private static List<Template> BPMN_TEMPLATE_LIST = new ArrayList<>();

    public static List<Template> getTemplateList(String protocol) {
        return Constants.PROTOCOL_BPMN.equals(protocol) ? BPMN_TEMPLATE_LIST : BPM_TEMPLATE_LIST;
    }

    static {
        //start
        Template start = new Template();
        start.name = "开始节点";
        start.iconUrl = "/com/alibaba/compileflow/idea/graph/images/palette/start.png";
        start.style = "start";
        start.width = 30;
        start.height = 30;
        start.value = StartNodeModel.of();
        ((StartNodeModel)start.value).setName("开始");
        BPM_TEMPLATE_LIST.add(start);
        //end
        Template end = new Template();
        end.name = "结束节点";
        end.iconUrl = "/com/alibaba/compileflow/idea/graph/images/palette/end.png";
        end.style = "end";
        end.width = 30;
        end.height = 30;
        end.value = EndNodeModel.of();
        ((EndNodeModel)end.value).setName("结束");
        BPM_TEMPLATE_LIST.add(end);
        //auto
        Template auto = new Template();
        auto.name = "自动节点";
        auto.iconUrl = "/com/alibaba/compileflow/idea/graph/images/palette/auto.png";
        auto.style = "auto";
        auto.width = 90;
        auto.height = 50;
        auto.value = AutoTaskNodeModel.of();
        ((AutoTaskNodeModel)auto.value).setName("自动节点");
        BPM_TEMPLATE_LIST.add(auto);
        //decision
        Template decision = new Template();
        decision.name = "判断节点";
        decision.iconUrl = "/com/alibaba/compileflow/idea/graph/images/palette/decision.png";
        decision.style = "gateway";
        decision.width = 90;
        decision.height = 50;
        decision.value = DecisionNodeModel.of();
        ((DecisionNodeModel)decision.value).setName("判断节点");
        BPM_TEMPLATE_LIST.add(decision);
        //script
        Template script = new Template();
        script.name = "脚本节点";
        script.iconUrl = "/com/alibaba/compileflow/idea/graph/images/palette/script.png";
        script.style = "script";
        script.width = 90;
        script.height = 50;
        script.value = ScriptTaskNodeModel.of();
        ((ScriptTaskNodeModel)script.value).setName("脚本节点");
        BPM_TEMPLATE_LIST.add(script);
        //subBpm
        Template subBpm = new Template();
        subBpm.name = "子流程节点";
        subBpm.iconUrl = "/com/alibaba/compileflow/idea/graph/images/palette/subBpm.png";
        subBpm.style = "subprocess";
        subBpm.width = 90;
        subBpm.height = 50;
        subBpm.value = SubBpmNodeModel.of();
        ((SubBpmNodeModel)subBpm.value).setName("子流程节点");
        BPM_TEMPLATE_LIST.add(subBpm);
        //user
        Template user = new Template();
        user.name = "人工节点";
        user.iconUrl = "/com/alibaba/compileflow/idea/graph/images/palette/user.png";
        user.style = "user";
        user.width = 90;
        user.height = 50;
        user.value = UserTaskNodeModel.of();
        ((UserTaskNodeModel)user.value).setName("人工节点");
        //BPM_TEMPLATE_LIST.add(user);
        //wait
        Template wait = new Template();
        wait.name = "等待节点";
        wait.iconUrl = "/com/alibaba/compileflow/idea/graph/images/palette/wait.png";
        wait.style = "wait";
        wait.width = 90;
        wait.height = 50;
        wait.value = WaitTaskNodeModel.of();
        ((WaitTaskNodeModel)wait.value).setName("等待节点");
        //BPM_TEMPLATE_LIST.add(wait);
        //note
        Template note = new Template();
        note.name = "注释节点";
        note.iconUrl = "/com/alibaba/compileflow/idea/graph/images/palette/note.png";
        note.style = "note";
        note.width = 90;
        note.height = 50;
        note.value = NoteNodeModel.of();
        ((NoteNodeModel)note.value).setName("注释节点");
        ((NoteNodeModel)note.value).setComment("注释节点");
        BPM_TEMPLATE_LIST.add(note);
        //loop
        Template loop = new Template();
        loop.name = "循环节点";
        loop.iconUrl = "/com/alibaba/compileflow/idea/graph/images/palette/loop.png";
        loop.style = "loopVertex";
        loop.width = 200;
        loop.height = 200;
        loop.value = LoopProcessNodeModel.of();
        ((LoopProcessNodeModel)loop.value).setName("循环节点");
        BPM_TEMPLATE_LIST.add(loop);
    }

    static {
        //start
        Template start = new Template();
        start.name = "开始节点";
        start.iconUrl = "/com/alibaba/compileflow/idea/graph/images/palette/start.png";
        start.style = "start";
        start.width = 30;
        start.height = 30;
        start.value = StartNodeModel.of();
        ((StartNodeModel)start.value).setName("开始");
        BPMN_TEMPLATE_LIST.add(start);
        //end
        Template end = new Template();
        end.name = "结束节点";
        end.iconUrl = "/com/alibaba/compileflow/idea/graph/images/palette/end.png";
        end.style = "end";
        end.width = 30;
        end.height = 30;
        end.value = EndNodeModel.of();
        ((EndNodeModel)end.value).setName("结束");
        BPMN_TEMPLATE_LIST.add(end);
        //auto
        Template auto = new Template();
        auto.name = "自动节点";
        auto.iconUrl = "/com/alibaba/compileflow/idea/graph/images/palette/auto.png";
        auto.style = "auto";
        auto.width = 90;
        auto.height = 50;
        auto.value = AutoTaskNodeModel.of();
        ((AutoTaskNodeModel)auto.value).setName("服务节点");
        BPMN_TEMPLATE_LIST.add(auto);
        //decision
        Template decision = new Template();
        decision.name = "判断节点";
        decision.iconUrl = "/com/alibaba/compileflow/idea/graph/images/palette/decision.png";
        decision.style = "gateway";
        decision.width = 90;
        decision.height = 50;
        decision.value = DecisionNodeModel.of();
        ((DecisionNodeModel)decision.value).setName("判断节点");
        BPMN_TEMPLATE_LIST.add(decision);
        //script
        Template script = new Template();
        script.name = "脚本节点";
        script.iconUrl = "/com/alibaba/compileflow/idea/graph/images/palette/script.png";
        script.style = "script";
        script.width = 90;
        script.height = 50;
        script.value = ScriptTaskNodeModel.of();
        ((ScriptTaskNodeModel)script.value).setName("脚本节点");
        BPMN_TEMPLATE_LIST.add(script);
    }

}
