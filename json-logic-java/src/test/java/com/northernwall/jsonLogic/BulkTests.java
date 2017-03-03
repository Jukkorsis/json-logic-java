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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * @author Richard
 */
public class BulkTests {

    public BulkTests() {
    }

    @Test
    public void BulkTest() throws ParseException, EvaluationException {
        try {
            JsonLogic jsonLogic = new JsonLogic();

            InputStream stream = new FileInputStream("BulkTests.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            int lineNumber = 1;
            while (runTest(jsonLogic, reader, lineNumber++)) {
            }
            System.out.println();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private boolean runTest(JsonLogic jsonLogic, BufferedReader reader, int lineNumber) throws IOException, ParseException, EvaluationException {
        String line = reader.readLine();
        if (line == null) {
            return false;
        }
        line = line.trim();
        if (line.isEmpty()) {
            return true;
        }

        String[] parts = line.split("\t");
        if (parts.length == 1) {
            System.out.println();
            System.out.println("Bulk Tests: " + parts[0]);
            return true;
        }
        System.out.print(lineNumber + " ");
        
        String rule = null;
        String data = null;
        String expectedResult = null;

        if (parts.length == 2) {
            rule = parts[0];
            expectedResult = parts[1];
        } else {
            rule = parts[0];
            data = parts[1];
            expectedResult = parts[2];
        }

        Result result = jsonLogic.apply(rule, data);
        Assert.assertEquals(expectedResult, result.getStringValue());

        JsonLogicTree tree = jsonLogic.parse(rule);
        tree.reduce();
        result = tree.evaluate(data);
        Assert.assertEquals(expectedResult, result.getStringValue());

        return true;
    }

}
