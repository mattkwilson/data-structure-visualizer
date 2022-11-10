# Data Structure Visualizer

## How to run the project (note: this has only been tested on Windows)

1. Open the project in Intellij and set up configuration for running the DataStructureVisualizer.java class
2. Edit the configuration, and set the command line arguments in this order:
   1. Path to Java Project to be analyzed
   2. Name of the class containing the field of the data structure to be analyzed
   3. The name of the field within the class (must be a private field)
3. Run the project
4. Open the terminal and travel to the frontend directory
5. Ensure Node.js is installed on the computer and enter the commands:
   1. npm Install
   2. npm Start
6. Launch the React frontend in browser to see the visualization


# Milestone 1

## Summary
Our plan is to create an Intellij plugin for data structure visualization of Java code.
The data structure visualizer helps Java developers debug Java's built-in data structures in a time efficient manner.
It shows the history of changes that happened to a data structure while the code was running.
This way developers do not need to waste their time single-stepping through the code.
The history of the changes is illustrated visually which is more intuitive than most debuggers that display information as plain text.

## Analysis

### Main Stages
 1. Static analysis of the program to find data structures we support
 2. User selects data structure for visualization
 3. Dynamic analysis of program to build snapshots of the data structure
 4. Display window in Intellij with the snapshots
 5. User can step through snapshots to view the contents of the data structure at various points throughout the program

### Inputs and Outputs
 - Input:  Java code and a data structure for visualization
 - Output: Graphical representation of the history of the data structure

### What Data to Pass Around
 - Data structure history which includes: 
    - snapshot of the data structure values
    - file name and line number that triggered the change
    - call stack when the data structure was change

### What we will Implement
 - Static analyzer Identifying which built-in data structures can be visualized.
 - Dynamic analyzer keeping track of the changes to the selected data structure.
 - Instrumentation of Java code with the dynamic analyzer.
 - Front end that shows a visual representation of the history of changes to the selected data structure.

### What we will use Frameworks to Handle
 - Parsing the Java code

## Target Aspects
 - Real-world programming language (Java)
 - Visualisation Component

## Scope
 - Original idea was to inject arguments into class methods, then visualize the data structures as the methods are executed
 - After discussion with professor and TA, we decided to focus on the visualizing of data structures dynamically at runtime

## Division of Responsibilities
 - Implementation - everyone
 - User Study 1 - Edvin, Mert
 - User Study 2 - Tarek, Jeffrey
 - Creating the video - everyone

## Timeline
 - Milestone 1 - Thursday June 9th
 - User Study 1 - Friday June 10th
 - Start Implementing - Friday June 10th
 - Final User Study - Wednesday June 15th
 - Complete Implementation - Thursday June 16th
 - Milestone 2 Due Friday June 17th
 - Video Due Tuesday June 21st

# Milestone 2

## Summary of changes:
 - Instead of developing an IntelliJ plugin, we decided to use a React frontend with offline dynamic analysis.
 - The data structures we decided to visualize are Java's built-in List and Map.
 - The tool can track and visualize private fields of List and Map types.

## Progress
 - Designed and implemented our backend
    - Created an Analysis and Instrumentor class to perform our dynamic analysis
    - The instrumentor instruments the code of an input Java project 
        - Takes in a class and field name of a collection data type
        - Finds all method calls to that field
        - Injects call to analyzer
        - Analyzer collects the data to output to Json for the frontend visualization
    - The instrumented code is then run to produce an output JSON file which can be handled by the frontend
 - Designed and implemented our Frontend 
    - Reads the outputted JSON from the backend and converts it into a visualization
        - Able to choose between which instance of the data structure to visualize
        - Allows “stepping” through the data structure, visualizing the changes that occur and the line they occur on
        - Shows the additions and deletions in colour
 - Can currently track any private field that is declared with the following interface types:
    - List<>
    - Map<>

## User Study 1 Feedback
Our user study involved two parts which encapsulated our initial and finalized project idea. 
The first part provided static images and sample Java code which would output those images. 
The second part showcased dynamic progress via images which showed line by line changes of a variable / data structure. 
The users found visual representations of the Java data structures easy to understand, they were able to match Java code 
to the image it would output. Looking at the dynamic, step-by-step, images the users were able to understand what was being provided.
Both users found our project to be useful for certain cases, one stated he would not use it on a daily basis but would use it for
certain things such as solving and analyzing algorithm questions. The other user stated he would use our project idea for debugging
and leetcoding. He also thought it would be useful for teaching. Some negative feedback we received were our project having specific
use cases only and one user was confused on what he was looking at on the step-by-step images. 

## Language and Libraries/Framework/Tools

### Language
 - Java
 - HTML
 - Javascript
 - CSS

### Libraries and Frameworks

#### Backend
 - JavaParser: Library for parsing Java code into an AST
 - JSON-Java (org.json): library for writing a Java object into a JSON file on disk

#### Frontend
 - React: User interface framework
 - Node.js: JavaScript runtime environment

## Final User Study
We will be performing a controlled experiment with a group of four users divided into two groups, one control group and one user group. Each group will be given a Java program which has a few hard to find bugs associated with a collection type. It will be up to the users to try and find as many bugs as they can within a given time limit. The user group will get to use our visualization tool. We will then compare the results between the two groups to see if our visualization tool helped the users to find more bugs.

## Scope (changes)
 - Add support for all built in Java List<> and Map<> subtypes 
 - Visualize associated lines of code for each snapshot in frontend
 - Update design of frontend
![image](https://media.github.students.cs.ubc.ca/user/4887/files/837e35a2-b38c-475e-b0b2-16254093cffb)


## Implementation and Evaluation Plan

### Frontend changes
 - Add list of steps on right hand side of page, used for seeing total number of steps, and traverse between them easier
 - Add more colour 
 - Add 3 lines of the current steps code (including the line before & after)

### Backend changes
 - Update the instrumentor to handle injection for subtype methods
 - Update analyzer to handle new subtypes
 - Parse surrounding lines of code associated with every snapshot and add to injection 

## Updated Timeline
 - ✅ Informal Planned Analysis - Tuesday June 7th (meeting - 9pm)
 - ✅ ~~User Study 1 - Wednesday June 8th~~ *** MOVED
 - ✅ Milestone 1 - Thursday June 9th (meeting)
 - ✅ Start Implementing - Friday June 10th (meeting)
 - ✅ User Study 1 - Sunday June 12th
 - ✅ ~~Final User Study - Wednesday June 15th~~ *** MOVED
 - ✅ ~~Complete Implementation - Thursday June 16th~~ *** MOVED
 - ✅ Milestone 2 Due Friday June 17th
 - ✅ Complete Implementation - Friday June 17th
 - ✅ Final User Study - Saturday June 18th
 - ✅ Video Due Tuesday June 21st

