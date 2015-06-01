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
	        	String[] authors = struc.get("author").split(" and ");

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
	        	line = line + "(" + struc.get("year") + "). ";


	        template = template.replace("{{ YEAR }}", line);
	        line="";


			if(struc.get("title") != null)
	        	line = line + struc.get("title") + ". ";


	        template = template.replace("{{ TITLE }}", line);
	        line="";

			if(struc.get("publisher") != null)
	        	line = line + struc.get("publisher")+ ".";

	        template = template.replace("{{ PUBLISHER }}", line);
	        line="";
	    }

	    return template;
	}
}