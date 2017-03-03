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
     *
     * @param rule
     * @param data
     * @return
     * @throws com.northernwall.jsonLogic.ParseException
     */
    public Result apply(String rule, String data) throws ParseException, EvaluationException {
        return parse(rule).evaluate(data);
    }

    /**
     * Parses the rules into a reusable tree which can be evaluated many times.
     *
     * @param rule
     * @return
     * @throws com.northernwall.jsonLogic.ParseException
     */
    public JsonLogicTree parse(String rule) throws ParseException {
        return new JsonLogicTree(parse(gson.newJsonReader(new StringReader(rule))), gson);
    }

    private Node parse(JsonReader jsonReader) throws ParseException {
        Node tree = null;
        try {
            JsonToken token = jsonReader.peek();
            switch (token) {
                case BEGIN_OBJECT:
                    jsonReader.beginObject();
                    String operation = jsonReader.nextName();
                    switch (operation) {
                        case "==":
                            tree = parseEquals(jsonReader);
                            break;
                        case ">":
                            tree = parseGreaterThan(jsonReader);
                            break;
                        case "<":
                            tree = parseLessThan(jsonReader);
                            break;
                        case "and":
                            tree = parseAnd(jsonReader);
                            break;
                        case "or":
                            tree = parseOr(jsonReader);
                            break;
                        case "!":
                            tree = parseNot(jsonReader);
                            break;
                        case "var":
                            tree = parseVar(jsonReader);
                            break;
                    }
                    jsonReader.endObject();
                    break;
                case NUMBER:
                    tree = new ConstantNode(new Result(jsonReader.nextLong()));
                    break;
                case BOOLEAN:
                    if (jsonReader.nextBoolean()) {
                        tree = TRUE_NODE;
                    } else {
                        tree = FALSE_NODE;
                    }
                    break;
            }
        } catch (IOException ex) {
            throw new ParseException(ex.getMessage(), ex);
        }
        return tree;
    }

    private Node parseEquals(JsonReader jsonReader) throws ParseException {
        Node tree = null;
        try {

            JsonToken token = jsonReader.peek();
            if (token == JsonToken.BEGIN_ARRAY) {
                jsonReader.beginArray();
                tree = new EqualsNode(parse(jsonReader), parse(jsonReader));
                jsonReader.endArray();
            }
        } catch (IOException ex) {
            throw new ParseException(ex.getMessage(), ex);
        }
        return tree;
    }

    private Node parseGreaterThan(JsonReader jsonReader) throws ParseException {
        Node tree = null;
        try {
            JsonToken token = jsonReader.peek();
            if (token == JsonToken.BEGIN_ARRAY) {
                jsonReader.beginArray();
                tree = new GreaterThanNode(parse(jsonReader), parse(jsonReader));
                jsonReader.endArray();
            }
        } catch (IOException ex) {
            throw new ParseException(ex.getMessage(), ex);
        }
        return tree;
    }

    private Node parseLessThan(JsonReader jsonReader) throws ParseException {
        Node tree = null;
        try {
            JsonToken token = jsonReader.peek();
            if (token == JsonToken.BEGIN_ARRAY) {
                jsonReader.beginArray();
                tree = new LessThanNode(parse(jsonReader), parse(jsonReader));
                jsonReader.endArray();
            }
        } catch (IOException ex) {
            throw new ParseException(ex.getMessage(), ex);
        }
        return tree;
    }

    private Node parseAnd(JsonReader jsonReader) throws ParseException {
        AndNode andNode = null;
        try {
            JsonToken token = jsonReader.peek();
            if (null != token) {
                switch (token) {
                    case BEGIN_ARRAY:
                        jsonReader.beginArray();
                        andNode = new AndNode(parse(jsonReader), parse(jsonReader));
                        while (jsonReader.peek() != JsonToken.END_ARRAY) {
                            andNode.add(parse(jsonReader));
                        }
                        jsonReader.endArray();
                        break;
                }
            }
        } catch (IOException ex) {
            throw new ParseException(ex.getMessage(), ex);
        }
        return andNode;
    }

    private Node parseOr(JsonReader jsonReader) throws ParseException {
        OrNode orNode = null;
        try {
            JsonToken token = jsonReader.peek();
            if (null != token) {
                switch (token) {
                    case BEGIN_ARRAY:
                        jsonReader.beginArray();
                        orNode = new OrNode(parse(jsonReader), parse(jsonReader));
                        while (jsonReader.peek() != JsonToken.END_ARRAY) {
                            orNode.add(parse(jsonReader));
                        }
                        jsonReader.endArray();
                        break;
                    default:
                        throw new ParseException("Expecting an array of two boolean expressions");
                }
            }
        } catch (IOException ex) {
            throw new ParseException(ex.getMessage(), ex);
        }
        return orNode;
    }

    private Node parseNot(JsonReader jsonReader) throws ParseException {
        Node tree = null;
        try {
            JsonToken token = jsonReader.peek();
            if (null != token) {
                switch (token) {
                    case BEGIN_ARRAY:
                        jsonReader.beginArray();
                        tree = new NotNode(parse(jsonReader));
                        jsonReader.endArray();
                        break;
                    case BOOLEAN:
                        tree = new NotNode(parse(jsonReader));
                        break;
                    default:
                        throw new ParseException("Expecting a boolean or an array of one boolean");
                }
            }
        } catch (IOException ex) {
            throw new ParseException(ex.getMessage(), ex);
        }
        return tree;
    }

    private Node parseVar(JsonReader jsonReader) throws ParseException {
        Node tree = null;
        try {
            JsonToken token = jsonReader.peek();
            if (token == JsonToken.BEGIN_ARRAY) {
                jsonReader.beginArray();
                String name = jsonReader.nextString();
                token = jsonReader.peek();
                if (null != token) {
                    switch (token) {
                        case END_ARRAY:
                            tree = new VarNode(name);
                            break;
                        case NUMBER:
                            tree = new VarNode(name, new Result(jsonReader.nextLong()));
                            break;
                        case STRING:
                            tree = new VarNode(name, new Result(jsonReader.nextString()));
                            break;
                        case BOOLEAN:
                            if (jsonReader.nextBoolean()) {
                                tree = new VarNode(name, TRUE_RESULT);
                            } else {
                                tree = new VarNode(name, FALSE_RESULT);
                            }
                            break;
                        default:
                            break;
                    }
                }
                jsonReader.endArray();
            }
        } catch (IOException ex) {
            throw new ParseException(ex.getMessage(), ex);
        }
        return tree;
    }

}
