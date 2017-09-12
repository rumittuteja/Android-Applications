package itunestippaidapps.inclass4.rumit.uncc.com.itunestoppaidapps;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Rumit on 2/25/17.
 */

public class Application implements Serializable{

    String strTitle;
    String strPrice;
    String strImgURL;
    String strCurrency;
    boolean isFav = false;

    public Application(){

    }

    public boolean isFav() {
        return isFav;
    }

    public void setFav(boolean fav) {
        isFav = fav;
    }

    public String getStrTitle() {
        return strTitle;
    }

    public void setStrTitle(String strTitle) {
        this.strTitle = strTitle;
    }

    public String getStrPrice() {
        return strPrice;
    }

    public void setStrPrice(String strPrice) {
        this.strPrice = strPrice;
    }

    public String getStrImgURL() {
        return strImgURL;
    }

    public void setStrImgURL(String strImgURL) {
        this.strImgURL = strImgURL;
    }

    public Application(JSONObject appJSON) throws JSONException {
        JSONObject name = appJSON.getJSONObject("im:name");
        strTitle = name.getString("label");

        JSONArray imgUrl = appJSON.getJSONArray("im:image");
        for(int i = 0 ; i < imgUrl.length() ; i++){
            if(i == imgUrl.length() - 1){
                strImgURL = imgUrl.getJSONObject(i).getString("label");
            }
        }

        JSONObject price = appJSON.getJSONObject("im:price");
        JSONObject attributes = price.getJSONObject("attributes");
        strPrice = attributes.getString("amount");
        strCurrency = attributes.getString("currency");
    }

    public String getStrCurrency() {
        return strCurrency;
    }

    public void setStrCurrency(String strCurrency) {
        this.strCurrency = strCurrency;
    }

    @Override
    public String toString() {
        return "Application{" +
                "strTitle='" + strTitle + '\'' +
                ", strPrice='" + strPrice + '\'' +
                ", strImgURL='" + strImgURL + '\'' +
                ", strCurrency='" + strCurrency + '\'' +
                ", isFav=" + isFav +
                '}';
    }
}
