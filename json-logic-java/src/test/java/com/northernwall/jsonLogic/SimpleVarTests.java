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

import org.junit.Test;

/**
 *
 * @author Richard
 */
public class SimpleVarTests extends BaseTest {

    public SimpleVarTests() {
    }

    @Test
    public void TestVarSimpleString() {
        TestRunner(
                "{ \"var\" : [\"a\"] }",
                "{ \"a\" : \"good\", \"b\" : true, \"c\" : 42 }",
                "var->\"a\"",
                "var->\"a\"",
                "good");
    }

    @Test
    public void TestVarSimpleBoolean() {
        TestRunner(
                "{ \"var\" : [\"b\"] }",
                "{ \"a\" : \"good\", \"b\" : true, \"c\" : 42 }",
                "var->\"b\"",
                "var->\"b\"",
                true);
    }

    @Test
    public void TestVarSimpleNumber() {
        TestRunner(
                "{ \"var\" : [\"c\"] }",
                "{ \"a\" : \"good\", \"b\" : true, \"c\" : 42 }",
                "var->\"c\"",
                "var->\"c\"",
                42);
    }

    @Test
    public void TestVarComplexString() {
        TestRunner(
                "{ \"var\" : [\"champ.name\"] }",
                "{\"champ\" : {\"name\" : \"Fezzig\",\"height\" : 223},\"challenger\" : {\"name\" : \"Dread Pirate Roberts\",\"height\" : 183}}",
                "var->\"champ.name\"",
                "var->\"champ.name\"",
                "Fezzig");
    }

    @Test
    public void TestVarComplexNumber() {
        TestRunner(
                "{ \"var\" : [\"challenger.height\"] }",
                "{\"champ\" : {\"name\" : \"Fezzig\",\"height\" : 223},\"challenger\" : {\"name\" : \"Dread Pirate Roberts\",\"height\" : 183}}",
                "var->\"challenger.height\"",
                "var->\"challenger.height\"",
                183);
    }

    @Test
    public void TestVarComplexNumber2() {
        TestRunner(
                "{ \">\" : [{ \"var\" : [\"champ.height\"] }, { \"var\" : [\"challenger.height\"] }] }",
                "{\"champ\" : {\"name\" : \"Fezzig\",\"height\" : 223},\"challenger\" : {\"name\" : \"Dread Pirate Roberts\",\"height\" : 183}}",
                "(var->\"champ.height\" > var->\"challenger.height\")",
                "(var->\"champ.height\" > var->\"challenger.height\")",
                true);
    }

    @Test
    public void TestVarComplexNumber3() {
        TestRunner(
                "{\"and\" : [{ \">\" : [{ \"var\" : [\"champ.height\"] }, { \"var\" : [\"challenger.height\"] }] }, {\"==\" : [1,1]}] }",
                "{\"champ\" : {\"name\" : \"Fezzig\",\"height\" : 223},\"challenger\" : {\"name\" : \"Dread Pirate Roberts\",\"height\" : 183}}",
                "((var->\"champ.height\" > var->\"challenger.height\") && (1 == 1))",
                "((var->\"champ.height\" > var->\"challenger.height\") && true)",
                true);
    }

}
