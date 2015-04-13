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
java BibTex <file_to_parse>
```
