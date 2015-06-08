
import java.util.*;

import java.io.*;

import java.util.regex.Pattern;
import java.util.regex.Matcher;



public class HtmlGenerator{

	public static final String APA_STYLE = "apa";
	public static final String CHICAGO_STYLE = "chicago";

	private String bibliographyStyle;
	private String outputFile;
	private String templateFileName;

	private HashMap<String , HashMap<String, String>> representation;

	public HtmlGenerator(String outputFile, String templateFileName, HashMap<String , HashMap<String, String>> representation, String bibliographyStyle) throws Exception{
		this.outputFile = outputFile;
		this.representation = representation;
		this.bibliographyStyle = bibliographyStyle;
		this.templateFileName = templateFileName;

	}

	public HtmlGenerator(String outputFile, String templateFileName, HashMap<String , HashMap<String, String>> representation){
		this.outputFile = outputLocation(outputFile);
		this.representation = representation;
		this.templateFileName = templateFileName;
	}

	private static String outputLocation(String outputLocation){
		if(outputLocation.contains(".html"))
			return outputLocation;
		else
			return outputLocation + ".html";
	}

	public void generate() throws Exception{
		generateHTML();
	}

	private String loadTemplate() throws Exception{
		
	  	BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(templateFileName)));
	 
	  	StringBuffer stringBuffer = new StringBuffer();
	  	String line = null;
	 
	  	while((line =bufferedReader.readLine())!=null){
	 
	   		stringBuffer.append(line).append("\n");
  		}
   
  		return stringBuffer.toString();
	}


	private void generateHTML() throws Exception
	{
		java.io.PrintWriter htmlFile = new java.io.PrintWriter(outputFile, "UTF-8");

		String template = loadTemplate();

		String bodyContent ="";

		Iterator it = representation.entrySet().iterator();

		while (it.hasNext()) {
	        Map.Entry classStructure = (Map.Entry)it.next();

	        bodyContent += "<div id=" + ((String)classStructure.getKey()).split("-")[1] + " class='class panel panel-default'><div class='panel-heading'><h2>" + ((String)classStructure.getKey()).split("-")[0] + " </h2></div>";
    		bodyContent += "<div class='panel-body'><label>ID = </label>"  + " "+ ((String)classStructure.getKey()).split("-")[1] +  "</div>";

    			/** current block hashmap **/
    		HashMap<String , HashMap<String, String>> currentBlock = new  HashMap<String , HashMap<String, String>> ();
            currentBlock.put((String)classStructure.getKey() , (HashMap<String, String>)classStructure.getValue());
            // get html with current block representation
    		String repres = new APAGenerator(currentBlock).getHtml();
    		String chicagoRep = new ChicagoGenerator(currentBlock).getHtml();
			
			bodyContent += "<div class='panel-body'>" + cleanLatex(repres) + "</div>";
			bodyContent += "<div class='panel-body'>" + cleanLatex(chicagoRep) + "</div>";


    		bodyContent += "<div><table class='table'><thead class='table_header'><tr><td class='param'>Param</td><td class='value_table'>Value</td></tr></thead>";
    		bodyContent += "<tbody>";

    		
    		String tableBodyEndString = "</tbody></table></div></div>";


			Iterator classIterator = ((HashMap<String, String>)classStructure.getValue()).entrySet().iterator();

			while (classIterator.hasNext()) {
	        	Map.Entry params = (Map.Entry) classIterator.next();

	        	bodyContent += "<tr><td>" + params.getKey() + "</td><td>"+ params.getValue() +"</td></tr>";
	    	}

	    	bodyContent += tableBodyEndString;

	    }

    	String finalOutput = "";
    	finalOutput = template.replace("{{ BODY }}", bodyContent);

		htmlFile.println(finalOutput);	

		htmlFile.close();
	}

	public String cleanLatex(String dirtyString) {
		String finalString="";

		//add flexibility to clean functions, currently only working for "a"
		String accented = cleanLatexAccent(dirtyString);
		//add more clean specials chars from bibtex here
		String special = cleanLatexOthers(accented);

		finalString = special;
		return finalString;
	}

	public String cleanLatexAccent(String dirtyString) {
		String current = dirtyString;
		Pattern MY_PATTERN = Pattern.compile("\\{[a-z]\\}");
		Matcher m = MY_PATTERN.matcher(current);
		while (m.find()) {
			String s = m.group(0);
			current = current.replace("\\~{"+s.charAt(1)+"}","&"+s.charAt(1)+"tilde");	
			current = current.replace("\\ʻ{"+s.charAt(1)+"}","&"+s.charAt(1)+"grave");
			current = current.replace("\\”{"+s.charAt(1)+"}","&"+s.charAt(1)+"uml");
			current = current.replace("\\^{"+s.charAt(1)+"}","&"+s.charAt(1)+"circ");
			current = current.replace("\\ʼ{"+s.charAt(1)+"}","&"+s.charAt(1)+"acute");
			current = current.replace("\\.{a}","");
			current = current.replace("\\c{"+s.charAt(1)+"}","&"+s.charAt(1)+"cedil");
		}		
		return current;
	}

	public String cleanLatexOthers(String dirtyString) {
		String current = dirtyString;
		current = current.replace("\\aa","&aring");
		current = current.replace("\\ae","&aelig");
		current = current.replace("\\0","&oslash");
		current = current.replace("\\ss","&szlig");


		return current;
	}
}











