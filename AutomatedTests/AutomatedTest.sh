#!/bin/bash

echo ""
echo "----------------------------------------"
echo "-------- JavaCC Parser Compile  --------"
echo "----------------------------------------"
echo ""

jjtree bibtext2html.jjt

javacc bibtext2html.jj

javac *.java

echo ""
echo ""
echo "----------------------------------------"
echo "----- Compiling correctExample.bib -----"
echo "----------------------------------------"
echo ""

java BibTex files/correctExample.bib files/output.html

echo ""
echo "----------------------------------------"
echo "------ Compiling errorExample.bib ------"
echo "----------------------------------------"
echo ""

java BibTex files/errorExample.bib files/output


echo ""
echo "----------------------------------------"
echo "----------- Compiling bug.bib ----------"
echo "----------------------------------------"
echo ""


java BibTex files/bug.bib files/output



echo ""
echo "----------------------------------------"
echo "-------- Compiling notFound.bib --------"
echo "----------------------------------------"
echo ""


java BibTex files/notFound.bib files/output

