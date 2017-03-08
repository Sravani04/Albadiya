package app.mamac.albadiya;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;



import java.util.ArrayList;

/**
 * Created by T on 10-11-2016.
 */

public class ExampleListActivity extends AppCompatActivity {
    ListView listView;
    ListviewAdapter listviewAdapter;
    ArrayList<String> names;
    ArrayList<Integer> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_example);
        listView = (ListView) findViewById(R.id.listview_example);
        names = new ArrayList<>();
        images = new ArrayList<>();

        names.add("android");
        names.add("ios");
        names.add("php");
        names.add("seo");
        names.add("android");
        names.add("ios");
        names.add("php");
        names.add("seo");

        images.add(R.drawable.banner);
        images.add(R.drawable.banner1);
        images.add(R.drawable.banner3);
        images.add(R.drawable.banner4);
        images.add(R.drawable.banner);
        images.add(R.drawable.banner1);
        images.add(R.drawable.banner3);
        images.add(R.drawable.banner4);


        listviewAdapter = new ListviewAdapter(this,names,images);
        listView.setAdapter(listviewAdapter);
    }
}
