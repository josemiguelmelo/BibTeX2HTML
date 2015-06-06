#Authors

- José Miguel Melo, ei12050
- Ricardo Loureiro, ei12034
- Tiago Ferreira, ei12051

#Overview

A tool to parse bibtex files (http://www.bibtex.org/) and output the bibliographical entries in HTML. 

# Compilation

This project needs JavaCC to be compiled. After installing JavaCC, use the following commands within the source directory:

```
jjtree bibtext2html.jjt

javacc bibtext2html.jj

javac *.java
```


#Running

You should run the BibTex class. To do so, run the following command:

```
java BibTex <file_to_parse> <output_location>

```


#Example


As example, there are 2 example files inside files/ directory (correctExample.bib and errorExample.bib).

To test the parser using these files, run the compilation commands and then the following commands:

#### File with no errors or warnings:
```
java BibTex files/correctExample.bib files/output.html

```
Expected result from command above:

```
HTML file generated successfully. Location: files/output.html
```

After running the file without errors, an HTML file is generated with all information.


#### File with errors and warnings:
```
java BibTex files/errorExample.bib files/output

```


#Grammar
```
S ---> Class "{" Id Body "}"
Class ---> "@article" | "@book" | ...
Id ---> STRING
Body ---> { "," Params? }
Params ---> "author" "=" STRING | "title" "=" STRING | ....
```

#Lexical and Sintatic Analysis

It is implemented in *bibtext2html.jjt*.

To analyze the lexical and sintatic part, the code goes through the tree and checks if values inserted are valid, according to the grammar created and to the tokens added. The analysis is started by the method Start().

If any error is found, an exception is thrown.

#Semantic Analysis

It is implemented in *Semantic.java*.
To analyze the semantic part, the code goes through the language tree and checks, using a Semantic object created, all parameters inserted:

- if all required parameters exist - prints an error if missing some parameter
- if all optional parameters are valid - warning if parameters is not valid for the class being checked
- if all parameters values are valid - prints warnings or error, if found any
- if crossref are valid - prints error if crossref id not found or duplicated
	
	
#Intermediate Representation

To represent the compiled file, it was used an *HashMap\<String, HashMap\<String,String\>\>*, where the String contains the class name and the id separated by '-' and the HashMap<String, String> has all the parameters inserted.

**Example:**

Class in bibtex file:
```
@article{example1,
author={Example Author},
title ={Example Title},
journal={Example Journal},
volume=136,
number=1
}
```

Intermediate Representation:
```
"article-example1"
	"author","Example Author"
	"title","Example Title"
	"journal","Example Journal"
	"volume","136"
	"number","1"
```


#HTML Generation

// TODO : complete

Referir bem os templates!!!

#Tests

// TODO : complete

#Architecture

To compile the bibtex file the following steps are done:

- open the file
- validate the grammar (sintatic and lexical analysis)
- validate the semantic (explained in Semantic Analysis section)
	- go through the language tree and evaluate if all parameters are valid (1)
	- go through the language tree and evaluate if all crossref are valid
- if no errors found, the HTML is generated using the HtmlGenerator object (explained in HTML Generation section) (2)
	

(1) - To know all the classes Required and Optional parameters, it was added a json file (located at config/ folder) with all the information needed. When the Semantic object is created, this file is parsed using a third-party library - http://www.json.org/java/index.html

(2) - To generate the HTML it is used some templates (located at templates/ folder), which are parsed and the template's parameters replaced by the values compiled from the bibtex file.


#Positive points

The implemented tool has several positive points:

- Converts a file using bibtex notation to HTML, which is cleaner
- Allows to convert a file from bibtex notation to APA and Chicago styles
- Allows to check if the file bibtex has any error
- Makes use of JSON to make it easier to change the required and optional parameters of each class, which makes the compiler more flexible
- Makes use of HTML templates to make it easier to change the output layout and style
- Semantic, sintatic and lexical analysis are well separated, which makes easier to scale the compiler and implement new features and validations


#Negative points
- If an unknown class is found in bibtex file, an exception is thrown and the analysis stops.
- // TODO: complete

### Future Improvements
- Show all error and always complete the analysis, to any error found
- Add more output styles (currently only Chicago and APA implemented)
- Integrate with Amazon and other services to get images and information about all objects/classes inserted in the bibtex file









