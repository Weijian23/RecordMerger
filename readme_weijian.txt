Structure:
The application can be split into two groups by usages: main class and helper classes. Each helper class handles one type of file or one type of job.
The helper classes were designed by following the builder pattern. There is a FileHelp interface describing a root path and required behaviors. Every solid helper was implemented from that interface. 
RecordMerger: Main class, use helper classes to get the final output.
DataRecord: A data structure to remember a row in a table.
FileHelper: Interface.
CSVHelper: Parse and read CSV.
HTMLHelper: Parse and read HTML.
MergeHelper: Merge files.
MyUnitTest: A simlpe unit test.

Extendability and Maintainess:
The application is currently only able to handles CSV and HTML. In the future when new file types coming in, we need to add new helper classes for new tyes. And in the main class add new cases for them. Nothing needs to be changed in other helper classes or merge functions.

How to run:
There is a jar file in "\out\artifacts\veeva_java_test_jar". 
Please first redirect to "src" folder, from there you can run the application with the following command:
java -jar YOUR_PATH_TO_THIS_FOLDER\veeva_java_test\out\artifacts\veeva_java_test_jar\veeva_java_test.jar first.html second.csv

About the output:
You can find "combined.csv" in veeve_java_test folder.
"combined.csv" is a table contains no duplicate id. 
Sorted by id in ascending order. 
Merged columns from all the input files.
No duplicate columns.
It handles any number of input files, any number of columns, any language.