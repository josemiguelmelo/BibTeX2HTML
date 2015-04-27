import java.util.*;

public class Semantic{

	public HashMap<String, HashMap<String, Boolean>> entriesSemantic;

	public Semantic(){
		entriesSemantic = new HashMap<String, HashMap<String, Boolean>> ();
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
		entriesSemantic.put("article", getArticleElement());
	}

	public boolean evalParams(ArrayList<String> paramsList, String className){

		HashMap<String, Boolean> possibleParams = this.entriesSemantic.get(className);

		Iterator it = possibleParams.entrySet().iterator();

		while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();

	        if(pair.getValue().equals(true)){
	        	if(!paramsList.contains(pair.getKey())){
	        		System.out.println("Required param not found ("+pair.getKey()+").");
	        		return false;
	        	}
	        }
	    }

	    return true;

	}
}