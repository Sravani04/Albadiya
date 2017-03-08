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
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


import java.util.ArrayList;

/**
 * Created by T on 15-11-2016.
 */

public class LawyerExample extends Activity {
    ListView listView;
    LawyerAdapter lawyerAdapter;
    ArrayList<String> description;
    ArrayList<Integer> images;
    ArrayList<Packages> packagesfrom_api;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lawyer_listview);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        listView = (ListView) findViewById(R.id.lawyer_listview);
        images = new ArrayList<>();
        description = new ArrayList<>();
        packagesfrom_api = new ArrayList<>();
        images.add(R.drawable.yellow_circle);
        images.add(R.drawable.yellow_circle);
        images.add(R.drawable.yellow_circle);

        description.add("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s. when an unknown printer took a gallery of type and scrambled it to make  a type specimen book");
        description.add("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s. when an unknown printer took a gallery of type and scrambled it to make  a type specimen book");
        description.add("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s. when an unknown printer took a gallery of type and scrambled it to make  a type specimen book");

        lawyerAdapter = new LawyerAdapter(this,packagesfrom_api);
        listView.setAdapter(lawyerAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(LawyerExample.this,description.get(position),Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(LawyerExample.this,LawyerSignup.class);
                startActivity(intent);
            }
        });
            get_packages();
    }


    private void get_packages(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("plase wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Ion.with(this)
                .load("http://clients.yellowsoft.in/lawyers/api/packages.php")
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

                                Packages packages = new Packages(result.get(i).getAsJsonObject(),LawyerExample.this);
                                packagesfrom_api.add(packages);
                            }
                            lawyerAdapter.notifyDataSetChanged();

                        }
                    }
                });

    }
}
