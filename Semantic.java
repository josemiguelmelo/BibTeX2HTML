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

	private int frequencyList(ArrayList<String> list, String element){
		int counter = 0;
		for(String el : list){
			if(el.equals(element)){
				counter++;
			}
        }	
        return counter;
	}

	public boolean validateCrossref(HashMap<String, String> paramsList, ArrayList<String> nodeIds, SimpleNode parentNode){

		if(paramsList.containsKey("crossref")){
			// get pages input
	        String crossRefId = paramsList.get("crossref");
	        if(frequencyList(nodeIds, crossRefId) == 1){
	        	for(String id : nodeIds){
					if(id.equals(crossRefId)){
						return true;
					}
	        	}
	        	return false;
	        }else if(frequencyList(nodeIds, crossRefId) == 0){
	        	System.out.println("Error: crossref reference not found at line " + parentNode.lineNumber);
	        	return false;
	        }
	       	System.out.println("Error: multiple crossref reference at line " + parentNode.lineNumber);
	       	return false;
		}
		return true;
	}

	private boolean pagesInputValid(String pagesInput, SimpleNode parentNode){
		pagesInput = pagesInput.replace("\"", "");

	    String[] pages = pagesInput.split("-");
	   
	    if(pages.length==2){
	        int firstPage = Integer.parseInt(pages[0]);
	        int lastPage = Integer.parseInt(pages[1]);
	        if(lastPage < firstPage){
	        	System.out.println("Error: Last page must be higher than the starting page. Block line: " + parentNode.lineNumber);
	        	return false;
	        }else if(lastPage == firstPage){
	        	System.out.println("Warning: Last page is equal to starting page. Block line: " + parentNode.lineNumber);
	        }
	    }
	    return true;
	}

	private boolean requiredParamExists(String requiredParam, HashMap<String, String> paramsList, SimpleNode parentNode){
		String[] paramNames = requiredParam.split("/");

	    if(paramNames.length == 2){
	        if(!paramsList.containsKey(paramNames[0]) && !paramsList.containsKey(paramNames[1])){
		       	System.out.println("Error: Required param not found ("+requiredParam+") at line " + parentNode.lineNumber + ".");
		        return false;
		    }
	    }else{
		   	if(!paramsList.containsKey(requiredParam)){
		        System.out.println("Error: Required param not found ("+requiredParam+") at line " + parentNode.lineNumber + ".");
		        return false;
		    }
	    }
	    return true;
	}


	public boolean evalParams(HashMap<String, String> paramsList, String className, SimpleNode parentNode){

		HashMap<String, Boolean> possibleParams = this.entriesSemantic.get(className);

		Iterator it = possibleParams.entrySet().iterator();

		while (it.hasNext()) {
	        Map.Entry param = (Map.Entry)it.next();

	        // if param is required
	        if(param.getValue().equals(true)){
	        	// verify if required param was inserted in bibtex file
	        	if(!requiredParamExists((String)param.getKey(), paramsList, parentNode)){
	        		return false;
	        	}
	        }

	        // if pages exists in params inserted by user and it is a possible param then check if pages input is correct
	        if(paramsList.containsKey(param.getKey()) && param.getKey().equals("pages")){
	        	// get pages input
	        	String pagesString = paramsList.get("pages");

	        	if(!pagesInputValid(pagesString, parentNode)){
	        		return false;
	        	}
	        }



	        
	    }

	    return true;

	}
}