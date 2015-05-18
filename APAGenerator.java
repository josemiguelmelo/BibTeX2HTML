import java.util.*;

import java.io.IOException;


public class APAGenerator{

	private HashMap<String , HashMap<String, String>> representation;

	public APAGenerator(HashMap<String , HashMap<String, String>> representation){
		this.representation = representation;
	}

	String getHtml(){

		ArrayList<String> bodyContent = new ArrayList<String>();
		

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
	        

	        if(struc.get("year") != null)
	        	line = line + "(" + struc.get("year") + "). ";

			if(struc.get("title") != null)
	        	line = line + struc.get("title") + ". ";

			if(struc.get("publisher") != null)
	        	line = line + struc.get("publisher")+ ".";


	    	bodyContent.add(line + "<br>");

	    }


	    String htmlFile = "";
	    for(int i = 0; i < bodyContent.size(); i++){
			htmlFile += bodyContent.get(i);	
	    }


	    return htmlFile;
	}
}