package app.mamac.albadiya;

import android.content.Context;

import com.google.gson.JsonObject;

import java.io.Serializable;

/**
 * Created by T on 27-01-2017.
 */

public class Notifications implements Serializable {
    public String post,type,time,time_ar,member_id,member_name,member_image,post_id,post_title,post_image;

    public Notifications(JsonObject jsonObject, Context context){
        member_id    = jsonObject.get("member").getAsJsonObject().get("id").getAsString();
        member_image = jsonObject.get("member").getAsJsonObject().get("image").getAsString();
        member_name  = jsonObject.get("member").getAsJsonObject().get("name").getAsString();
        if (jsonObject.get("type").getAsString().equals("Follow")){
            post = "";
        }else if (jsonObject.get("type").getAsString().equals("Like")){
            post_id = jsonObject.get("post").getAsJsonObject().get("id").getAsString();
            if (jsonObject.has("title")) {
                post_title = jsonObject.get("post").getAsJsonObject().get("title").getAsString();
            }else {
                post_title = "0";
            }
            post_image = jsonObject.get("post").getAsJsonObject().get("image").getAsString();
        }
        type         = jsonObject.get("type").getAsString();
        time         = jsonObject.get("time").getAsString();
        time_ar      = jsonObject.get("time_ar").getAsString();
    }
}
