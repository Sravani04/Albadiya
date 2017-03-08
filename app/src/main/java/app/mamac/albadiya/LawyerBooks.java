package app.mamac.albadiya;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


/**
 * Created by T on 22-11-2016.
 */

public class LawyerBooks extends Activity {
    TextView books_title;
    TextView book_id;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lawyer_books);
        books_title = (TextView) findViewById(R.id.book_title);
        book_id     = (TextView) findViewById(R.id.book_id);
        get_books();
    }

    public void get_books(){
        String url = "http://clients.yellowsoft.in/lawyers/api/books.php";
        Ion.with(LawyerBooks.this)
                .load(url)
                .setBodyParameter("member_id",Settings.GetUserId(LawyerBooks.this))
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        JsonObject jsonObject = result.get(0).getAsJsonObject();
                        book_id.setText(jsonObject.get("id").getAsString());
                        books_title.setText(jsonObject.get("title").getAsString());

                    }
                });
    }

}
