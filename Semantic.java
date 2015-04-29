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

	private HashMap<String, Boolean> getArticleElement(){
		HashMap<String, Boolean> articleElement = new HashMap<String, Boolean>();

		articleElement.put("author", true);
		articleElement.put("title", true);
		articleElement.put("journal", true);
		articleElement.put("year", true);
		articleElement.put("volume", true);
		articleElement.put("number", false);
		articleElement.put("pages", false);
		articleElement.put("month", false);
		articleElement.put("note", false);
		articleElement.put("key", false);

		return articleElement;
	}

	public void init(){

		try {
            this.semanticParser.parse();

            this.entriesSemantic = this.semanticParser.getConfigInformation();

        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	public boolean evalParams(ArrayList<String> paramsList, String className){

		HashMap<String, Boolean> possibleParams = this.entriesSemantic.get(className);

		Iterator it = possibleParams.entrySet().iterator();

		while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();

	        if(pair.getValue().equals(true)){

	        	String[] paramNames = ((String)pair.getKey()).split("/");

	        	if(paramNames.length == 2){
	        		if(!paramsList.contains(paramNames[0]) && !paramsList.contains(paramNames[1])){
		        		System.out.println("Required param not found ("+pair.getKey()+").");
		        		return false;
		        	}
	        	}else{
		        	if(!paramsList.contains(pair.getKey())){
		        		System.out.println("Required param not found ("+pair.getKey()+").");
		        		return false;
		        	}
	        	}
	        }
	    }

	    return true;

	}
}