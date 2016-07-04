# FileIndexer

## Description
Program to read a chosen directory and output the names of the files within it (along with their respective times of creation, last access, and last modified) to a CSV file in the directory.

## Why would I want to use this?
The initial purpose of this program is to help keep track of any new additions into a folder. By using this program along with a spreadsheet viewer, such as Excel, you will be able to sort by newest to oldest. However, the program could by modified to list multiple attributes of each file in a directory for another use.

## Requirements
Java 8+, as it uses the new time classes

## How to Use
Download the FileIndexer.jar in the root folder (or compile the classes in the /bin/ folder into a jar file with FileIndexer as the main class) to somewhere on your computer. After that, run it through the command prompt with `java -jar FileIndexer.jar`, and follow the on-screen prompts.


## Sample
Here is the sample output for the directory of this Eclipse project.
```
Begin execution of FileIndexer v1.0
Choose the root directory to analyze.
Qt: Untested Windows version 10.0 detected!	--> [This is a message related to the FileChooser class. However, it shouldn't have any impact on the program.]
The media directory is now C:\Users\Castor\Documents\workspace\FileIndexer
Begin analysis of [C:\Users\Castor\Documents\workspace\FileIndexer\]
 Analyzing [\.git\]
  Analyzing [\.git\hooks\]
  Analyzing [\.git\info\]
  Analyzing [\.git\logs\]
   Analyzing [\.git\logs\refs\]
    Analyzing [\.git\logs\refs\heads\]
  Analyzing [\.git\objects\]
   Analyzing [\.git\objects\4e\]
   Analyzing [\.git\objects\99\]
   Analyzing [\.git\objects\bd\]
   Analyzing [\.git\objects\cd\]
   Analyzing [\.git\objects\info\]
   Analyzing [\.git\objects\pack\]
  Analyzing [\.git\refs\]
   Analyzing [\.git\refs\heads\]
   Analyzing [\.git\refs\tags\]
 Analyzing [\.settings\]
 Analyzing [\bin\]
 Analyzing [\src\]
Finished analysis of [C:\Users\Castor\Documents\workspace\FileIndexer\]
Output saved to [C:\Users\Castor\Documents\workspace\FileIndexer\_list.2016-07-03.csv]
Done.
```

Also within this repo is the sample CSV file that is a result of this analysis. 

## Credits/Sources
This program uses a modified and stripped-down version of [bookClasses](http://home.cc.gatech.edu/TeaParty/47), which is licensed under a Creative Commons Attribution 3.0 US License, to handle choosing a directory.

## Licensing
<a rel="license" href="http://creativecommons.org/licenses/by/3.0/us/"><img alt="Creative Commons License" style="border-width:0" src="https://i.creativecommons.org/l/by/3.0/us/88x31.png" /></a><br />The source code created by me (and not mentioned in the Credits/Sources section) is licensed under a <a rel="license" href="http://creativecommons.org/licenses/by/3.0/us/">Creative Commons Attribution 3.0 United States License</a>. By using this code, you agree to the terms stated in the Creative Commons Attribution 3.0 United States License.

## Other Notes
This program is currently set to output times by GMT -5. You can change this by changing the "-5" after the timeOffset variable in FileIndexer.

