package itunestippaidapps.inclass4.rumit.uncc.com.itunestoppaidapps;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Rumit on 2/25/17.
 */

public class ApplicationJsonUtils {

    static public ArrayList<Application> parseJsonInputStream(InputStream in) throws IOException, JSONException {
        BufferedReader br;
        StringBuilder sb = new StringBuilder();
        br = new BufferedReader(new InputStreamReader(in));
        String line;
        while((line = br.readLine()) != null){
            sb.append(line);
        }
        String jsonString = sb.toString();
//        Log.d("JsonReceived",jsonString);
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONObject feed = jsonObject.getJSONObject("feed");
        JSONArray arrAppplicationItems = feed.getJSONArray("entry");
        ArrayList<Application> arrApplications = new ArrayList<Application>();
        for(int i = 0 ; i < arrAppplicationItems.length() ; i++){
            JSONObject objAppJson = arrAppplicationItems.getJSONObject(i);
            Application objApp = new Application(objAppJson);
            arrApplications.add(objApp);
        }
        Log.d("JOSNArr",arrApplications.toString());
        return arrApplications;
    }

}
