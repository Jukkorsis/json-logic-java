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
import java.io.InputStream;
import java.io.InputStreamReader;
import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * @author Richard
 */
public class BulkTests {

    private int lineNumber = 1;
    private String line;
    private int passCount = 0;
    private int failCount = 0;

    public BulkTests() {
    }

    @Test
    public void BulkTest() throws ParseException, EvaluationException {
        try {
            JsonLogic jsonLogic = new JsonLogic();

            InputStream stream = new FileInputStream("BulkTests.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            line = reader.readLine();
            while (line != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    if (line.startsWith("#")) {
                        System.out.println(line);
                    } else {
                        runTest(jsonLogic);
                    }
                }
                line = reader.readLine();
                lineNumber = lineNumber + 1;
            }
            if (failCount > 0) {
                Assert.fail(failCount
                        + " failed tests, "
                        + passCount
                        + " passed tests, "
                        + (failCount + passCount)
                        + " total tests.");
            } else {
                System.out.println("All "
                        + passCount
                        + " tests passed!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void runTest(JsonLogic jsonLogic) {
        String[] parts = line.split("\t");
        if (parts.length < 2) {
            printError("test format is illegal");
            failCount++;
            return;
        }

        String rule;
        String data;
        String expectedResult;

        if (parts.length == 2) {
            rule = parts[0];
            data = null;
            expectedResult = parts[1];
        } else {
            rule = parts[0];
            data = parts[1];
            expectedResult = parts[2];
        }

        try {
            Result result = jsonLogic.apply(rule, data);
            if (!checkResult(expectedResult, result)) {
                return;
            }

            JsonLogicTree tree = jsonLogic.parse(rule);
            result = tree.evaluate(data);
            if (!checkResult(expectedResult, result)) {
                return;
            }

            tree.reduce();
            result = tree.evaluate(data);
            if (!checkResult(expectedResult, result)) {
                return;
            }
        } catch (Exception e) {
            printError(e.getClass().getSimpleName()
                    + ": "
                    + e.getMessage());
            failCount++;
            return;
        }
        passCount++;
    }

    private boolean checkResult(String expectedResult, Result result) {
        if (result == null) {
            printError("result is null");
            failCount++;
            return false;
        }
        if (!expectedResult.equals(result.getStringValue())) {
            printError("expecting "
                    + expectedResult
                    + " but actual result is "
                    + result.getStringValue());
            failCount++;
            return false;
        }
        return true;
    }

    private void printError(String message) {
        System.out.println("  Error on line "
                + lineNumber
                + " - "
                + message);
        System.out.println("    "
                + line);
    }

}
