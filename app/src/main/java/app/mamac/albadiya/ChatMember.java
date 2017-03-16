package app.mamac.albadiya;

import android.content.Context;

import com.google.gson.JsonObject;

/**
 * Created by T on 16-03-2017.
 */

public class ChatMember {
    public String id,name,msg_type,file,image,count,message,date;

    public ChatMember(JsonObject jsonObject, Context context){
        id  = jsonObject.get("id").getAsString();
        name = jsonObject.get("name").getAsString();
        msg_type = jsonObject.get("msg_type").getAsString();
        file   = jsonObject.get("file").getAsString();
        image  = jsonObject.get("image").getAsString();
        count  = jsonObject.get("count").getAsString();
        message = jsonObject.get("message").getAsString();
        date = jsonObject.get("date").getAsString();
    }
}
