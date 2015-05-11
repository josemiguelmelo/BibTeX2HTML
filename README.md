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

#Grammar
```
S ---> Class "{" Id Body "}"
Class ---> "@article" | "@book" | ...
Id ---> STRING
Body ---> { "," Params? }
Params ---> "author" "=" STRING | "title" "=" STRING | ....
```