This program will calculate halstead complexity and output the values in the console.

IN-DEPTH:
My program will parse through all the folders in the "osp" folder.
It will then take every .java file in the osp folder and its subdirectories. 
From those files it will convert the entire text portion into a string.
The string will then be passed into a parser method which uses the ASTParser class.
The parser class will then check which nodes are visible and increment integer counters,
and add the name values to an array list.
The array list will be sorted to a hash set to calculate the unique amount of operands/operators.
Using all that information it will calculate the halstead complexity and output it into the console.


TESTS:
My program uses the open source program Hibernate search
http://help.eclipse.org/mars/index.jsp?topic=%2Forg.eclipse.jdt.doc.isv%2Freference%2Fapi%2Forg%2Feclipse%2Fjdt%2Fcore%2Fdom%2FASTParser.html
Which was updated 10 months ago as a test case.
To use custom test cases see the next section.
If you wish to change the actual values go to the method:
public static void halsteadComplex() 
Go to line 173
Change the values of n1, n2, tN1, and tN2.

HOW TO CHANGE PARSED FILES:
Go into the project folder
Place the folder or file/s that you want parsed into the "osp" folder.
Run the program (See next section)


HOW TO RUN:
Open the .project file in Eclipse Studio
Go to the Gradle configuration and Go to Gradle -> Refresh Gradle
Run Program from the class labeled main


HALSTEAD VARIABLES:
Operators used:
https://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-ZeroToThree
Variables declarations (int, float, double, etc...)
Class declarations (ASTParser, etc)

Operands:
NumberLiteral 
SimpleName

EXTRA DEPENDENCIES: 
https://mvnrepository.com/artifact/org.apache.commons/commons-io
To parse all folders and files, this is already included in the gradle config

