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

import junit.framework.Assert;

/**
 *
 * @author Richard
 */
public class BaseTest {

    protected void TestRunner(String rule, String data, String printFull, String printReduced, boolean expectedResult) throws ParseException, EvaluationException {
        JsonLogic jsonLogic = new JsonLogic();

        Result result = jsonLogic.apply(rule, data);
        Assert.assertEquals(true, result.isBoolean());
        Assert.assertEquals(false, result.isLong());
        Assert.assertEquals(false, result.isString());
        Assert.assertEquals(expectedResult, result.getBooleanValue());

        JsonLogicTree tree = jsonLogic.parse(rule);
        Assert.assertEquals(printFull, tree.treeToString());
        tree.reduce();
        Assert.assertEquals(printReduced, tree.treeToString());
        result = tree.evaluate(data);
        Assert.assertEquals(true, result.isBoolean());
        Assert.assertEquals(false, result.isLong());
        Assert.assertEquals(false, result.isString());
        Assert.assertEquals(expectedResult, result.getBooleanValue());
    }

    protected void TestRunner(String rule, String data, String printFull, String printReduced, long expectedResult) throws ParseException, EvaluationException {
        JsonLogic jsonLogic = new JsonLogic();

        Result result = jsonLogic.apply(rule, data);
        Assert.assertEquals(false, result.isBoolean());
        Assert.assertEquals(true, result.isLong());
        Assert.assertEquals(false, result.isString());
        Assert.assertEquals(expectedResult, result.getLongValue());

        JsonLogicTree tree = jsonLogic.parse(rule);
        Assert.assertEquals(printFull, tree.treeToString());
        tree.reduce();
        Assert.assertEquals(printReduced, tree.treeToString());
        result = tree.evaluate(data);
        Assert.assertEquals(false, result.isBoolean());
        Assert.assertEquals(true, result.isLong());
        Assert.assertEquals(false, result.isString());
        Assert.assertEquals(expectedResult, result.getLongValue());
    }

    protected void TestRunner(String rule, String data, String printFull, String printReduced, String expectedResult) throws ParseException, EvaluationException {
        JsonLogic jsonLogic = new JsonLogic();

        Result result = jsonLogic.apply(rule, data);
        Assert.assertEquals(false, result.isBoolean());
        Assert.assertEquals(false, result.isLong());
        Assert.assertEquals(true, result.isString());
        Assert.assertEquals(expectedResult, result.getStringValue());

        JsonLogicTree tree = jsonLogic.parse(rule);
        Assert.assertEquals(printFull, tree.treeToString());
        tree.reduce();
        Assert.assertEquals(printReduced, tree.treeToString());
        result = tree.evaluate(data);
        Assert.assertEquals(false, result.isBoolean());
        Assert.assertEquals(false, result.isLong());
        Assert.assertEquals(true, result.isString());
        Assert.assertEquals(expectedResult, result.getStringValue());
    }

}
