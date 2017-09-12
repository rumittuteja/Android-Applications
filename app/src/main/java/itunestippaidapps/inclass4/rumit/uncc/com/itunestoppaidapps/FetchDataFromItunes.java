package itunestippaidapps.inclass4.rumit.uncc.com.itunestoppaidapps;

import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * Created by Rumit on 2/25/17.
 */

public class FetchDataFromItunes extends AsyncTask<Void,Void,ArrayList>{

    DataReceived activity;

    public FetchDataFromItunes(DataReceived activity) {
        this.activity = activity;
    }

    @Override
    protected ArrayList doInBackground(Void... params) {

        Request req = new Request("https://itunes.apple.com/us/rss/toppaidapplications/limit=25/json","GET");
        try {
            HttpURLConnection conn = req.getConnection();
            conn.connect();
            int statusCode = conn.getResponseCode();
            if(statusCode == HttpURLConnection.HTTP_OK){
                InputStream in = conn.getInputStream();
                return ApplicationJsonUtils.parseJsonInputStream(in);
            }else{
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList arrayList) {
        super.onPostExecute(arrayList);
        activity.dataReceived(arrayList);
    }


    static public interface DataReceived{
        public void dataReceived(ArrayList<Application> arrListApplication);
    }
}
