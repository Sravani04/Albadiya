package app.mamac.albadiya;

import android.content.Context;

import com.google.gson.JsonObject;

/**
 * Created by T on 12-01-2017.
 */

public class News  {

    public String id,title,title_ar,image,description,description_ar,date;

    public News(JsonObject jsonObject, Context context){
        id              = jsonObject.get("id").getAsString();
        title           = jsonObject.get("title").getAsString();
        title_ar        = jsonObject.get("title_ar").getAsString();
        image           = jsonObject.get("image").getAsString();
        description     = jsonObject.get("description").getAsString();
        description_ar  = jsonObject.get("description_ar").getAsString();
        date            = jsonObject.get("date").getAsString();
    }
}
