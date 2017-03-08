package app.mamac.albadiya;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.GridView;
import android.view.View;
import android.widget.Toast;



import java.util.ArrayList;

/**
 * Created by T on 10-11-2016.
 */

public class GridExampleActivity extends AppCompatActivity {
    GridView gridView;
    GridviewAdapter gridviewAdapter;
    ArrayList<String> names;
    ArrayList<Integer> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridview_activity);
        gridView = (GridView) findViewById(R.id.gridview_activity);
        names = new ArrayList<>();
        images = new ArrayList<>();



        names.add("Yellowsoft");
        names.add("Housejoy");
        names.add("Snapdeal");
        names.add("Yahoo");
        names.add("Flipkart");
        names.add("Amazon");


        images.add(R.drawable.yellowsoft);
        images.add(R.drawable.housejoy);
        images.add(R.drawable.snapdeal);
        images.add(R.drawable.yahoo);
        images.add(R.drawable.flipkart);
        images.add(R.drawable.amazon);




        gridviewAdapter = new GridviewAdapter(this,names,images);
        gridView.setAdapter(gridviewAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(GridExampleActivity.this,names.get(position),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(GridExampleActivity.this,ThirdActivity.class);
                intent.putExtra("abc",names.get(position));
                intent.putExtra("abcd",images.get(position));
                startActivity(intent);

            }
        });
    }


}
