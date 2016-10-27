package chillr.monitor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Macs on 10/3/2016.
 */
public class JsonPars {

    Double point[],temp;
    float avg;
    String errors = new String("");

    public Server serverdetails(String data,String metr,Server s) throws JSONException {
        JSONObject obj = new JSONObject(data);
        try {
            JSONArray series = obj.getJSONArray("series");
            JSONObject j = series.getJSONObject(0);
            JSONArray jsonarray = j.getJSONArray("pointlist");
            point = new Double[jsonarray.length()];
            for(int i=0;i<jsonarray.length();i++) {
                JSONArray value = jsonarray.getJSONArray(i);
                point[i]=Double.parseDouble(value.getString(1));
            }
            temp = new Double(0);
            for (int i=0;i<jsonarray.length();i++) {
                temp += point[i];
            }
            temp = temp/jsonarray.length();
            if(metr.contentEquals("system.disk.free")){
                temp = temp/1E10;
                avg = temp.floatValue();
                s.setDisk(point,jsonarray.length());
                s.setAvgDisk(avg);
            }
            if(metr.contentEquals("system.mem.free")){
                temp = temp/1E9;
                avg = temp.floatValue();
                s.setMem(point,jsonarray.length());
                s.setAvgMem(avg);
            }
            if(metr.contentEquals("nginx.net.avg_response")){
                avg = temp.floatValue();
                s.setAvgResp(avg);
                s.setResp(point,jsonarray.length());
            }
            if(metr.contentEquals("mongodb.metrics.document.returnedps")){
                avg = temp.floatValue();
                s.setAvgRetn(avg);
                s.setRetn(point,jsonarray.length());
            }

        } catch (JSONException e) {
            JSONArray err = obj.getJSONArray("errors");
            for(int i=0;i<err.length();i++) {
                errors = err.getString(i);
            }
            s.setFlag(errors);

        } catch (NumberFormatException e) {

            e.printStackTrace();
        }finally {
            return s;
        }
    }

    private JSONObject getObject(String key, JSONObject obj) throws JSONException {

        JSONObject temp = obj.getJSONObject(key);
        return temp;
    }

}
