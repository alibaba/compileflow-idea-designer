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
package com.alibaba.compileflow.idea.graph.model;

/**
 * @author wuxiang
 */
public class EdgeModel {

    private BaseNodeModel currentNode;
    private TransitionModel transition;

    public EdgeModel() {
    }

    public EdgeModel(BaseNodeModel currentNode, TransitionModel transition) {
        this.currentNode = currentNode;
        this.transition = transition;
    }

    public BaseNodeModel getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(BaseNodeModel currentNode) {
        this.currentNode = currentNode;
    }

    public TransitionModel getTransition() {
        return transition;
    }

    public void setTransition(TransitionModel transition) {
        this.transition = transition;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result += 31 * currentNode.getId().hashCode();
        result += 31 * transition.getTo().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "TransitionModel{" +
            "currentNode=" + currentNode +
            ", transition=" + transition +
            '}';
    }

}
