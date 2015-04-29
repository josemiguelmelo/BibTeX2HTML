package JsonParser;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonSemanticParser {

    private HashMap<String,HashMap<String,Boolean> > configInformation;
    private String filename;

    public JsonSemanticParser(String filename){
        this.filename = filename;
        this.configInformation = new HashMap<String, HashMap<String, Boolean>>();
    }

    public void parse() throws IOException {
        String configInformation = readFile(this.filename, StandardCharsets.UTF_8);

        JSONObject jsonObject = new JSONObject(configInformation);

        Iterator<String> it = jsonObject.keys();

        int counter = 0;

        while(it.hasNext()){
            counter++;
            String key = it.next();

            JSONObject obj = jsonObject.getJSONObject(key);

            JSONArray optional = obj.getJSONArray("optional");
            JSONArray required = obj.getJSONArray("required");

            HashMap<String, Boolean> params = new HashMap<String, Boolean>();
            // optional params
            for(int i = 0; i < optional.length(); i++){
                String param = optional.getString(i);

                params.put(param, false);
            }
            // required params
            for(int i = 0; i < required.length(); i++){
                String param = required.getString(i);

                params.put(param, true);
            }

            this.configInformation.put(key, params);
        }
    }

    private String readFile(String path,Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded,encoding);
    }


    public HashMap<String, HashMap<String, Boolean>> getConfigInformation() {
        return configInformation;
    }

    public void setConfigInformation(HashMap<String, HashMap<String, Boolean>> configInformation) {
        this.configInformation = configInformation;
    }
}
