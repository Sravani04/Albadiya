package app.mamac.albadiya;

import android.content.Context;

import com.google.gson.JsonObject;

import java.io.Serializable;

/**
 * Created by T on 02-03-2017.
 */

public class Subscription implements Serializable {
    public String logo,title,title_ar,subscription_price,itunes_link,playstore_link;
    public Subscription(JsonObject jsonObject, Context context){
        logo  = jsonObject.get("logo").getAsString();
        title = jsonObject.get("title").getAsString();
        title_ar = jsonObject.get("title_ar").getAsString();
        subscription_price = jsonObject.get("subscription_price").getAsString();
        itunes_link = jsonObject.get("itunes_link").getAsString();
        playstore_link = jsonObject.get("playstore_link").getAsString();
    }
}
