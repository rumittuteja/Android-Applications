package itunestippaidapps.inclass4.rumit.uncc.com.itunestoppaidapps;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created by Rumit on 2/25/17.
 */

public class Request {

    String strBaseURL;
    String strMethod;
    HashMap<String,String> params = new HashMap<String, String>();

    public Request(String strBaseURL, String strMethod) {
        this.strBaseURL = strBaseURL;
        this.strMethod = strMethod;
    }


    public void addParams(String key, String value){
        params.put(key,value);
    }

    private String getEncodedParameters()  {
        StringBuilder sb = new StringBuilder();
        for(String key:params.keySet()){
            String value = null;
            try {
                value = URLEncoder.encode(params.get(key),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if(sb.length() != 0){
                sb.append("&");
            }
            sb.append(key + "=" + value);
        }
        return sb.toString();
    }

    private String getEncodedURL(){
        return strBaseURL + "?" + getEncodedParameters();
    }


    public HttpURLConnection getConnection() throws IOException {
        if(strMethod.equals("GET")){
            Log.d("URL",getEncodedURL());
            URL url = new URL(getEncodedURL());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            return conn;
        }else{ // POST
            URL url = new URL(this.strBaseURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            OutputStreamWriter writer =  new OutputStreamWriter(con.getOutputStream());
            writer.write(getEncodedParameters());
            writer.flush();
            return con;
        }
    }

}
