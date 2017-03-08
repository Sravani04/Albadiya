package app.mamac.albadiya;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by T on 11-01-2017.
 */

public class ResultsDetailPage extends Activity {
    ImageView item_image;
    TextView end_date,posts,item_name;
    ResultsDetailPageAdapter resultsDetailPageAdapter;
    ListView listView;
    String result_id;
    String title;
    String date;
    String image;
    String post;
    Results resultsfrom_api;
    ArrayList<Integer> images;
    ArrayList<String> votes;
    ImageView back_btn;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_detail_page);
//        item_image = (ImageView) findViewById(R.id.item_image);
//        end_date   = (TextView) findViewById(R.id.end_date);
//        posts      = (TextView) findViewById(R.id.posts);
        item_name  = (TextView) findViewById(R.id.item_name);
        listView   = (ListView) findViewById(R.id.gallery_images);
        back_btn   = (ImageView) findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResultsDetailPage.this.onBackPressed();
            }
        });

        images = new ArrayList<>();
        votes = new ArrayList<>();

        images.add(R.drawable.yellowsoft);
        images.add(R.drawable.housejoy);
        images.add(R.drawable.flipkart);

        votes.add("3");
        votes.add("2");
        votes.add("5");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });


        if (getIntent()!=null){
            result_id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            item_name.setText(title);
            date = getIntent().getStringExtra("date");
            //end_date.setText(date);
            image = getIntent().getStringExtra("image");
//            Ion.with(this)
//                    .load(image)
//                    .withBitmap()
//                    .placeholder(R.drawable.ic_profile)
//                    .intoImageView(item_image);
            post = getIntent().getStringExtra("top_posts");
//            posts.setText(post);
            resultsfrom_api = (Results) getIntent().getSerializableExtra("results");

            resultsDetailPageAdapter = new ResultsDetailPageAdapter(this,resultsfrom_api);
            listView.setAdapter(resultsDetailPageAdapter);


        }



    }
}
