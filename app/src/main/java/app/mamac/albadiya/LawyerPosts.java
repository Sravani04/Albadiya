package app.mamac.albadiya;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


import java.util.ArrayList;

/**
 * Created by T on 15-11-2016.
 */

public class LawyerPosts extends Activity {
    ListView listView;
    LawyerPostsAdapter lawyerPostsAdapter;
    ArrayList<String> title;
    ArrayList<String> message;
    ArrayList<Integer> image;
    ArrayList<String> date;
    ArrayList<Posts> postsfrom_api;
    TextView add_post;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lawyer_posts);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        listView = (ListView) findViewById(R.id.posts);
        add_post = (TextView) findViewById(R.id.add_post);


        title   = new ArrayList<>();
        message = new ArrayList<>();
        image   = new ArrayList<>();
        date    = new ArrayList<>();
        postsfrom_api = new ArrayList<>();

        title.add("fgf");
        title.add("fgg");

        message.add("dfsdfdsf");
        message.add("cvcvcvf");

        image.add(R.drawable.timeline);
        image.add(R.drawable.amazon);

        date.add("");
        date.add("");




        lawyerPostsAdapter = new LawyerPostsAdapter(this,postsfrom_api);
        listView.setAdapter(lawyerPostsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(LawyerPosts.this,title.get(position),Toast.LENGTH_SHORT).show();
                add_post.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent post = new Intent(LawyerPosts.this,LawyerAddPost.class);
                        startActivity(post);
                    }
                });
            }
        });
        get_posts();


    }


    private void get_posts(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("plase wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Ion.with(this)
                .load("http://clients.yellowsoft.in/lawyers/api/posts.php")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        // do stuff with the result or error
                        //{"status":"Failure","message":"Please Enter Your Type"}
                        if(progressDialog!=null)
                            progressDialog.dismiss();
                        if(e!=null){
                            e.printStackTrace();
                        }
                        else {
                            Log.e("response",String.valueOf(result.size()));
                            for(int i=0;i<result.size();i++){

                                Posts posts = new Posts(result.get(i).getAsJsonObject(),LawyerPosts.this);
                                postsfrom_api.add(posts);
                            }

                            lawyerPostsAdapter.notifyDataSetChanged();

                        }
                    }
                });

    }







}
