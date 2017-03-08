package app.mamac.albadiya;

import android.content.Context;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by T on 11-01-2017.
 */

public class Results implements Serializable{

    public String id,title,title_ar,image,end_date,top_posts;
    public ArrayList<Posts> posts;

    public Results(JsonObject jsonObject, Context context){
        id = jsonObject.get("id").getAsString();
        title = jsonObject.get("title").getAsString();
        title_ar = jsonObject.get("title_ar").getAsString();
        image = jsonObject.get("image").getAsString();
        end_date = jsonObject.get("end_date").getAsString();
        posts = new ArrayList<>();
        for(int i=0;i<jsonObject.get("top_posts").getAsJsonArray().size();i++){

            Posts comp_image = new Posts(jsonObject.get("top_posts").getAsJsonArray().get(i).getAsJsonObject(),context);

            posts.add(comp_image);

        }

    }

    public class Posts implements  Serializable{

        public String id,title,description,image,votes_count;

        public Posts(JsonObject jsonObject,Context context){

            id             = jsonObject.get("id").getAsString();
            title          = jsonObject.get("title").getAsString();
            description    = jsonObject.get("description").getAsString();
            image          = jsonObject.get("image").getAsString();
            votes_count    = jsonObject.get("votes_count").getAsString();

        }
    }


}
