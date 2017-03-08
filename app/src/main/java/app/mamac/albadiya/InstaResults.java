package app.mamac.albadiya;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;


import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by T on 08-12-2016.
 */

public class InstaResults extends Fragment {
    InstaResultsAdapter instaResultsAdapter;
    ListView listView;
    ArrayList<String> name;
    ArrayList<Results> resultsfrom_api;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.insta_results_items,container,false);
        listView = (ListView) view.findViewById(R.id.results_items);

        name = new ArrayList<>();
        resultsfrom_api = new ArrayList<>();

        name.add("Results");
        name.add("Results");
        name.add("Results");
        name.add("Results");
        name.add("Results");
        name.add("Results");
        name.add("Results");
        name.add("Results");
        name.add("Results");
        name.add("Results");
        name.add("Results");
        name.add("Results");
        name.add("Results");

        instaResultsAdapter = new InstaResultsAdapter(getActivity(),resultsfrom_api);
        listView.setAdapter(instaResultsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(),name.get(position),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(),ResultsDetailPage.class);
                intent.putExtra("results",resultsfrom_api.get(position));
                intent.putExtra("id",resultsfrom_api.get(position).id);
                intent.putExtra("title",resultsfrom_api.get(position).title);
                intent.putExtra("date",resultsfrom_api.get(position).end_date);
                intent.putExtra("TopPosts",String.valueOf(resultsfrom_api.get(position).top_posts));
                intent.putExtra("image",resultsfrom_api.get(position).image);
                startActivity(intent);
            }
        });
        get_results();
        return view;
    }

    public void get_results(){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Ion.with(this)
                .load(Settings.SERVER_URL+"results.php")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        try{
                            if (progressDialog!=null)
                                progressDialog.dismiss();
                            for (int i=0;i<result.size();i++){
                                Results results = new Results(result.get(i).getAsJsonObject(),getActivity());
                                resultsfrom_api.add(results);
                            }

                            instaResultsAdapter.notifyDataSetChanged();
                        }catch (Exception ex){
                            e.printStackTrace();
                        }

                    }
                });
    }
}
