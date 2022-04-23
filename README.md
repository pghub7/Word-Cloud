Directions:
* Build the jarfile for the analysis libraries with `gradle jar`. The output jarfile will appear in `build/libs/wordcloud.jar`. 
* Back up (or make a copy) of your project directory, as the instrumentation will directly modify the source code.
* Compile and run the instrumentation app at `Instrumentality.main`. The program expects two arguments, consisting of the target directory containing the code you wish to analyze, and then the filepath/filename for the output log file.
* Include the analysis libraries `wordcloud.jar` in your build, and compile your project. 
  * Example: `javac -cp ".:JARFILE_PATH" *.java`
* Run your built executable (example: `java -cp ".:JARFILE_PATH" MainClass`). At the end of program execution, the visualization tool should appear.
Initially, the word cloud will show the classes in your program. Word size will correspond to how often functions within said classes were called. Clicking on one of the classes will show which functions within said classes were called (with word size corresponding to call frequency). The exact number of calls to a class or method can be observed by hovering over its name in the word cloud.

#### Contributors
Mac Iverson, Isabelle Lowe, Parth Garg, Ben Shen, Peter Li
 
