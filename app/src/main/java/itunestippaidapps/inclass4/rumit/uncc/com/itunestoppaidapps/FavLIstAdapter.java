package itunestippaidapps.inclass4.rumit.uncc.com.itunestoppaidapps;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Rumit on 2/26/17.
 */

public class FavLIstAdapter extends ArrayAdapter<Application> implements View.OnClickListener{
    Context mContext;
    int cellResourceID;
    ArrayList<Application> arrApplications;
    private ArrayList<DataSetObserver> observers = new ArrayList<DataSetObserver>();

    public FavLIstAdapter(Context context, int resource, ArrayList<Application> objects) {
        super(context, resource, objects);
        mContext = context;
        cellResourceID = resource;
        arrApplications = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(cellResourceID,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.txtViewName = (TextView)convertView.findViewById(R.id.txtViewName);
            viewHolder.txtViewPrice = (TextView)convertView.findViewById(R.id.txtViewPrice);
            viewHolder.imgView =  (ImageView)convertView.findViewById(R.id.imgView);
            viewHolder.imgStarButton = (ImageButton) convertView.findViewById(R.id.imgStarBtn);
            convertView.setTag(viewHolder);
        }
        Log.d("CameToLoad","");
        viewHolder = (ViewHolder)convertView.getTag();
        viewHolder.txtViewName.setText(arrApplications.get(position).getStrTitle());
        viewHolder.txtViewPrice.setText(arrApplications.get(position).getStrCurrency() + " " + arrApplications.get(position).getStrPrice());
        viewHolder.imgStarButton.setOnClickListener(this);
        viewHolder.imgStarButton.setTag(position);
        viewHolder.imgStarButton.setImageResource(arrApplications.get(position).isFav() ? R.drawable.i1 : R.drawable.i2);
        viewHolder.imgStarButton.setSelected(arrApplications.get(position).isFav());
        Log.d("Obj",arrApplications.get(position).toString());
        Picasso.with(mContext).load(arrApplications.get(position).getStrImgURL()).into(viewHolder.imgView);
        return convertView;
    }

    @Override
    public void onClick(final View v) {
        new AlertDialog.Builder(mContext)
                .setTitle("Add to Favourites")
                .setMessage("Are you sure you want to remove this app from your favourites?")
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ImageButton btn = (ImageButton) v;
                        int tag = (Integer) btn.getTag();
                        Application objApp = arrApplications.get(tag);
                        SharedPreferences sharedpreferences = mContext.getSharedPreferences("Preferences", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        arrApplications.remove(objApp);
                        editor.remove(objApp.getStrTitle());
                        btn.setSelected(false);
                        objApp.setFav(false);
                        btn.setImageResource(R.drawable.i2);
                        editor.commit();
                        Log.d("AllFavs",sharedpreferences.getAll().toString());
                        notifyDataSetChanged();
                        if(arrApplications.size() == 0){
                            Toast.makeText(mContext, "No applications to display in the favourites list.", Toast.LENGTH_LONG).show();
                            Activity act = (Activity)mContext;
                            act.finish();
                        }
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    static private class ViewHolder{
        ImageView imgView;
        TextView txtViewName;
        TextView txtViewPrice;
        ImageButton imgStarButton;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        super.registerDataSetObserver(observer);
        observers.add(observer);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        for (DataSetObserver observer: observers) {
            observer.onChanged();
        }
    }

}
