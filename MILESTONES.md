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
