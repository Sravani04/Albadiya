package app.mamac.albadiya;

import android.content.Context;

import com.google.gson.JsonObject;

/**
 * Created by T on 21-11-2016.
 */

public class Packages {


    public  String id,title,title_ar,price,validity,messages,go_icon,timeline,social,description;

    public  Packages(JsonObject jsonObject, Context context){
        id = jsonObject.get("id").getAsString();
        title = jsonObject.get("title").getAsString();
        title_ar = jsonObject.get("title_ar").getAsString();
        price = jsonObject.get("price").getAsString();
        validity = jsonObject.get("validity").getAsString();
        messages = jsonObject.get("messages").getAsString();
        go_icon = jsonObject.get("go_icon").getAsString();
        timeline = jsonObject.get("timeline").getAsString();
        social = jsonObject.get("social").getAsString();
        description= jsonObject.get("description").getAsString();

    }

}




