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

import java.util.Map;

/**
 *
 * @author Richard
 */
class VarNode extends Node {
    private final String name;
    private final Result defaultResult;

    VarNode(String name) {
        this.name = name;
        this.defaultResult = JsonLogic.FALSE_RESULT;
    }

    VarNode(String name, Result defaultResult) {
        this.name = name;
        this.defaultResult = defaultResult;
    }

    @Override
    Result eval(Map<String, Result> data) {
        if (data == null || data.isEmpty() || !data.containsKey(name)) {
            return defaultResult;
        }
        return data.get(name);
    }
    
    @Override
    boolean isConstant() {
        return false;
    }

    @Override
    void treeToString(StringBuilder builder) {
        builder.append("var->\"");
        builder.append(name);
        builder.append("\"");
    }
    
}
