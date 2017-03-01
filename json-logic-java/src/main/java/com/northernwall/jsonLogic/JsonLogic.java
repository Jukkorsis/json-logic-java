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

    static final Result TRUE_RESULT = new Result(true);
    static final Result FALSE_RESULT = new Result(false);
    static final ConstantNode TRUE_NODE = new ConstantNode(TRUE_RESULT);
    static final ConstantNode FALSE_NODE = new ConstantNode(FALSE_RESULT);

    private final Gson gson;

    public JsonLogic() {
        gson = new Gson();
    }

    /**
     * Equivalent to "parse(rule).evaluate(data);"
     * @param rule
     * @param data
     * @return 
     */
    public Result apply(String rule, String data) {
        return parse(rule).evaluate(data);
    }

    /**
     * Parses the rules into a reusable tree which can be evaluated many times.
     * @param rule
     * @return 
     */
    public JsonLogicTree parse(String rule) {
        return new JsonLogicTree(parse(gson.newJsonReader(new StringReader(rule))), gson);
    }
    
    private Node parse(JsonReader jsonRuleReader) {
        Node tree = null;
        try {
            JsonToken token = jsonRuleReader.peek();
            switch (token) {
                case BEGIN_OBJECT:
                    jsonRuleReader.beginObject();
                    String operation = jsonRuleReader.nextName();
                    switch (operation) {
                        case "==":
                            tree = parseEquals(jsonRuleReader);
                            break;
                        case ">":
                            tree = parseGreaterThan(jsonRuleReader);
                            break;
                        case "<":
                            tree = parseLessThan(jsonRuleReader);
                            break;
                        case "and":
                            tree = parseAnd(jsonRuleReader);
                            break;
                        case "or":
                            tree = parseOr(jsonRuleReader);
                            break;
                        case "var":
                            tree = parseVar(jsonRuleReader);
                            break;
                    }
                    jsonRuleReader.endObject();
                    break;
                case NUMBER:
                    tree = new ConstantNode(new Result(jsonRuleReader.nextLong()));
                    break;
                case BOOLEAN:
                    if (jsonRuleReader.nextBoolean()) {
                        tree = TRUE_NODE;
                    } else {
                        tree = FALSE_NODE;
                    }
                    break;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return tree;
    }

    private Node parseEquals(JsonReader jsonRuleReader) {
        Node tree = null;
        try {

            JsonToken token = jsonRuleReader.peek();
            if (token == JsonToken.BEGIN_ARRAY) {
                jsonRuleReader.beginArray();
                tree = new EqualsNode(parse(jsonRuleReader), parse(jsonRuleReader));
                jsonRuleReader.endArray();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return tree;
    }

    private Node parseGreaterThan(JsonReader jsonRuleReader) {
        Node tree = null;
        try {
            JsonToken token = jsonRuleReader.peek();
            if (token == JsonToken.BEGIN_ARRAY) {
                jsonRuleReader.beginArray();
                tree = new GreaterThanNode(parse(jsonRuleReader), parse(jsonRuleReader));
                jsonRuleReader.endArray();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return tree;
    }

    private Node parseLessThan(JsonReader jsonRuleReader) {
        Node tree = null;
        try {
            JsonToken token = jsonRuleReader.peek();
            if (token == JsonToken.BEGIN_ARRAY) {
                jsonRuleReader.beginArray();
                tree = new LessThanNode(parse(jsonRuleReader), parse(jsonRuleReader));
                jsonRuleReader.endArray();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return tree;
    }

    private Node parseAnd(JsonReader jsonRuleReader) {
        Node tree = null;
        try {
            JsonToken token = jsonRuleReader.peek();
            if (token == JsonToken.BEGIN_ARRAY) {
                jsonRuleReader.beginArray();
                tree = new AndNode(parse(jsonRuleReader), parse(jsonRuleReader));
                jsonRuleReader.endArray();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return tree;
    }

    private Node parseOr(JsonReader jsonRuleReader) {
        Node tree = null;
        try {
            JsonToken token = jsonRuleReader.peek();
            if (token == JsonToken.BEGIN_ARRAY) {
                jsonRuleReader.beginArray();
                tree = new OrNode(parse(jsonRuleReader), parse(jsonRuleReader));
                jsonRuleReader.endArray();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return tree;
    }

    private Node parseVar(JsonReader jsonRuleReader) {
        Node tree = null;
        try {
            JsonToken token = jsonRuleReader.peek();
            if (token == JsonToken.BEGIN_ARRAY) {
                jsonRuleReader.beginArray();
                String name = jsonRuleReader.nextString();
                token = jsonRuleReader.peek();
                if (token == JsonToken.END_ARRAY) {
                    tree = new VarNode(name);
                } else {
                    //todo Process default value, second optional element in the array.
                }
                jsonRuleReader.endArray();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return tree;
    }

}