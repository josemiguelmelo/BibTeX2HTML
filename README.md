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

- File with no errors or warnings:
```
java BibText files/correctExample.bib files/output.html

```

- File with errors and warnings:
```
java BibText files/errorExample.bib files/output

```


After running the file without errors, an HTML file is generated with all information.


#Grammar
```
S ---> Class "{" Id Body "}"
Class ---> "@article" | "@book" | ...
Id ---> STRING
Body ---> { "," Params? }
Params ---> "author" "=" STRING | "title" "=" STRING | ....
```