package app.mamac.albadiya;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import static android.R.attr.action;

/**
 * Created by T on 12-12-2016.
 */

public class LikeFragment extends Fragment {
    LikeFragmentAdapter likeFragmentAdapter;
    ArrayList<Integer> images;
    ListView listView;
    ArrayList<Notifications>notificationsfrom_api;
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.likes_fragment,container,false);

        listView = (ListView) view.findViewById(R.id.like_list);

        images = new ArrayList<>();
        notificationsfrom_api = new ArrayList<>();

        images.add(R.drawable.banner3);
        images.add(R.drawable.timeline);
        images.add(R.drawable.amazon);
        images.add(R.drawable.banner1);
        images.add(R.drawable.banner);

        likeFragmentAdapter = new LikeFragmentAdapter(getActivity(),notificationsfrom_api);
        listView.setAdapter(likeFragmentAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
        get_notifications();

        return view;

    }

    public void get_notifications(){
        Ion.with(getActivity())
                .load(Settings.SERVER_URL+"notifications.php")
                .setBodyParameter("member_id",Settings.GetUserId(getActivity()))
                .setBodyParameter("type","2")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                       for (int i=0;i<result.size();i++){
                           Notifications notifications = new Notifications(result.get(i).getAsJsonObject(),getActivity());
                           notificationsfrom_api.add(notifications);
                       }
                        likeFragmentAdapter.notifyDataSetChanged();
                    }
                });
    }






    }

