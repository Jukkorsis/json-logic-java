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

/**
 *
 * @author Richard
 */
public class Result {

    private final Object value;
    
    public Result(Object value) {
        this.value = value;
    }

    public boolean isBoolean() {
        return (value instanceof Boolean);
    }

    public boolean getBooleanValue() {
        return ((Boolean)value).booleanValue();
    }
    
    public boolean isLong() {
        return (value instanceof Long);
    }

    public long getLongValue() {
        return ((Long)value).longValue();
    }
    
    public boolean isString() {
        return (value instanceof String);
    }

    public String getStringValue() {
        return value.toString();
    }
    
}
