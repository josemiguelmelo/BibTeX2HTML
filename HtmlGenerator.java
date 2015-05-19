
import java.util.*;

import java.io.IOException;


public class HtmlGenerator{

	public static final String APA_STYLE = "apa";
	public static final String CHICAGO_STYLE = "chicago";

	private String bibliographyStyle;
	private String outputFile;

	private HashMap<String , HashMap<String, String>> representation;

	public HtmlGenerator(String outputFile, HashMap<String , HashMap<String, String>> representation, String bibliographyStyle) throws Exception{
		this.outputFile = outputFile;
		this.representation = representation;
		this.bibliographyStyle = bibliographyStyle;
	}

	public HtmlGenerator(String outputFile, HashMap<String , HashMap<String, String>> representation){
		this.outputFile = outputLocation(outputFile);
		this.representation = representation;
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


	private void generateHTML() throws Exception
	{
		java.io.PrintWriter htmlFile = new java.io.PrintWriter(outputFile, "UTF-8");

		String includes = "<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css'>"
    						+ "<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css'>"
    						+ "<script src='https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js'></script>"
    						+ "<script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js'></script>";


		String header = "<html><head><title> " + outputFile.split("\\.")[0] + " </title>" + includes + "</head>";

		String bodyInit = "<body>";

		String bodyEnd = "</body></html>";

		String cssFile = "<link rel='stylesheet' type='text/css' href='main.css'>";

		ArrayList<String> bodyContent = new ArrayList<String>();
		
		bodyContent.add(header);
		bodyContent.add(cssFile);
		bodyContent.add(bodyInit);


		Iterator it = representation.entrySet().iterator();

		while (it.hasNext()) {
	        Map.Entry classStructure = (Map.Entry)it.next();

	        String classContent = "<div class='class panel panel-default'><div class='panel-heading'><h2>" + ((String)classStructure.getKey()).split("-")[0] + " </h2></div>";
    		String idString = "<div class='panel-body'><label>ID = </label>"  + " "+ ((String)classStructure.getKey()).split("-")[1] +  "<br><br></div>";
    		

    			/** current block hashmap **/
    		HashMap<String , HashMap<String, String>> currentBlock = new  HashMap<String , HashMap<String, String>> ();
            currentBlock.put((String)classStructure.getKey() , (HashMap<String, String>)classStructure.getValue());
            // get html with current block representation
    		String repres = new APAGenerator(currentBlock).getHtml();

    		String representationString = "<div class='panel-body'><label>APA representation </label><br>" + cleanLatex(repres) + "<br><br></div>";

    		String tableString = "<div><table class='table'><thead class='table_header'><tr><td class='param'>Param</td><td class='value_table'>Value</td></tr></thead>";
    		String tableBodyInitString = "<tbody>";

    		
    		String tableBodyEndString = "</tbody></table></div></div>";

    		bodyContent.add(classContent);
    		bodyContent.add(idString);
    		bodyContent.add(representationString);
    		bodyContent.add(tableString);
    		bodyContent.add(tableBodyInitString);

			Iterator classIterator = ((HashMap<String, String>)classStructure.getValue()).entrySet().iterator();

			while (classIterator.hasNext()) {
	        	Map.Entry params = (Map.Entry) classIterator.next();

	        	String tableRow = "<tr><td>" + params.getKey() + "</td><td>"+ params.getValue() +"</td></tr>";


	        	bodyContent.add(tableRow);
    		
	    	}

	    	bodyContent.add(tableBodyEndString);

	    }

	    bodyContent.add(bodyEnd);

	    for(int i = 0; i < bodyContent.size(); i++){
			htmlFile.println(bodyContent.get(i));	
	    }

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
		current = current.replace("\\~{a}","&atilde");	
		current = current.replace("\\ʻ{a}","&agrave");
		current = current.replace("\\”{a}","&auml");
		current = current.replace("\\^{a}","&acirc");
		current = current.replace("\\ʼ{a}","&aacute");
		current = current.replace("\\.{a}",""); // add . on top of the char

		return current;
	}

	public String cleanLatexOthers(String dirtyString) {
		String current = dirtyString;
		current = current.replace("\\aa","&aring");
		current = current.replace("\\ae","&aelig");
		current = current.replace("\\0","&oslash");
		current = current.replace("\\c{c}","&ccedil");
		current = current.replace("\\ss","&szlig");

		System.out.println(current);

		return current;
	}
}











