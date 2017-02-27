/*
 * Copyright 2017 Richard Thurston.
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
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import java.io.IOException;
import java.io.StringReader;

public class JsonLogic {

    private final Gson gson;

    public JsonLogic() {
        gson = new Gson();
    }

    public Result apply(String rule, String data) {
        JsonReader jsonRuleReader = gson.newJsonReader(new StringReader(rule));
        return apply(jsonRuleReader, data);
    }

    private Result apply(JsonReader jsonRuleReader, String data) {
        try {
            JsonToken token = jsonRuleReader.peek();
            switch (token) {
                case BEGIN_OBJECT:
                    jsonRuleReader.beginObject();
                    String operation = jsonRuleReader.nextName();
                    Result result = null;
                    switch (operation) {
                        case "==":
                            result = processEquals(jsonRuleReader, data);
                            break;
                        case ">":
                            result = processGreaterThan(jsonRuleReader, data);
                            break;
                        case "<":
                            result = processLessThan(jsonRuleReader, data);
                            break;
                        case "and":
                            result = processAnd(jsonRuleReader, data);
                            break;
                        case "or":
                            result = processOr(jsonRuleReader, data);
                            break;
                        case "var":
                            result = processVar(jsonRuleReader, data);
                            break;
                    }
                    jsonRuleReader.endObject();
                    return result;
                case NUMBER:
                    return new Result(new Long(jsonRuleReader.nextLong()));
                case BOOLEAN:
                    return new Result(jsonRuleReader.nextBoolean());
                default:
                    return null;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private Result processEquals(JsonReader jsonRuleReader, String data) {
        try {
            JsonToken token = jsonRuleReader.peek();
            if (token == JsonToken.BEGIN_ARRAY) {
                jsonRuleReader.beginArray();
                Result left = apply(jsonRuleReader, data);
                Result right = apply(jsonRuleReader, data);
                jsonRuleReader.endArray();
                if (left.isBoolean() && right.isBoolean()) {
                    return new Result(new Boolean(left.getBooleanValue() == right.getBooleanValue()));
                }
                if (left.isLong() && right.isLong()) {
                    return new Result(new Boolean(left.getLongValue() == right.getLongValue()));
                }
            }
            return null;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private Result processGreaterThan(JsonReader jsonRuleReader, String data) {
        try {
            JsonToken token = jsonRuleReader.peek();
            if (token == JsonToken.BEGIN_ARRAY) {
                jsonRuleReader.beginArray();
                Result left = apply(jsonRuleReader, data);
                Result right = apply(jsonRuleReader, data);
                jsonRuleReader.endArray();
                if (left.isLong() && right.isLong()) {
                    return new Result(new Boolean(left.getLongValue() > right.getLongValue()));
                }
            }
            return null;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private Result processLessThan(JsonReader jsonRuleReader, String data) {
        try {
            JsonToken token = jsonRuleReader.peek();
            if (token == JsonToken.BEGIN_ARRAY) {
                jsonRuleReader.beginArray();
                Result left = apply(jsonRuleReader, data);
                Result right = apply(jsonRuleReader, data);
                jsonRuleReader.endArray();
                if (left.isLong() && right.isLong()) {
                    return new Result(new Boolean(left.getLongValue() < right.getLongValue()));
                }
            }
            return null;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private Result processAnd(JsonReader jsonRuleReader, String data) {
        try {
            JsonToken token = jsonRuleReader.peek();
            if (token == JsonToken.BEGIN_ARRAY) {
                jsonRuleReader.beginArray();
                Result left = apply(jsonRuleReader, data);
                Result right = apply(jsonRuleReader, data);
                jsonRuleReader.endArray();
                if (left.isBoolean() && right.isBoolean()) {
                    return new Result(new Boolean(left.getBooleanValue() && right.getBooleanValue()));
                }
            }
            return null;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private Result processOr(JsonReader jsonRuleReader, String data) {
        try {
            JsonToken token = jsonRuleReader.peek();
            if (token == JsonToken.BEGIN_ARRAY) {
                jsonRuleReader.beginArray();
                Result left = apply(jsonRuleReader, data);
                Result right = apply(jsonRuleReader, data);
                jsonRuleReader.endArray();
                if (left.isBoolean() && right.isBoolean()) {
                    return new Result(new Boolean(left.getBooleanValue() || right.getBooleanValue()));
                }
            }
            return null;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private Result processVar(JsonReader jsonRuleReader, String data) {
        try {
            JsonToken token = jsonRuleReader.peek();
            if (token == JsonToken.BEGIN_ARRAY) {
                jsonRuleReader.beginArray();
                Result left = apply(jsonRuleReader, data);
                Result right = apply(jsonRuleReader, data);
                jsonRuleReader.endArray();
                if (left.isBoolean() && right.isBoolean()) {
                    return new Result(new Boolean(left.getBooleanValue() || right.getBooleanValue()));
                }
            }
            return null;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
