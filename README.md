# WordCloud
Visualize the execution of a Java program using a Word Cloud. The word cloud will contain all of the classes in the program, and the words representing the classes will appear larger depending on how often calls are made in the class. From there, users will be able to dive deeper into the class in the form of it’s own word cloud, but instead of class names, it will contain functions and properties of the class (their words will also be sized according to their usages in execution). Our idea is that this will give programmers an incredibly easy visualization into their most used components, which can give them a great starting point for finding hot spots in code.

#### Directions:
* Build the jarfile for the analysis libraries with `gradle jar`. The output jarfile will appear in `build/libs/wordcloud.jar`. 
* Back up (or make a copy) of your project directory, as the instrumentation will directly modify the source code.
* Compile and run the instrumentation app at `Instrumentality.main`. The program expects two arguments, consisting of the target directory containing the code you wish to analyze, and then the filepath/filename for the output log file.
* Include the analysis libraries `wordcloud.jar` in your build, and compile your project. 
  * Example: `javac -cp ".:JARFILE_PATH" *.java`
* Run your built executable (example: `java -cp ".:JARFILE_PATH" MainClass`). At the end of program execution, the visualization tool should appear.
Initially, the word cloud will show the classes in your program. Word size will correspond to how often functions within said classes were called. Clicking on one of the classes will show which functions within said classes were called (with word size corresponding to call frequency). The exact number of calls to a class or method can be observed by hovering over its name in the word cloud.

#### Contributors
Mac Iverson, Isabelle Lowe, Parth Garg, Ben Shen, Peter Li
 
