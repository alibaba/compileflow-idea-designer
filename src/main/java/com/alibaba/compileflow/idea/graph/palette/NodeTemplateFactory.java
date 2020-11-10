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

import com.alibaba.compileflow.idea.graph.model.BreakNodeModel;
import com.alibaba.compileflow.idea.graph.model.ContinueNodeModel;
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
import com.alibaba.compileflow.idea.plugin.lang.Lang;

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
        start.name = Lang.getString("node.name.start");
        start.iconUrl = "/com/alibaba/compileflow/idea/graph/images/palette/start.png";
        start.style = "start";
        start.width = 30;
        start.height = 30;
        start.value = StartNodeModel.of();
        ((StartNodeModel)start.value).setName(start.name);
        BPM_TEMPLATE_LIST.add(start);
        //end
        Template end = new Template();
        end.name = Lang.getString("node.name.end");
        end.iconUrl = "/com/alibaba/compileflow/idea/graph/images/palette/end.png";
        end.style = "end";
        end.width = 30;
        end.height = 30;
        end.value = EndNodeModel.of();
        ((EndNodeModel)end.value).setName(end.name);
        BPM_TEMPLATE_LIST.add(end);
        //auto
        Template auto = new Template();
        auto.name = Lang.getString("node.name.auto");
        auto.iconUrl = "/com/alibaba/compileflow/idea/graph/images/palette/auto.png";
        auto.style = "auto";
        auto.width = 90;
        auto.height = 50;
        auto.value = AutoTaskNodeModel.of();
        ((AutoTaskNodeModel)auto.value).setName(auto.name);
        BPM_TEMPLATE_LIST.add(auto);
        //decision
        Template decision = new Template();
        decision.name = Lang.getString("node.name.decision");
        decision.iconUrl = "/com/alibaba/compileflow/idea/graph/images/palette/decision.png";
        decision.style = "gateway";
        decision.width = 90;
        decision.height = 50;
        decision.value = DecisionNodeModel.of();
        ((DecisionNodeModel)decision.value).setName(decision.name);
        BPM_TEMPLATE_LIST.add(decision);
        //script
        Template script = new Template();
        script.name = Lang.getString("node.name.script");
        script.iconUrl = "/com/alibaba/compileflow/idea/graph/images/palette/script.png";
        script.style = "script";
        script.width = 90;
        script.height = 50;
        script.value = ScriptTaskNodeModel.of();
        ((ScriptTaskNodeModel)script.value).setName(script.name);
        BPM_TEMPLATE_LIST.add(script);
        //subBpm
        Template subBpm = new Template();
        subBpm.name = Lang.getString("node.name.sub");
        subBpm.iconUrl = "/com/alibaba/compileflow/idea/graph/images/palette/subBpm.png";
        subBpm.style = "subprocess";
        subBpm.width = 90;
        subBpm.height = 50;
        subBpm.value = SubBpmNodeModel.of();
        ((SubBpmNodeModel)subBpm.value).setName(subBpm.name);
        BPM_TEMPLATE_LIST.add(subBpm);
        //user
        Template user = new Template();
        user.name = Lang.getString("node.name.user");
        user.iconUrl = "/com/alibaba/compileflow/idea/graph/images/palette/user.png";
        user.style = "user";
        user.width = 90;
        user.height = 50;
        user.value = UserTaskNodeModel.of();
        ((UserTaskNodeModel)user.value).setName(user.name);
        //BPM_TEMPLATE_LIST.add(user);
        //wait
        Template wait = new Template();
        wait.name = Lang.getString("node.name.wait");
        wait.iconUrl = "/com/alibaba/compileflow/idea/graph/images/palette/wait.png";
        wait.style = "wait";
        wait.width = 90;
        wait.height = 50;
        wait.value = WaitTaskNodeModel.of();
        ((WaitTaskNodeModel)wait.value).setName(wait.name);
        //BPM_TEMPLATE_LIST.add(wait);
        //note
        Template note = new Template();
        note.name = Lang.getString("node.name.note");
        note.iconUrl = "/com/alibaba/compileflow/idea/graph/images/palette/note.png";
        note.style = "note";
        note.width = 90;
        note.height = 50;
        note.value = NoteNodeModel.of();
        ((NoteNodeModel)note.value).setName(note.name);
        ((NoteNodeModel)note.value).setComment(note.name);
        BPM_TEMPLATE_LIST.add(note);
        //loop
        Template loop = new Template();
        loop.name = Lang.getString("node.name.loop");
        loop.iconUrl = "/com/alibaba/compileflow/idea/graph/images/palette/loop.png";
        loop.style = "loop";
        loop.width = 200;
        loop.height = 200;
        loop.value = LoopProcessNodeModel.of();
        ((LoopProcessNodeModel)loop.value).setName(loop.name);
        BPM_TEMPLATE_LIST.add(loop);
        //loop
        Template continuee = new Template();
        continuee.name = Lang.getString("node.name.continue");
        continuee.iconUrl = "/com/alibaba/compileflow/idea/graph/images/palette/continue.png";
        continuee.style = "continue";
        continuee.width = 80;
        continuee.height = 40;
        continuee.value = ContinueNodeModel.of();
        ((ContinueNodeModel)continuee.value).setName(continuee.name);
        BPM_TEMPLATE_LIST.add(continuee);
        Template breakk = new Template();
        breakk.name = Lang.getString("node.name.break");
        breakk.iconUrl = "/com/alibaba/compileflow/idea/graph/images/palette/break.png";
        breakk.style = "break";
        breakk.width = 80;
        breakk.height = 40;
        breakk.value = BreakNodeModel.of();
        ((BreakNodeModel)breakk.value).setName(breakk.name);
        BPM_TEMPLATE_LIST.add(breakk);
    }

    static {
        //start
        Template start = new Template();
        start.name = Lang.getString("node.name.start");
        start.iconUrl = "/com/alibaba/compileflow/idea/graph/images/palette/start.png";
        start.style = "start";
        start.width = 30;
        start.height = 30;
        start.value = StartNodeModel.of();
        ((StartNodeModel)start.value).setName(start.name);
        BPMN_TEMPLATE_LIST.add(start);
        //end
        Template end = new Template();
        end.name = Lang.getString("node.name.end");
        end.iconUrl = "/com/alibaba/compileflow/idea/graph/images/palette/end.png";
        end.style = "end";
        end.width = 30;
        end.height = 30;
        end.value = EndNodeModel.of();
        ((EndNodeModel)end.value).setName(end.name);
        BPMN_TEMPLATE_LIST.add(end);
        //auto
        Template auto = new Template();
        auto.name = Lang.getString("node.name.auto");
        auto.iconUrl = "/com/alibaba/compileflow/idea/graph/images/palette/auto.png";
        auto.style = "auto";
        auto.width = 90;
        auto.height = 50;
        auto.value = AutoTaskNodeModel.of();
        ((AutoTaskNodeModel)auto.value).setName(auto.name);
        BPMN_TEMPLATE_LIST.add(auto);
        //decision
        Template decision = new Template();
        decision.name = Lang.getString("node.name.decision");
        decision.iconUrl = "/com/alibaba/compileflow/idea/graph/images/palette/decision.png";
        decision.style = "gateway";
        decision.width = 90;
        decision.height = 50;
        decision.value = DecisionNodeModel.of();
        ((DecisionNodeModel)decision.value).setName(decision.name);
        BPMN_TEMPLATE_LIST.add(decision);
        //script
        Template script = new Template();
        script.name = Lang.getString("node.name.script");
        script.iconUrl = "/com/alibaba/compileflow/idea/graph/images/palette/script.png";
        script.style = "script";
        script.width = 90;
        script.height = 50;
        script.value = ScriptTaskNodeModel.of();
        ((ScriptTaskNodeModel)script.value).setName(script.name);
        BPMN_TEMPLATE_LIST.add(script);
    }

}
