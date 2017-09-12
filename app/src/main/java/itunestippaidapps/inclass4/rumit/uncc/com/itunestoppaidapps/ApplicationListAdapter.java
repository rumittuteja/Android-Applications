package itunestippaidapps.inclass4.rumit.uncc.com.itunestoppaidapps;

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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by Rumit on 2/26/17.
 */

public class ApplicationListAdapter extends ArrayAdapter implements View.OnClickListener{
    Context mContext;
    int cellResourceID;
    ArrayList<Application> arrApplications;

    public ApplicationListAdapter(Context context, int resource, ArrayList<Application> arrayList) {
        super(context, resource, arrayList);
        mContext = context;
        cellResourceID = resource;
        arrApplications = arrayList;
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
        final ImageButton btn = (ImageButton) v;
        if(btn.isSelected()) {
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
                            editor.remove(objApp.getStrTitle());
                            btn.setSelected(false);
                            objApp.setFav(false);
                            btn.setImageResource(R.drawable.i2);
                            editor.commit();
                        }
                    })
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing

                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }else{
            new AlertDialog.Builder(mContext)
                    .setTitle("Add to Favourites")
                    .setMessage("Are you sure you want to add this app to your favourites?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ImageButton btn = (ImageButton) v;
                            int tag = (Integer) btn.getTag();
                            Application objApp = arrApplications.get(tag);
                            SharedPreferences sharedpreferences = mContext.getSharedPreferences("Preferences", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(objApp.getStrTitle(), objApp.getStrImgURL() + "|" + objApp.getStrTitle() + "|" +objApp.getStrCurrency()+ "|" +objApp.getStrPrice());
                            btn.setSelected(true);
                            objApp.setFav(true);
                            btn.setImageResource(R.drawable.i1);
                            editor.commit();
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
//        ImageButton btn = (ImageButton) v;
//        int tag = (Integer) btn.getTag();
//        Application objApp = arrApplications.get(tag);
//        SharedPreferences sharedpreferences = mContext.getSharedPreferences("Preferences", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedpreferences.edit();
//        if(btn.isSelected()) {
//
//        }else{
//
//
//        }
//        Log.d("AllFavs",sharedpreferences.getAll().toString());

    }



    static private class ViewHolder{
        ImageView imgView;
        TextView txtViewName;
        TextView txtViewPrice;
        ImageButton imgStarButton;
    }
}
