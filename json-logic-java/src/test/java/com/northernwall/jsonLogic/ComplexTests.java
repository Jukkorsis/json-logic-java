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
public class ComplexTests extends BaseTest {

    public ComplexTests() {
    }

    @Test
    public void TestAndWithContants1() {
        TestRunner(
                "{\"and\" : [{\">\" : [3,1]}, {\"<\" : [1,3]}] }",
                null,
                "((3 > 1) && (1 < 3))",
                "true",
                true);
    }

    @Test
    public void TestAndWithContants2() {
        TestRunner(
                "{\"and\" : [{\">\" : [4,5]}, {\"<\" : [1,3]}] }",
                null,
                "((4 > 5) && (1 < 3))",
                "false",
                false);
    }

    @Test
    public void TestAndWithContants3() {
        TestRunner(
                "{\"and\" : [{\">\" : [1,3]}, {\"<\" : [6,4]}] }",
                null,
                "((1 > 3) && (6 < 4))",
                "false",
                false);
    }

    @Test
    public void TestOrWithContants1() {
        TestRunner(
                "{\"or\" : [{\">\" : [3,1]}, {\"<\" : [1,3]}] }",
                null,
                "((3 > 1) || (1 < 3))",
                "true",
                true);
    }

    @Test
    public void TestOrWithContants2() {
        TestRunner(
                "{\"or\" : [{\">\" : [4,5]}, {\"<\" : [1,3]}] }",
                null,
                "((4 > 5) || (1 < 3))",
                "true",
                true);
    }

    @Test
    public void TestOrWithContants3() {
        TestRunner(
                "{\"or\" : [{\">\" : [2,7]}, {\"<\" : [6,4]}] }",
                null,
                "((2 > 7) || (6 < 4))",
                "false",
                false);
    }

}
