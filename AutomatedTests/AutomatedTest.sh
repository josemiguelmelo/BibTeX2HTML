#!/bin/bash

echo ""
echo "----------------------------------------"
echo "-------- JavaCC Parser Compile  --------"
echo "----------------------------------------"
echo ""

jjtree bibtext2html.jjt

javacc bibtext2html.jj

javac -encoding ISO-8859-1 *.java

echo ""
echo ""
echo "----------------------------------------"
echo "--- Compiling NO Error&Warning File ----"
echo "----------------------------------------"
echo ""

java BibTex files/correctExample.bib files/correctExample.html

echo ""
echo "------------------------------------------"
echo "------ Compiling ONLY Warning file ------"
echo "------------------------------------------"
echo ""

java BibTex files/warningExample.bib files/warningExample.html


echo ""
echo "------------------------------------------"
echo "------ Compiling Error&Warning file ------"
echo "------------------------------------------"
echo ""

java BibTex files/errorWarningExample.bib files/errorExample.html


echo ""
echo "-----------------------------------------"
echo "----------- Compiling Bug file ----------"
echo "-----------------------------------------"
echo ""


java BibTex files/bugExample.bib files/bugExample.html



echo ""
echo "-----------------------------------------"
echo "------- Compiling Not Found file --------"
echo "-----------------------------------------"
echo ""


java BibTex files/notFound.bib files/notFound.html


echo ""
echo ""