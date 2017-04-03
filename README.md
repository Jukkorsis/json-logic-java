# json-logic-java
A Java implementation of http://jsonlogic.com/

# Maven
A maven release has not yet been made as the library is still under development

    <dependencies>
      <dependency>
        <groupId>com.northernwall</groupId>
        <artifactId>json-logic-java</artifactId>
        <version>1.0.0</version>
        <type>jar</type>
      </dependency>
    </dependencies>


# Usage
## One Time Evaluation
    String rule = "{\"cat\": [\"I love \", {\"var\":\"filling\"}, \" pie"\]}";
    String data = "{"filling":"apple", "temp":110}";
    Result result = jsonLogic.apply(rule, data);

## Reuse A Rule With different data
    String rule = "{\"cat\": [\"I love \", {\"var\":\"filling\"}, \" pie"\]}";
    JsonLogicTree tree = jsonLogic.parse(rule);
    
    Result result1 = tree.evaluate("{"filling":"apple", "temp":110}");
    Result result2 = tree.evaluate("{"filling":"blueberry", "temp":120}");
    Result result3 = tree.evaluate("{"filling":"key lime", "temp":115}");

# Tests
There are 95 test cases that are contained within the file ["BulkTests.txt"](https://github.com/Jukkorsis/json-logic-java/blob/master/json-logic-java/BulkTests.txt). Some of the test cases are from http://jsonlogic.com/operations.html while other I wrote. Each line in the file represents a test or a comment. Each line has 2 or 3 components sperated by a tab. The first component is the JsonLogic expression, the second (optional) component is the JSON data object, and the last component is the expected result.

Current 48 tests are failing, 47 tests are passing, of 95 total tests. The failing tests largely represent operations that have not yet been coded for. When all test cases pass intend to release the first version of json-logic-java
