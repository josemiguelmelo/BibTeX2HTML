import java.util.*;

import JsonParser.JsonSemanticParser;

import java.io.*;

public class Semantic{

	public HashMap<String, HashMap<String, Boolean>> entriesSemantic;

	public JsonSemanticParser semanticParser;



	public Semantic(){
		entriesSemantic = new HashMap<String, HashMap<String, Boolean>> ();

		this.semanticParser = new JsonSemanticParser("config/config.json");

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

	public String cleanTokens(String tokenValue) {
		if(tokenValue.length()!=0) {
			if(tokenValue.charAt(0) == '{' ||
				tokenValue.charAt(0) == '\"' ) {

				tokenValue = tokenValue.substring(1,tokenValue.length()-1);
			}
		}
		return tokenValue;
	}

	private boolean pagesInputValid(String pagesInput){
		pagesInput = pagesInput.replace("\"", "");

	    String[] pages = pagesInput.split("-");
	   
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
	    return true;
	}

	private boolean requiredParamExists(String requiredParam, HashMap<String, String> paramsList){
		String[] paramNames = requiredParam.split("/");

	    if(paramNames.length == 2){
	        if(!paramsList.containsKey(paramNames[0]) && !paramsList.containsKey(paramNames[1])){
		       	System.out.println("Error: Required param not found ("+requiredParam+").");
		        return false;
		    }
	    }else{
		   	if(!paramsList.containsKey(requiredParam)){
		        System.out.println("Error: Required param not found ("+requiredParam+").");
		        return false;
		    }
	    }
	    return true;
	}


	public boolean evalParams(HashMap<String, String> paramsList, String className){

		HashMap<String, Boolean> possibleParams = this.entriesSemantic.get(className);

		Iterator it = possibleParams.entrySet().iterator();

		while (it.hasNext()) {
	        Map.Entry param = (Map.Entry)it.next();

	        // if param is required
	        if(param.getValue().equals(true)){
	        	// verify if required param was inserted in bibtex file
	        	if(!requiredParamExists((String)param.getKey(), paramsList)){
	        		return false;
	        	}
	        }

	        // if pages exists in params inserted by user and it is a possible param then check if pages input is correct
	        if(paramsList.containsKey(param.getKey()) && param.getKey().equals("pages")){
	        	// get pages input
	        	String pagesString = paramsList.get("pages");

	        	if(!pagesInputValid(pagesString)){
	        		return false;
	        	}
	        }



	        
	    }

	    return true;

	}
}