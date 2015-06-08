import java.util.*;

import java.io.*;



public class ChicagoGenerator{

	private HashMap<String , HashMap<String, String>> representation;

	private final String CHICAGO_TEMPLATE_FILE = "templates/Chicago/Chicago_template.html"; 

	public ChicagoGenerator(HashMap<String , HashMap<String, String>> representation){
		this.representation = representation;
	}

	private String loadTemplate() throws Exception{

	  	BufferedReader bufferedReader = new BufferedReader(new FileReader(CHICAGO_TEMPLATE_FILE));
	 
	  	StringBuffer stringBuffer = new StringBuffer();
	  	String line = null;
	 
	  	while((line =bufferedReader.readLine())!=null){
	 
	   		stringBuffer.append(line).append("\n");
  		}
   
  		return stringBuffer.toString();
	}


	String getHtml(){
		String template = "";
		try{
			template = loadTemplate();
		}catch(Exception e){
			System.out.println("Error: Could not load APA template.");
			return "";
		}

		Iterator it = representation.entrySet().iterator();

		while (it.hasNext()) {
	        Map.Entry classStructure = (Map.Entry)it.next();

	        String className = new String((String)classStructure.getKey());
	        className = className.split("-")[0];

	        Iterator classIterator = ((HashMap<String, String>)classStructure.getValue()).entrySet().iterator();

	        String line = "";

	        HashMap<String, String> struc = ((HashMap<String, String>)classStructure.getValue());

	    
	        if(struc.get("author")!= null) {
	        	String[] authors = null;

	        	if(struc.get("author").toLowerCase().contains("and")) {
					authors = struc.get("author").split("and");
					for(int i=0;i<authors.length;i++) {
						String[] currentAuthor = authors[i].split(",");
						String correctAuthor = currentAuthor[1] + currentAuthor[0];
						authors[i] = correctAuthor;
					}
	        	} else {
	        		authors = struc.get("author").split(",");

	        	}

	        	if(authors.length == 1) {
	        		String[] correctRepresentation = authors[0].split(" ",2);

	        		if(correctRepresentation.length==2) {
	        			line = line + correctRepresentation[1] + ", " + correctRepresentation[0] + ".";
	        		} else {
	        			line = line + correctRepresentation[0] +".";
	        		}
	        		template = template.replace("{{ AUTHOR }}",line);
	        		line="";
	        	} else if(authors.length > 1 && authors.length < 4) {
	        		String[] correctRepresentation = authors[0].split(" ",2);
	        		if(correctRepresentation.length==2) {
	        			line = line + correctRepresentation[1] + "," + correctRepresentation[0];
	        		} else {
	        			line = line + correctRepresentation[0];
	        		}
	        	 
	        		if(authors.length==2) {
	        			line = line + " and " + authors[1] + ".";
	        		}else if(authors.length==3){
	        			line = line +" and " + authors[1] + " and " + authors[2] + ".";
	        		}
	        		template = template.replace("{{ AUTHOR }}",line);
	        		line="";
	        	} else if(authors.length >= 4) {
	        		String[] correctRepresentation = authors[0].split(" ",2);
	        		if(correctRepresentation.length==2) {
	        			line = line + correctRepresentation[1] + ", " + correctRepresentation[0] + " et al.";
	        		}
	        		else {
	        			line = line + correctRepresentation[0]+" et al.";
	        		}

	        		template = template.replace("{{ AUTHOR }}",line);
	        		line="";
	        	}
	        }

	        if(struc.get("chapter") != null)
	        	line = line + "\"" +struc.get("chapter") + "\", in";
	        template = template.replace("{{ CHAPTER }}", line);
	        line="";

	        if(className.equals("phdthesis")) {
	        	if(struc.get("title") != null)
	        		line = line + struc.get("title") + " PhD., ";
	        	template = template.replace("{{ TITLE }}", line);
	        	line="";

	        } else if(className.equals("masterthesis")){
	        	if(struc.get("title") != null)
	        		line = line + struc.get("title") + " MA thesis, ";
	        	template = template.replace("{{ TITLE }}", line);
	        	line="";

	        } else if(className.equals("book")) {
	        	if(struc.get("title") != null)
	        		line = line + "<span style='italic'>" + struc.get("title") + "</span>, ";
	        	template = template.replace("{{ TITLE }}", line);
	        	line="";

	        } else {
	        	if(struc.get("title") != null)
	        		line = line + "\"" + struc.get("title") + "\""+ ", ";
	        	template = template.replace("{{ TITLE }}", line);
	        	line="";
	        }

	        

	        if(struc.get("editor") != null)
	        	line = line + "edited by "+ struc.get("editor") + ". ";
	        template = template.replace("{{ EDITOR }}", line);
	        line="";

	        if(struc.get("journal") != null)
	        	line = line + struc.get("journal") + ". ";
	        template = template.replace("{{ JOURNAL }}", line);
	        line="";

	        if(struc.get("number") != null)
	        	line = line + "no." + struc.get("number") + ". ";
	        template = template.replace("{{ NUMBER }}", line);
	        line="";

	        if(struc.get("volume") != null)
	        	line = line + struc.get("volume") + ". ";
	        template = template.replace("{{ VOLUME }}", line);
	        line="";

	        if(struc.get("address") != null)
	        	line = line + struc.get("address") + ": ";
	        template = template.replace("{{ ADDRESS }}", line);
	        line="";

	        if(struc.get("publisher") != null)
	        	line = line + struc.get("publisher") + ", ";
	        template = template.replace("{{ PUBLISHER }}", line);
	        line="";

	        if(struc.get("year") != null)
	        	line = line  +struc.get("year") + ". ";
	        template = template.replace("{{ YEAR }}", line);
	        line="";

	        if(struc.get("month") != null)
	        	line = line + struc.get("month") + ", ";
	        template = template.replace("{{ MONTH }}", line);
	        line="";

	        if(struc.get("edition") != null)
	        	line = line + struc.get("edition") + "ed., ";
	        template = template.replace("{{ EDITION }}", line);
	        line="";

	        if(struc.get("pages") != null)
	        	line = line + struc.get("pages") + ". ";
	        template = template.replace("{{ PAGES }}", line);
	        line="";

	        if(struc.get("notes") != null)
	        	line = line + struc.get("notes") + ". ";
	        template = template.replace("{{ NOTES }}", line);
	        line="";

	        if(struc.get("annote") != null)
	        	line = line + "<br>" + struc.get("annote") + ". ";
	        template = template.replace("{{ ANNOTE }}", line);
	        line="";

	        if(struc.get("note") != null)
	        	line = line + "<br>" + struc.get("note");
	        template = template.replace("{{ NOTE }}",line);
	        line="";

	        if(struc.get("crossref") != null)
	        	line = line + "referes <a href=#" + struc.get("crossref")+">"+ struc.get("crossref")+"</a>" + ". ";
	        template = template.replace("{{ CROSSREF }}", line);
	        line="";

	        if(struc.get("institution") != null)
	        	line = line + struc.get("institution") + ". ";
	        template = template.replace("{{ INSTITUTION }}", line);
	        line="";

 










	    }

	    return template;
	}
}