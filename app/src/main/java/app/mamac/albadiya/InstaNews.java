package app.mamac.albadiya;

import android.app.ProgressDialog;
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

public class InstaNews extends Fragment {
    ListView listView;
    InstaNewsAdapter instaNewsAdapter;
    ArrayList<String> items;
    ArrayList<News> newsfrom_api;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.insta_news_items,container,false);
        listView = (ListView) view.findViewById(R.id.news_items);

        items = new ArrayList<>();
        newsfrom_api = new ArrayList<>();

        items.add("News");
        items.add("News");
        items.add("News");
        items.add("News");
        items.add("News");
        items.add("News");
        items.add("News");
        items.add("News");
        items.add("News");
        items.add("News");
        items.add("News");
        items.add("News");
        items.add("News");

        instaNewsAdapter = new InstaNewsAdapter(getActivity(),newsfrom_api);
        listView.setAdapter(instaNewsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(),items.get(position),Toast.LENGTH_SHORT).show();
            }
        });
        get_news();
        return view;
    }

    public void get_news(){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Ion.with(getActivity())
                .load(Settings.SERVER_URL+"news.php")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        try {
                            if (progressDialog!=null)
                                progressDialog.dismiss();
                            for (int i=0;i<result.size();i++){
                                News news = new News(result.get(i).getAsJsonObject(),getActivity());
                                newsfrom_api.add(news);
                            }

                            instaNewsAdapter.notifyDataSetChanged();
                        }catch (Exception ex){
                            e.printStackTrace();
                        }

                    }
                });
    }
}
