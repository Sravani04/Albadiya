package im.ene.toro.sample.feature.facebook.timeline;

import android.content.Context;

import com.google.gson.JsonObject;

import java.util.ArrayList;

/**
 * Created by T on 17-03-2017.
 */

public class MemberDetails  {
    public String id,name,email,phone,image,subscription;
    public ArrayList<Posts> postses;
    public MemberDetails(JsonObject jsonObject, Context context){
        id = jsonObject.get("id").getAsString();
        name = jsonObject.get("name").getAsString();
        email = jsonObject.get("email").getAsString();
        phone = jsonObject.get("phone").getAsString();
        image = jsonObject.get("image").getAsString();
        subscription = jsonObject.get("subscription").getAsString();
        postses = new ArrayList<>();
        for (int i=0;i<jsonObject.get("posts").getAsJsonArray().size();i++){
            Posts posts = new Posts(jsonObject.get("posts").getAsJsonArray().get(i).getAsJsonObject(),context);
            postses.add(posts);
        }
    }

    public class Posts{
        public String id,title,title_ar,image,video,views,likes,member_liked,description,description_ar,time,time_ar;
        public Posts(JsonObject jsonObject,Context context){
            id = jsonObject.get("id").getAsString();
            title = jsonObject.get("title").getAsString();
            title_ar = jsonObject.get("title_ar").getAsString();
            image = jsonObject.get("image").getAsString();
            video = jsonObject.get("video").getAsString();
            views = jsonObject.get("views").getAsString();
            likes = jsonObject.get("likes").getAsString();
            member_liked = jsonObject.get("member_liked").getAsString();
            description = jsonObject.get("description").getAsString();
            description_ar = jsonObject.get("description_ar").getAsString();
            time = jsonObject.get("time").getAsString();
            time_ar = jsonObject.get("time_ar").getAsString();


        }
    }
}
