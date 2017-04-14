package app.mamac.albadiya;

import android.content.Context;

import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by T on 19-12-2016.
 */

public class Competitors implements Serializable{

    public String id,title,title_ar,description,description_ar,end_date,image;
    public ArrayList<Images> images;
//fsfsdf
    public Competitors(JsonObject jsonObject, Context context){
        id             = jsonObject.get("id").getAsString();
        title          = jsonObject.get("title").getAsString();
        title_ar       = jsonObject.get("title_ar").getAsString();
        description    = jsonObject.get("description").getAsString();
        description_ar = jsonObject.get("description_ar").getAsString();
        end_date       = jsonObject.get("end_date").getAsString();
        image = jsonObject.get("image").getAsString();
        images = new ArrayList<>();
        for(int i=0;i<jsonObject.get("images").getAsJsonArray().size();i++){

            Images comp_image = new Images(jsonObject.get("images").getAsJsonArray().get(i).getAsJsonObject(),context);

            images.add(comp_image);

        }


    }

    public class Images implements  Serializable{

        public String id,title,description,image,video,mid,mname,mimage;

        public Images(JsonObject jsonObject,Context context){

            id             = jsonObject.get("id").getAsString();
            title          = jsonObject.get("title").getAsString();
            description    = jsonObject.get("description").getAsString();
            image = jsonObject.get("image").getAsString();
            video = jsonObject.get("video").getAsString();
            mid            = jsonObject.get("member").getAsJsonObject().get("id").getAsString();

            try {
                mname          = jsonObject.get("member").getAsJsonObject().get("name").getAsString();
            }catch (Exception ex){
                mname = "no-name";
            }


            mimage            = jsonObject.get("member").getAsJsonObject().get("image").getAsString();
        }
    }

}
