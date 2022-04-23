# Milestone 1
## Discussions within team
* Have spent some time brainstorming some ideas
 * Maybe we can use Git Hooks to give users more visibility into their code?
 * Forced execution of branches
  * This may not fit the project’s goal of ”program analysis”
 * Code quality review (i.e. a linter)
 * Performance analysis (static analysis via Big O, dynamic analysis via detection of bottlenecks during execution, etc.)
* TA said our ideas may not fit the project’s requirements
 * Suggested something like a code coverage tool (ex: IntelliJ)
* The main point is that we need to figure out a useful tool that analyzes the program itself
## Tasks for next week
* Continue brainstorming ideas
* Have an idea picked out by the end of the week

# Milestone 2
## Planned idea
* The idea is to analyze recursive problems. For recursive algorithms, since each recursive call creates a whole new set of local variables in the stack/heap, we might notice an increased amount of memory usage. We could then plot a graph that shows the memory usage as the number of recursive calls increase. This graph could then be used as an indicator to see if maybe the recursive solution should be updated to use more memoization or some other strategy to optimize memory usage.
* Visualizing a recursion problem by drawing a recursion tree might help better understand the problem and help in debugging.
* Analyse time performance as well by gathering data on when and how often certain calls are made.
* Possibly implement sampling at certain intervals, to ensure that the application can run at full performance during analysis.
## TA feedback
* Need to check this idea with Alex. An email has been sent. Waiting for a reply.

The recursive idea is still tentative. As a backup option, we plan to visualize the software execution using animations, similar to the solar system example from class. Our next step is to fix on an idea early next week and get started with the implementaion.

