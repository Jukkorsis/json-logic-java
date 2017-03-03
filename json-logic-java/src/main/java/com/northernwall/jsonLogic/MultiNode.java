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

import java.util.ArrayList;;
import java.util.List;

/**
 *
 * @author Richard
 */
public abstract class MultiNode extends Node {

    protected List<Node> nodes;
    private final String operator;

    MultiNode(Node left, Node right, String operator) {
        nodes = new ArrayList<>();
        nodes.add(left);
        nodes.add(right);
        this.operator = operator;
    }
    
    void add(Node Node) {
        nodes.add(Node);
    }

    @Override
    boolean isConstant() {
        for (Node node : nodes) {
            if (!node.isConstant()) {
                return false;
            }
        }
        return true;
    }

    @Override
    void reduce() throws EvaluationException {
        for (int i=0;i<nodes.size();i++) {
            Node node = nodes.get(i);
            if (node.isConstant() && !(node instanceof ConstantNode)) {
                nodes.set(i, new ConstantNode(node.eval(null)));
            }
        }
    }

    @Override
    void treeToString(StringBuilder builder) {
        builder.append("(");
        for (int i=0;i<nodes.size();i++) {
            Node node = nodes.get(i);
            node.treeToString(builder);
            if (i < nodes.size()-1) {
                builder.append(operator);
            }
        }
        builder.append(")");
    }

}
