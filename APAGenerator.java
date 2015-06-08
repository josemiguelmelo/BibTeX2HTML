import java.util.*;

import java.io.*;

public class APAGenerator{

	private HashMap<String , HashMap<String, String>> representation;

	private final String APA_TEMPLATE_FILE = "templates/APA/APA_template.html"; 

	public APAGenerator(HashMap<String , HashMap<String, String>> representation){
		this.representation = representation;
	}

	private String loadTemplate() throws Exception{

	  	BufferedReader bufferedReader = new BufferedReader(new FileReader(APA_TEMPLATE_FILE));
	 
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

	        Iterator classIterator = ((HashMap<String, String>)classStructure.getValue()).entrySet().iterator();

	        String line = "";

	        HashMap<String, String> struc = ((HashMap<String, String>)classStructure.getValue());

	        if(struc.get("author") != null){
	        	String[] authors = null;

	        	if(struc.get("author").toLowerCase().contains("and".toLowerCase())) {
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
	        		String[] correctRepresentation = authors[0].split(" ");

	        		if(correctRepresentation.length == 1) {
	        			line = line + correctRepresentation[0] +".";
	        		}
	        		else {
	        			line += correctRepresentation[correctRepresentation.length-1] + ", ";

	        			for(int i = 0; i < (correctRepresentation.length)-1; i++) {
        					line += correctRepresentation[i].toUpperCase().charAt(0) + ".";
        					if(i < correctRepresentation.length-2) {
        						line += " ";
        					}
	        				
	        			}
	        		}

	        		template = template.replace("{{ AUTHOR }}",line);
	        		line="";
	        	} else if(authors.length > 1 && authors.length < 8) {

	        		for(int i = 0; i < authors.length; i++) {

	        			authors[i] = authors[i].replaceAll("  ", " ");
	        			if(Character.toString(authors[i].charAt(0)).equals(" "))
	        				authors[i] = authors[i].substring(1);


	        			String[] correctRepresentation = authors[i].split(" ");

		        		if(correctRepresentation.length == 1) {
		        			line = line + correctRepresentation[0] + ".";
		        		}
		        		else {
		        			line += correctRepresentation[correctRepresentation.length-1] + ", ";

		        			for(int j = 0; j < (correctRepresentation.length)-1; j++) {
		    					line += correctRepresentation[j].toUpperCase().charAt(0) + ".";
		    					if(j < correctRepresentation.length-2) {
		    						line += " ";
		    					}
		        				
		        			}
		        		}

		        		if(i < authors.length-2) {
		        			line += ", ";
		        		}
		        		else if(i < authors.length-1) {
		        			line += ", & ";
		        		}
	        		}

	        		template = template.replace("{{ AUTHOR }}",line);
	        		line="";

	        	} else if(authors.length > 7) {
	        		for(int i = 0; i < authors.length; i++) {

	        			authors[i] = authors[i].replaceAll("  ", " ");
	        			if(Character.toString(authors[i].charAt(0)).equals(" "))
	        				authors[i] = authors[i].substring(1);


	        			String[] correctRepresentation = authors[i].split(" ");

	        			if(i >= 6) {
	        				line += "...";
	        				break;
	        			}
	        			else {
	        				if(correctRepresentation.length == 1) {
		        				line = line + correctRepresentation[0] + ".";
			        		}
			        		else {
			        			line += correctRepresentation[correctRepresentation.length-1] + ", ";

			        			for(int j = 0; j < (correctRepresentation.length)-1; j++) {
			    					line += correctRepresentation[j].toUpperCase().charAt(0) + ".";
			    					if(j < correctRepresentation.length-2) {
			    						line += " ";
			    					}
			        				
			        			}
			        		}
	        			}

		        		if(i < authors.length-2) {
		        			line += ", ";
		        		}
		        		else if(i < authors.length-1) {
		        			line += ", & ";
		        		}
	        		}

	        		template = template.replace("{{ AUTHOR }}",line);
	        		line="";
	        	}
	        }

	        if(struc.get("editor") != null){
	        	String[] authors = struc.get("editor").split(" and ");

		        int i;
		        for(i = 0; i < authors.length - 1; i++){
		        	String[] authorString = authors[i].split(",");
		        	if(authorString.length == 1)
		        		line = line + authorString[0] + ", ";
		        	else
		        		line = line + authorString[1].charAt(0) + ", " + authorString[0] + ", ";
		        }

		        if(authors[i].equals("others")){
		        	line = line + "...";
		        }else{
		        	String[] authorString = authors[i].split(", ");
		        	if(authorString.length == 1)
						line = line + authorString[0] + ", ";
		        	else
		        		line = line + authorString[1].charAt(0) + ", " + authorString[0] + ", ";
		        }
	        }

	        template = template.replace("{{ AUTHOR }}", line);
	        line = "";
	        


			if(struc.get("year") != null)
	        	line = line + struc.get("year");
	        template = template.replace("{{ YEAR }}", line);
	        line="";

	        if(struc.get("month") != null && struc.get("year") != null)
	        	line = line + " - " + struc.get("month");
	        template = template.replace("{{ MONTH }}", line);
	        line="";


			if(struc.get("title") != null)
	        	line = line + struc.get("title") + ". ";


	        template = template.replace("{{ TITLE }}", line);
	        line="";

			if(struc.get("publisher") != null)
	        	line = line + struc.get("publisher")+ ".";

	        template = template.replace("{{ PUBLISHER }}", line);
	        line="";


	        if(struc.get("chapter") != null)
	        	line = line + struc.get("chapter") + ". In";
	        template = template.replace("{{ CHAPTER }}",line);
	        line="";

	        if(struc.get("editor") != null) 
	        	line = line + "(" + struc.get("editor") + ", Eds.).";
	        template = template.replace("{{ EDITOR }}",line);
	        line="";

	        if(struc.get("edition") != null)
	        	line = line + struc.get("edition") + " ed. . ";
	        template = template.replace("{{ EDITION }}",line);
	        line="";

	        if(struc.get("volume") != null)
	        	line = line + struc.get("volume") + " ";
	        template = template.replace("{{ VOLUME }}",line);
	        line="";

	        if(struc.get("pages") != null)
	        	line = line + "pp. " + struc.get("pages") + ". ";
	        template = template.replace("{{ PAGES }}",line);
	        line="";

	        if(struc.get("number") != null)
	        	line = line + "(" + struc.get("number") + ") ";
	        template = template.replace("{{ NUMBER }}",line);
	        line="";

	        if(struc.get("howpublished") != null)
	        	line = line + "(" + struc.get("howpublished") + "). ";
	        template = template.replace("{{ HOWPUBLISHED }}",line);
	        line="";

	        if(struc.get("school") != null)
	        	line = line + struc.get("school") + ". ";
	        template = template.replace("{{ SCHOOL }}",line);
	        line="";

	        if(struc.get("organization") != null)
	        	line = line + struc.get("organization") + ". ";
	        template = template.replace("{{ ORGANIZATION }}",line);
	        line="";

	        if(struc.get("institution") != null)
	        	line = line + struc.get("institution") + ". ";
	        template = template.replace("{{ INSTITUTION }}",line);
	        line="";

	        if(struc.get("journal") != null)
	        	line = line + struc.get("journal") + ". ";
	        template = template.replace("{{ JOURNAL }}",line);
	        line="";

	        if(struc.get("booktitle") != null)
	        	line = line + struc.get("booktitle") + ". ";
	        template = template.replace("{{ BOOKTITLE }}",line);
	        line="";

	        if(struc.get("series") != null)
	        	line = line + "series:" + struc.get("series") + ". ";
	        template = template.replace("{{ SERIES }}",line);
	        line="";

			if(struc.get("address") != null)
	        	line = line + struc.get("address") + ". ";
	        template = template.replace("{{ ADDRESS }}",line);
	        line="";

	        if(struc.get("note") != null)
	        	line = line + struc.get("note");
	        template = template.replace("{{ NOTE }}",line);
	        line="";

	        if(struc.get("annote") != null)
	        	line = line + "<br>" + struc.get("annote");
	        template = template.replace("{{ ANNOTE }}",line);
	        line="";

	        if(struc.get("type") != null)
	        	line = line + struc.get("type") + ".";
	        template = template.replace("{{ TYPE }}",line);
	        line="";

	        if(struc.get("crossref") != null)
	        	line = line + "ref. to <a href=#" + struc.get("crossref")+">"+ struc.get("crossref")+"</a>" + ". ";
	        template = template.replace("{{ CROSSREF }}",line);
	        line="";

	         if(struc.get("key") != null)
	        	line = line + "inkey=" + struc.get("key") + ".";
	        template = template.replace("{{ KEY }}",line);
	        line="";

	    }

	    return template;
	}
}