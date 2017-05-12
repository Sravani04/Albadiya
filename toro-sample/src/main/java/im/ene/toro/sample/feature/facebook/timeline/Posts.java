package im.ene.toro.sample.feature.facebook.timeline;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;

import java.io.Serializable;


/**
 * Created by T on 26-11-2016.
 */

public class Posts implements Serializable {

    public String id,title,title_ar,image,video,description,description_ar,member_like,total_likes,total_views,user_image,user_id,user_name,time,
            competition_id;


    public Posts(JsonObject jsonObject, Context context){
        id = jsonObject.get("id").getAsString();
        title = jsonObject.get("title").getAsString();
        title_ar = jsonObject.get("title_ar").getAsString();
        image = jsonObject.get("image").getAsString();

        video = jsonObject.get("video").getAsString();
      //  video = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";

        description = jsonObject.get("description").getAsString();
        description_ar = jsonObject.get("description_ar").getAsString();
        if(jsonObject.has("member_like"))
            member_like = jsonObject.get("member_like").getAsString();
        else
            member_like = "0";
        Log.e("response",jsonObject.get("member_like").getAsString());

        if(jsonObject.has("total_likes"))
            total_likes = jsonObject.get("total_likes").getAsString();
        else
            total_likes = "0";
        user_id = "0";
        user_image = "no-image";
        user_name = "no-name";

        try{
            if(jsonObject.has("views"))
                total_views = jsonObject.get("views").getAsString();
            else
                total_views = "99";

            user_id = jsonObject.get("posted").getAsJsonObject().get("id").getAsString();
            user_image = jsonObject.get("posted").getAsJsonObject().get("image").getAsString();
            try {
                user_name = jsonObject.get("posted").getAsJsonObject().get("name").getAsString();
            }catch (Exception ex){
                user_name = "no-name";
            }




        }catch (Exception ex){
            ex.printStackTrace();
        }

        time = jsonObject.get("time").getAsString();


    }

    public Posts(JsonObject jsonObject, Context context,boolean tre){
        id = jsonObject.get("id").getAsString();
        title = jsonObject.get("title").getAsString();
        image = jsonObject.get("image").getAsString();
        description = jsonObject.get("description").getAsString();
        //video = image;
//        if (image.endsWith(".mp4")){
//            video = image;
//        }else {
//            video ="";
//        }
        video = jsonObject.get("video").getAsString();
        user_id = jsonObject.get("member").getAsJsonObject().get("id").getAsString();
        user_image = jsonObject.get("member").getAsJsonObject().get("image").getAsString();
        try {
            user_name = jsonObject.get("member").getAsJsonObject().get("name").getAsString();
        }catch (Exception ex){
            user_name = "no-name";
        }

        time = "0";
        member_like = "0";
        total_likes = "0";
        total_views="0";
        description_ar = "0";
    }


   



}
