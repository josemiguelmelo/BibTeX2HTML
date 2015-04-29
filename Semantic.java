import java.util.*;

import JsonParser.JsonSemanticParser;

import java.io.*;

public class Semantic{

	public HashMap<String, HashMap<String, Boolean>> entriesSemantic;

	public JsonSemanticParser semanticParser;



	public Semantic(){
		entriesSemantic = new HashMap<String, HashMap<String, Boolean>> ();

		this.semanticParser = new JsonSemanticParser("files/config.json");

		init();

	}

	public void init(){

		try {
            this.semanticParser.parse();

            this.entriesSemantic = this.semanticParser.getConfigInformation();

        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	public boolean evalParams(HashMap<String, String> paramsList, String className){

		HashMap<String, Boolean> possibleParams = this.entriesSemantic.get(className);

		Iterator it = possibleParams.entrySet().iterator();

		while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();

	        if(pair.getValue().equals(true)){

	        	String[] paramNames = ((String)pair.getKey()).split("/");

	        	if(paramNames.length == 2){
	        		if(!paramsList.containsKey(paramNames[0]) && !paramsList.containsKey(paramNames[1])){
		        		System.out.println("Required param not found ("+pair.getKey()+").");
		        		return false;
		        	}
	        	}else{
		        	if(!paramsList.containsKey(pair.getKey())){
		        		System.out.println("Required param not found ("+pair.getKey()+").");
		        		return false;
		        	}
	        	}
	        }

	        if(paramsList.containsKey(pair.getKey()) && pair.getKey().equals("pages")){
	        	String pagesString = paramsList.get("pages");
	        	pagesString = pagesString.replace("\"", "");

			    String[] pages = pagesString.split("-");
			   
			    if(pages.length==2){
			        int firstPage = Integer.parseInt(pages[0]);
			        int lastPage = Integer.parseInt(pages[1]);
			        if(lastPage < firstPage){
			        	System.out.println("Error: Last page must be higher than the starting page.");
			        	return false;
			        }else if(lastPage == firstPage){
			        	System.out.println("Warning: Last page is equal to starting page.");
			        }

			    }
	        }
	        
	    }

	    return true;

	}
}