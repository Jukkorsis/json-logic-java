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
public class SimpleLogicTests extends BaseTest {

    public SimpleLogicTests() {
    }

    @Test
    public void TestAndWithContants1() throws ParseException, EvaluationException {
        TestRunner(
                "{ \"and\" : [true, true] }",
                null,
                "(true && true)",
                "true",
                true);
    }

    @Test
    public void TestAndWithContants2() throws ParseException, EvaluationException {
        TestRunner(
                "{ \"and\" : [true, false] }",
                null,
                "(true && false)",
                "false",
                false);
    }

    @Test
    public void TestOrWithContants1() throws ParseException, EvaluationException {
        TestRunner(
                "{ \"or\" : [true, true] }",
                null,
                "(true || true)",
                "true",
                true);
    }

    @Test
    public void TestOrWithContants2() throws ParseException, EvaluationException {
        TestRunner(
                "{ \"or\" : [true, false] }",
                null,
                "(true || false)",
                "true",
                true);
    }

    @Test
    public void TestOrWithContants3() throws ParseException, EvaluationException {
        TestRunner(
                "{ \"or\" : [false, false] }",
                null,
                "(false || false)",
                "false",
                false);
    }

}
