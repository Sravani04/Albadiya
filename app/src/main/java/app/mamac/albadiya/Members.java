package app.mamac.albadiya;

import android.content.Context;

import com.google.gson.JsonObject;

/**
 * Created by T on 14-12-2016.
 */

public class Members  {
    public String id,name,email,phone,image;

    public Members(JsonObject jsonObject, Context context){
        id = jsonObject.get("id").getAsString();
        name = jsonObject.get("name").getAsString();
        email = jsonObject.get("email").getAsString();
        phone = jsonObject.get("phone").getAsString();
        image = jsonObject.get("image").getAsString();
    }
}
