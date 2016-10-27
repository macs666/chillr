package chillr.monitor;

import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Macs on 10/3/2016.
 */
public class ServerClient {

    private static String url = "https://app.datadoghq.com/api/v1/query?api_key=";
    String api_key = "93af0da2383e1af60fe1bfe42b8ba949";
    String app_key = "62dc6e0c5c605f8273c9479b604c0b52e873ef70";
    String from,to;
    Long temp;

    public String getServerData(String m,String serv) {

        HttpURLConnection con = null ;
        InputStream is = null;
        temp = (Long) System.currentTimeMillis()/1000;
        to =  temp.toString();
        temp = temp - 10;
        from = temp.toString();


        try {
            con = (HttpURLConnection) ( new URL(url+api_key+"&application_key="+app_key+"&from="+from+"&to="+to+"&query="+m+"{*}by{"+serv+"}")).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.connect();
            StringBuffer buffer = new StringBuffer();

            if(con.getResponseCode()==200){
            is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ( (line = br.readLine()) != null )
                buffer.append(line);
            }
            else{
                is = con.getErrorStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line =  br.readLine();
                buffer.append(line);
            }
            is.close();
            con.disconnect();
            return buffer.toString();
        }
        catch(Throwable t) {
            t.printStackTrace();
        }
        finally {
            try {
                is.close();
            } catch(Throwable t) {}
            try {
                con.disconnect();
            } catch(Throwable t) {}
        }
        return null;
    }

}