# Milestone 3
## Mockup
![image](https://media.github.students.cs.ubc.ca/user/1032/files/e7f71b00-45ae-11ec-8cfd-db4eb07dc487)

Larger names mean that these classes are called more often. Those making use of recursion then would appear much larger, highlighting their usage. The (rough) sketch shows a way to drill down into classes to view the usages of their properties and functions in a similar manner. 

## Project Idea and Motivation
Our plan is to visualize the execution of a Java program using a Word Cloud or something of similar feel. We expect the result to be an image composed of words of various sizes and orientations, and these words should at first represent all the different classes that took part in the particular run of the code. Our idea is that this will give programmers an incredibly easy visualization into their most used components, which can give them a great starting point for finding hot spots in code. 

For example, if the user is unsatisfied with the runtime of their program, looking at the most used classes can be a great way to look for areas where optimization can have a dramatic impact. Or, if the user is looking to increase test coverage, it would be very useful to ensure that the most heavily-used classes are fully covered. The tool may prove to be a very user-friendly way to get beginning programmers to start thinking more deeply about how their programs execute and perform, as it provides a visually appealing way to observe program execution.

## Planned Features
We can have a word cloud containing all of the classes in the program, and the words representing the classes will appear larger depending on how often calls are made in the class. From there, users will be able to dive deeper into the class in the form of it’s own word cloud, but instead of class names, it will contain functions and properties of the class (their words will also be sized according to their usages in execution). Our idea is that this will give programmers an incredibly easy visualization into their most used components, which can give them a great starting point for finding hot spots in code. 

## User studies
User 1 notes that they see potential in the idea. They like that the project appears to be able to determine which parts of the codebase are being underused/overused, and what is possibly being used inefficiently. They do express concern that it could not be immediately obvious to an inexperienced developer as to what could be causing problems only from looking at the word cloud, suggesting further data to somehow be incorporated into it. 

We received similar reception from User 2, where they mused that something like this would've been useful back when they were a student, since they feel such a tool would have provided a more intuitive means to understanding recursion. User 2 suggested that loops do not appear to be explicitly considered, and opined that there should be some way to differentiate between recursion and iteration. 

User 3 appreciated the user-friendly look of the tool. Thought the visualization is a promising way to quickly see the most used/important classes. They agreed that it would be a quick way to see what classes need the most attention in terms of optimization, code coverage, and utilization. However, they also noted that, based on the parameters given to the program, our result has the chance to look substantially different on each execution. It may be a bit of extra work for the user to run the program many times with different parameters to find common patterns. 

## Changes to original design
Originally, we were expecting to analyze memory in recursive problems. However, due to difficulty determining the best course for implementation, we have decided instead on a visual representation of the execution of a Java program, namely in a word cloud format. This aims to provide a more visual and interactive approach to displaying information for the developer to understand.

Thanks to our user studies, there are a few more ideas we have considered; Normalization of the sizes may be necessary to prevent words in the cloud from being too big or too small. Instead of arbitrary coloration, we could utilize color as a heatmap to illustration how much execution time is spent within certain classes or functions, providing further information in a stylistic way. There could also be an alternative to clicking, where a search query could also be used to select classes to drill down into. This would be helpful to locate harder-to-find classes in the word cloud in large programs.

## Division of Labour
We are planning to divide the tasks up into 3 main components: Analysis, Integration and Word Cloud. Two people will be assigned for analysis, where they will focus on providing logging for the program, as well as instrumenting the data gathered. The integration role will take the gathered information and determine a way to format it such that it can be used with the Word Cloud. This will be filled by one person. The Word Cloud task is to provide visualization of the data as a word cloud, as well as the ability to interact with it, and this will be assigned to two people. We will finalize who will fulfill what role after the Dynamic Program Analysis lecture. 

## Roadmap
As this roadmap was developed recently, its length is about two weeks. We have a general idea as to what to expect at the end of each week. The first seven days, we intend to have a minimal, yet functioning product, where we will have a basic word cloud that displays the frequency of which classes are called. This means within it, the logging, instrumentation will be developed and successfully hooked to the front-end, which will produce a word cloud of classes. The remaining days will expand on this, with further interaction with classes, being able to drill down into classes and functions for more precise analysis. Analysis will become much more complex, as well as that the interactive components of the word cloud will be fully developed and finalized.

## Progress so far
We have begun looking into possible tools to help us with the development, notably https://github.com/kennycason/kumo and wordle, which we plan to use one of them as the basis for our word cloud implementation. We will also begin development of a backend for providing analytics. We currently plan to use offline dynamic analysis, instrumenting the source code.

# Milestone 4
## Status of Implementation
We have begun development for the project where currently, Peter and Ben have been assigned to the dynamic analysis component, while Mac, Isabella and Parth are working on word cloud visualization and interaction. These positions may be subject to change as the complexity of each task is fully realized. We have decided to go ahead with an offline dynamic analysis with source code instrumentation, and implementing the wordle algorithm to be used by a GUI framework, where Swing has been tetatively elected. The language being analysed and used for development is solely Java.

## Plans for Final User Study
Since it is likely that most users from UBC will still have their CPSC210 project, we could ask them to run the tool on that. By doing so, they can hopefully get some personalized insight on our product. If not, they can either find another Java program to use, or we can provide them a test program to play around with using the tool.

## Timeline for Remaining Days
Since the roadmap was established very recently, it remains a reasonbly accurate representation of our planned timeline. 

# Milestone 5
## Status of Final User Study
We gave users our prototype build, and asked them to try it out on their programs, or just the example program if they didn't have one, and explained to them future
features that we planned to add. User 1 was interested by the visualization, commending the ability to look at different classes case-by-case. However, when told about adding fields to the cloud as well as methods, they disagreed with the importance of fields, stating that field use didn't feel necessary to analyze because they generally don't impact performance and got in the way of being able to distinguish methods. For that reason, we decided to omit fields from our word clouds. User 2 expressed similar likes compared to User 1, although they noted that unlike the mock-up sketch, the current word cloud only had words rightside up. Since this word cloud is meant to be more utilitarian than stylistic, we made the decision to keep words in the word cloud exclusively in the rightside up position, since sideways configurations would affect legibility. 

## Plans for Final Video
After we solidfy the implementation, we will proceed with making the video. The current idea is to do a simple explanation, tutorial and demonstration of the use of our program. 

## Timeline for Remaining Days
We need to connect the analysis component with the visualization, which should be the only remaining task to complete the product. After this is completed, we will perform the user study and create the video. If we have time, we will further refine the visualization and features. 
