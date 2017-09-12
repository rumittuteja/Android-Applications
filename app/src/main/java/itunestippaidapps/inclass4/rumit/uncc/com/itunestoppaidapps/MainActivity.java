package itunestippaidapps.inclass4.rumit.uncc.com.itunestoppaidapps;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements FetchDataFromItunes.DataReceived{
    ListView listView;
    ArrayList<Application> arrListApps;
    int groupID = 1001;
    ProgressBar progBar;
    ApplicationListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instantiateUIElements();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(arrListApps != null) {
            markFavouritesAppropriately();
            adapter.notifyDataSetChanged();
        }else{
            fetchDataFromItunes();
        }
    }

    private void markFavouritesAppropriately(){
        SharedPreferences sharedpreferences = this.getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        Map<String, String> map = (Map<String, String>) sharedpreferences.getAll();
        for (Application objApp : arrListApps) {
            objApp.setFav(false);
            for (String key : map.keySet()){
                if(objApp.getStrTitle().equals(key)){
                    objApp.setFav(true);
                    break;
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(groupID,0,0,"Refresh List");
        menu.add(groupID,1,0,"Favorites");
        menu.add(groupID,2,0,"Sort increasingly");
        menu.add(groupID,3,0,"Sort decreasingly");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 0 :
                arrListApps.clear();
                fetchDataFromItunes();
                break;
            case 1 :
                Intent intent = new Intent("itunestippaidapps.inclass4.rumit.uncc.com.itunestoppaidapps.intent.action.FAVORITE_ACTIVITIES");
                startActivity(intent);
                break;
            case 2 :
                sortByAscending(true);
                break;
            case 3 :
                sortByAscending(false);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void sortByAscending(Boolean isAscending){
        Log.d("BeforeSortingList",arrListApps.toString());
        if(isAscending){
            Collections.sort(arrListApps, new Comparator<Application>() {
                @Override
                public int compare(Application app1, Application app2) {
                    Double  price1 = Double.parseDouble(app1.getStrPrice());
                    Double  price2 = Double.parseDouble(app2.getStrPrice());
                    return price1.compareTo(price2);
                }
            });
        }else{
            Collections.sort(arrListApps, new Comparator<Application>() {
                @Override
                public int compare(Application app1, Application app2) {
                    Double  price1 = Double.parseDouble(app1.getStrPrice());
                    Double  price2 = Double.parseDouble(app2.getStrPrice());
//                    return price1 > price2 ? 1 : 0;
                    return price2.compareTo(price1);
                }
            });
        }

        Log.d("SortedList",arrListApps.toString());
        if(adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    private void instantiateUIElements(){
        progBar = (ProgressBar)findViewById(R.id.progressBar);
        listView = (ListView)findViewById(R.id.listView);
    }

    private void populateIU(){
        adapter = new ApplicationListAdapter(this,R.layout.application_item_cell,arrListApps);
        listView.setAdapter(adapter);
    }

    private void fetchDataFromItunes(){
        progBar.animate();
        new FetchDataFromItunes(this).execute();
    }

    @Override
    public void dataReceived(ArrayList<Application> arrListApplication) {
        if(arrListApplication != null) {
            progBar.setVisibility(View.INVISIBLE);
            progBar.clearAnimation();
            progBar.requestLayout();
            arrListApps = arrListApplication;
            markFavouritesAppropriately();
            populateIU();
        }else{
            Toast.makeText(this, "Couldnt fetch data from the iTunes server. Please try refreshing the list from the top right menu.", Toast.LENGTH_LONG).show();
        }
    }
}
