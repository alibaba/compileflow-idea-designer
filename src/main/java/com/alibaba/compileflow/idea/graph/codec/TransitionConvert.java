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

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.alibaba.compileflow.engine.definition.tbbpm.Transition;
import com.alibaba.compileflow.idea.graph.model.TransitionModel;

/**
 * @author xuan
 * @since 2020/5/19
 */
class TransitionConvert {

    public static Transition toTransition(TransitionModel transition) {
        if (null == transition) {
            return null;
        }

        Transition t = new Transition();
        t.setName(transition.getName());
        t.setTo(transition.getTo());
        t.setPriority(toInt(transition.getPriority()));
        t.setG(transition.getG());
        t.setExpression(transition.getExpression());
        return t;
    }

    public static List<Transition> toTransitionList(List<TransitionModel> transitionList) {
        if (null == transitionList) {
            return null;
        }

        return transitionList.stream().map(TransitionConvert::toTransition).filter(Objects::nonNull).collect(
            Collectors.toList());
    }

    public static TransitionModel toModel(Transition tn) {
        if (null == tn) {
            return null;
        }

        TransitionModel t = TransitionModel.of();
        t.setName(tn.getName());
        t.setTo(tn.getTo());
        t.setPriority(String.valueOf(tn.getPriority()));
        t.setG(tn.getG());
        t.setExpression(tn.getExpression());
        return t;
    }

    public static List<TransitionModel> toModelList(List<Transition> tnList) {
        if (null == tnList) {
            return null;
        }
        return tnList.stream().map(TransitionConvert::toModel).filter(Objects::nonNull).collect(
            Collectors.toList());
    }

    private static int toInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            //Ignore
        }
        return 0;
    }

}
