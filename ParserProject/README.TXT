This project was developed using Eclipse, so if you have that installed, then it would 
be easiest to just open this project in Eclipse, go to Run->Run Configurations..., 
go to the Arguments tab, and then enter the name of the source code file and grammar 
file to test. After that, you can press Ctrl+F11 to run the program.

If you don't have Eclipse installed, then you can compile and run it from the command 
line. To do this, navigate to the directory with the java code (ParserProject/src), 
execute the command "javac *.java", and then execute the command 
"java ParserDriver sourcecode.llc grammar.txt". Make sure that your source code file 
and grammar file are both in the present working directory.