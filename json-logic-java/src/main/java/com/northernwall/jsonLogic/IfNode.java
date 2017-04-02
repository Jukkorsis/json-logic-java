/*
 * Copyright 2017 Richard.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.northernwall.jsonLogic;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Richard
 */
class IfNode extends Node {

    private final List<Node> nodes;

    IfNode(Node conditionNode, Node trueNode, Node falseNode) {
        nodes = new LinkedList<>();
        nodes.add(conditionNode);
        nodes.add(trueNode);
        nodes.add(falseNode);
    }

    void addConditionNode(Node conditionNode, Node trueNode) {
        nodes.add(conditionNode);
        nodes.add(trueNode);
    }

    @Override
    Result eval(Map<String, Result> data) throws EvaluationException {
        int size = nodes.size() - 1;
        int i = 0;
        while (i < size) {
            Result result = nodes.get(i).eval(data);
            if (result.getBooleanValue()) {
                return nodes.get(i + 1).eval(data);
            }
            i = i + 2;
        }
        return nodes.get(i).eval(data);
    }

    @Override
    boolean isConstant() {
        return false;
    }

    @Override
    void treeToString(StringBuilder builder) {
        builder.append("if (");
        int size = nodes.size() - 1;
        int i = 0;
        while (i < size) {
            nodes.get(i).treeToString(builder);
            builder.append(", ");
            i = i + 1;
        }
        nodes.get(i).treeToString(builder);
        builder.append(")");
    }

}
