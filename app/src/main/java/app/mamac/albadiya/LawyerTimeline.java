package app.mamac.albadiya;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;



/**
 * Created by T on 15-11-2016.
 */

public class LawyerTimeline extends Activity {
    ImageView profile;
    ImageView settings;
    ImageView books;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lawyers_timeline);
        ImageView profile = (ImageView) findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(LawyerTimeline.this,LawyerStatus.class);
                startActivity(profile);
            }
        });

        ImageView settings = (ImageView) findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(LawyerTimeline.this);
                progressDialog.setMessage("please wait..");
                progressDialog.setCancelable(true);
                progressDialog.show();
                if(progressDialog!=null)
                    progressDialog.dismiss();
                Intent settings = new Intent(LawyerTimeline.this,LawyerSettings.class);
                startActivity(settings);
            }
        });

        ImageView books = (ImageView) findViewById(R.id.books);
        books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent books = new Intent(LawyerTimeline.this,LawyerBooks.class);
                startActivity(books);
            }
        });
    }


}
