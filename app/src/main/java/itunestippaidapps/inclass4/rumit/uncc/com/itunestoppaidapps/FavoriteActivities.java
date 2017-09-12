package itunestippaidapps.inclass4.rumit.uncc.com.itunestoppaidapps;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Map;

public class FavoriteActivities extends AppCompatActivity {
    ArrayList<Application> arrFavApplications = new ArrayList<Application>();
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_activities);
        listView = (ListView)findViewById(R.id.favListView);
        loadData();
        populateUI();
    }

    private void loadData(){
        SharedPreferences sharedpreferences = this.getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        Map<String,String> map = (Map<String, String>) sharedpreferences.getAll();
        for (String key:map.keySet()){
            Log.d(key,map.get(key));
            String strObjApp = map.get(key);
            Application objApp = new Application();
            CharSequence[] arrData = strObjApp.split("\\|");
            Log.d(key,map.get(key));
            objApp.setStrTitle(arrData[1].toString());
            objApp.setStrCurrency(arrData[2].toString());
            objApp.setStrPrice(arrData[3].toString());
            objApp.setStrImgURL(arrData[0].toString());
            objApp.setFav(true);
            arrFavApplications.add(objApp);
        }
    }

    private void populateUI(){
        FavLIstAdapter adapter = new FavLIstAdapter(this,R.layout.application_item_cell,arrFavApplications);
        listView.setAdapter(adapter);
    }
}
