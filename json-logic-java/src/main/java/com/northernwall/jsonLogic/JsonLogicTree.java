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

import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Richard
 */
public class JsonLogicTree {
    private Node node;
    private final Gson gson;

    JsonLogicTree(Node node, Gson gson) {
        this.node = node;
        this.gson = gson;
    }
    
    /**
     * Applies the value of data to the rules described in the tree to produce a result.
     * @param data
     * @return 
     */
    public Result evaluate(String data) {
        return node.eval(convertData(data));
    }

    private Map<String, Result> convertData(String data) {
        Map<String, Result> temp = new HashMap<>();
        if (data == null || data.isEmpty()) {
            return temp;
        }
        
        //todo replace
        temp.put("a", new Result("good"));
        return temp;
    }
    
    /**
     * This method tries to reduces the complexity of the tree by pruning sub-trees that produce a constant value regardless of the value of data.
     */
    public void reduce() {
        if (node.isConstant()) {
            node = new ConstantNode(node.eval(new HashMap<>()));
        } else {
            node.reduce();
        }
    }
    
    /**
     * Produces the human readable text equivalent of the rules.
     * @return 
     */
    public String treeToString() {
        StringBuilder builder = new StringBuilder();
        node.treeToString(builder);
        return builder.toString();
    }
    
}
