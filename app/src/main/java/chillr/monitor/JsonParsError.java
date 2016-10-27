package chillr.monitor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Macs on 10/25/2016.
 */

public class JsonParsError {
    public String errordetails(String data) throws JSONException {

        JSONObject obj = new JSONObject(data);
        JSONArray arrayEror = obj.getJSONArray("errors");
        String[] errors = new String[10];
        String errorSent = new String("");
        int i;
        for(i=0;i<arrayEror.length();i++){
         errors[i] = arrayEror.getString(i);
            errorSent.concat(errors[i]+"\n");
        }
        errors[i]=null;
        return errorSent;
    }
}
