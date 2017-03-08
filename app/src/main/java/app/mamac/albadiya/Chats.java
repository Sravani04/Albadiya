package app.mamac.albadiya;

import android.content.Context;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by T on 29-12-2016.
 */

public class Chats  {
    public String id,msg_type,file,description,member_id,member_name,member_image,receiver,date;

    public Chats(JsonObject jsonObject,Context context) {
        id = jsonObject.get("id").getAsString();
        member_id = jsonObject.get("member").getAsJsonObject().get("id").getAsString();
        member_name = jsonObject.get("member").getAsJsonObject().get("name").getAsString();
        member_image = jsonObject.get("member").getAsJsonObject().get("image").getAsString();
        msg_type = jsonObject.get("msg_type").getAsString();
        file = jsonObject.get("file").getAsString();
        description = jsonObject.get("description").getAsString();
        receiver = "0";
        date = jsonObject.get("date").getAsString();


    }


}

