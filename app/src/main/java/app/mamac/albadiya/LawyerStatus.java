package app.mamac.albadiya;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;



import java.util.ArrayList;

/**
 * Created by T on 18-11-2016.
 */

public class LawyerStatus extends Activity {
    ListView status;
    ImageView loading;
    ImageView settings;
    ImageView books;
    LawyerPeopleAdapter lawyerPeopleAdapter;
    ArrayList<String> heading;
    ArrayList<Integer> images;
    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lawyers_people);
        status = (ListView) findViewById(R.id.lawyers);
        heading = new ArrayList<>();
        images  = new ArrayList<>();

        images.add(R.drawable.yellow_circle);
        images.add(R.drawable.yellow_circle);
        images.add(R.drawable.yellow_circle);

        heading.add("THE LAW FIRM OF LABEED ABDAL");
        heading.add("KUWAIT SOCIETY OF LAWYERS");
        heading.add("AL TAMIMI AND COMPANY");

        lawyerPeopleAdapter = new LawyerPeopleAdapter(this,heading,images);
        status.setAdapter(lawyerPeopleAdapter);
        status.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(LawyerStatus.this,heading.get(position),Toast.LENGTH_SHORT).show();
                Intent Status = new Intent(LawyerStatus.this,Lawyerspage.class);
                startActivity(Status);
            }
        });

        loading = (ImageView) findViewById(R.id.loading);
        loading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loading = new Intent(LawyerStatus.this,LawyerTimeline.class);
                startActivity(loading);
            }
        });

        settings = (ImageView) findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent setting = new Intent(LawyerStatus.this,LawyerSettings.class);
                startActivity(setting);
            }
        });

        books = (ImageView) findViewById(R.id.books);
        books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent books = new Intent(LawyerStatus.this,LawyerBooks.class);
                startActivity(books);
            }
        });


    }


}



