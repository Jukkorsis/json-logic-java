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
import org.junit.Test;

/**
 *
 * @author Richard
 */
public class SimpleCompareTests {

    public SimpleCompareTests() {
    }

    @Test
    public void TestEqualsWithContants1() {
        String rule = "{ \"==\" : [1, 1] }";
        String data = null;

        JsonLogic jsonLogic = new JsonLogic();
        
        Result result = jsonLogic.apply(rule, data);
        Assert.assertEquals(true, result.isBoolean());
        Assert.assertEquals(true, result.getBooleanValue());
    }

    @Test
    public void TestEqualsWithContants2() {
        String rule = "{ \"==\" : [1, 3] }";
        String data = null;

        JsonLogic jsonLogic = new JsonLogic();
        
        Result result = jsonLogic.apply(rule, data);
        Assert.assertEquals(true, result.isBoolean());
        Assert.assertEquals(false, result.getBooleanValue());
    }

    @Test
    public void TestGreaterWithContants1() {
        String rule = "{ \">\" : [3, 1] }";
        String data = null;

        JsonLogic jsonLogic = new JsonLogic();
        
        Result result = jsonLogic.apply(rule, data);
        Assert.assertEquals(true, result.isBoolean());
        Assert.assertEquals(true, result.getBooleanValue());
    }

    @Test
    public void TestGreaterWithContants2() {
        String rule = "{ \">\" : [1, 1] }";
        String data = null;

        JsonLogic jsonLogic = new JsonLogic();
        
        Result result = jsonLogic.apply(rule, data);
        Assert.assertEquals(true, result.isBoolean());
        Assert.assertEquals(false, result.getBooleanValue());
    }

    @Test
    public void TestGreaterWithContants3() {
        String rule = "{ \">\" : [1, 3] }";
        String data = null;

        JsonLogic jsonLogic = new JsonLogic();
        
        Result result = jsonLogic.apply(rule, data);
        Assert.assertEquals(true, result.isBoolean());
        Assert.assertEquals(false, result.getBooleanValue());
    }

}
